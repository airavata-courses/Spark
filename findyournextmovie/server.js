var http = require('http');
var app = require('./app');

http.createServer(app).listen(8080);

// function (req, res) {
//   res.writeHead(200, {'Content-Type': 'text/html'});
//   res.end('Hello World!');
// }
