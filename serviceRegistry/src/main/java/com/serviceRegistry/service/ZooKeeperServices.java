package com.serviceRegistry.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ZooKeeperServices {
    private final CuratorFramework curatorFramework;
    private final ConcurrentHashMap<String, String> uriToZnodePath;

    public ZooKeeperServices() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getResourceAsStream("/application.properties"));

            curatorFramework = CuratorFrameworkFactory
                    .newClient(props.getProperty("z_host")
                            + ":"
                            + props.getProperty("z_port"), new RetryNTimes(5, 1000));
            curatorFramework.start();
            uriToZnodePath = new ConcurrentHashMap<>();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public void registerService(String name, String uri) {
        try {
            String znode = "/services/" + name;

            if (curatorFramework.checkExists().forPath(znode) == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath(znode);
            }

            CuratorOp znodePath = curatorFramework.transactionOp()
                    .create()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(znode+"/_", uri.getBytes());
            String result = curatorFramework.transaction().forOperations(znodePath).get(0).toString();
            uriToZnodePath.put(uri, result);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not register service rating\""
                    + "\", with URI \"" + uri + "\": ");
        }
    }

    public void unregisterService(String uri) {
        try {
            if (uriToZnodePath.contains(uri)) {
                curatorFramework.delete().forPath(uriToZnodePath.get(uri));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not unregister service rating\""
                    + "\", with URI \"" + uri + "\": " + ex.getLocalizedMessage());
        }
    }

    public String discoverServiceURI(String name) {
        try {
            String znode = "/services/" + name;

            List<String> uris = curatorFramework.getChildren().forPath(znode);
            return new String(curatorFramework.getData().forPath(ZKPaths.makePath(znode, uris.get(0))));
        } catch (Exception ex) {
            throw new RuntimeException("Service \"" + name + "\" not found: " + ex.getLocalizedMessage());
        }
    }

    public Map<String, String> discoverAllServices() {
        Map<String, String> serviceToUri = new HashMap<>();
        try {
            String znode = "/services";

            List<String> uris = curatorFramework.getChildren().forPath(znode);
            for (String uri : uris) {
                List<String> fullChildPaths = curatorFramework.getChildren().forPath(znode + "/" + uri);
                System.out.println("paths:" + fullChildPaths);
                serviceToUri.put(uri, new String(curatorFramework.getData().forPath(ZKPaths.makePath(znode+ "/" + uri, fullChildPaths.get(0)))));
            }
            System.out.println("all services " + serviceToUri);
            return serviceToUri;
        } catch (Exception ex) {
            throw new RuntimeException("Services not found: " + ex.getLocalizedMessage());
        }
    }
}
