package com.serviceRegistry.controller;

import com.serviceRegistry.service.ZooKeeperServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RequestMapping("/services")
@RestController
public class serviceRegistryController {

    @Autowired
    private ZooKeeperServices zooKeeperServices;

    @PostMapping("/register")
    public void registerService(@RequestParam(name = "name") String name, @RequestParam(name = "uri") String uri) {
        zooKeeperServices.registerService(name, uri);
    }

    @PostMapping("/unregister")
    public void unregisterService(@RequestParam(name = "uri") String uri) {
        zooKeeperServices.unregisterService(uri);
    }

    @GetMapping("/discover")
    public String discoverServiceByName(@RequestParam(name = "name") String name) {
        return zooKeeperServices.discoverServiceURI(name);
    }

    @GetMapping("/discoverAll")
    public Map<String, String> discoverAllService() {
        return zooKeeperServices.discoverAllServices();
    }
}
