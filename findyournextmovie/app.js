var express     = require('express');
var app         = express();
// var path        = require('path');
var bodyParser  = require('body-parser');
var login = require('./routes/loginroutes');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var router = express.Router();
// test route
router.get('/', function(req, res) {
    res.json({ message: 'welcome to our upload module apis' });
});

app.use('/api', router);

module.exports = app;
