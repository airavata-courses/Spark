// Database connection

var mysql      = require('mysql');
var credentials = require('../constants/credentials');

var connection = mysql.createConnection(credentials);

connection.connect(function(err){
if(!err) {
    console.log("Database is connected ... nn");
} else {
    console.log("Error connecting database ... nn" + err.message);
}
});

module.exports = connection;
