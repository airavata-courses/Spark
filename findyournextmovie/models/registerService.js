var request = require('request');

function register() {
  const args = require('minimist')(process.argv);

  request.post(
      'http://149.165.169.102:5000/services/register?name=login&uri='+'http://' + args['LOGIN_IP'] + ':8080',
      function (error, response, body) {
          if (!error && response.statusCode == 200) {
              console.log("success");
          }
      }
  );
}

module.exports =  {register};
