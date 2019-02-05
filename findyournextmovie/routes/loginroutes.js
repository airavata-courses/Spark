// Module for Database connection
var connection = require('../models/db_connection')
// Module for password encryption
var bcrypt = require('bcryptjs');

// API to register new user
exports.register = function(req,res){
  var today = new Date();
  var uuid = require('uuid');
  console.log("UUID is: "+ uuid.v4());
  var users={
    "user_id":uuid.v4(),
    "first_name":req.body.first_name,
    "last_name":req.body.last_name,
    "email":req.body.email,
    "dob": new Date(req.body.dob),
    "gender":req.body.gender,
    "country":req.body.country,
    "created":today,
    "modified":today
  };

  let hash = bcrypt.hashSync(req.body.password, 10);
  users["password"] = hash;
  console.log("hash is: " + hash);

  connection.query('INSERT INTO users SET ?',users, function (error, results, fields) {
  if (error) {
    console.log("error ocurred",error);
    res.send({
      "code":400,
      "failed":"error ocurred"
    })
  }else{
    console.log('The solution is: ', results);
    res.send({
      "code":200,
      "success":"user registered sucessfully"
        });
  }
  });
}

// API to login user
exports.login = function(req,res){
  var email= req.body.email;
  var password = req.body.password;
  connection.query('SELECT * FROM users WHERE email = ?',[email], function (error, results, fields) {
  if (error) {
    // console.log("error ocurred",error);
    res.send({
      "code":400,
      "failed":"error ocurred"
    })
  }else{
    // console.log('The solution is: ', results);
    if(results.length >0){
      if(bcrypt.compareSync(password, results[0].password)) {
        res.send({
          "code":200,
          "success":"login sucessfull",
          "user_id":results[0].user_id
            });
      } else {
        res.send({
          "code":204,
          "success":"Email and password does not match"
            });
      }

    }
    else{
      res.send({
        "code":404,
        "failed":"Email does not exits"
          });
    }
  }
  });
}
