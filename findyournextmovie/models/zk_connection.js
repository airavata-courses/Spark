var zookeeper = require('node-zookeeper-client');

var client = zookeeper.createClient('149.165.156.198:2181');

// function to discover services
function zkGetData(client, path) {
    client.getData(path,
        function (event) {
          console.log('Got event: %s.', event);
        },
        function (error, data, stat) {
          if (error) {
            console.log("Failed to get data for login", error);
            return;
          }

          console.log('Got data: %s', data.toString('utf8'));
        }
    );
}
function zkCreateNode(client, path, data) {
  client.create(path, new Buffer(data), zookeeper.CreateMode.EPHEMERAL, function (error) {
      if (error) {
          console.log('Failed to create node: %s due to: %s.', error);
      } else {
          console.log('Node: %s is successfully created.');
      }
    }
  );
}

client.connect();

client.once('connected', function () {
    console.log('Connected to the server.');
    zkCreateNode(client, '/services/login', 'http://localhost:8080/');
    // zkGetData(client, '/services/login');
});

console.log("Tried connecting to zookeeper");
module.exports =  {client, zkGetData};
