var request = require('request');
const ip = require('ip');

function register() {
  request.post(
      'http://149.165.169.102:5000/services/register?name=login&uri='+'http://' + ip.address() + ':8080',
      function (error, response, body) {
          if (!error && response.statusCode == 200) {
              console.log("success");
          }
      }
  );
}

module.exports =  {register};
