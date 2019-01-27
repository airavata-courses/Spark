exports.register = function(req,res){
  // console.log("req",req.body);
  var today = new Date();
  var users={
    "first_name":req.body.first_name,
    "last_name":req.body.last_name,
    "email":req.body.email,
    "password":req.body.password,
    "dob":req.body.dob,
    "gender":req.body.gender,
    "country":req.body.country,
    "language":req.body.language,
    "created":today,
    "modified":today
  }
}
