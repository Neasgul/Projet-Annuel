var express = require("express");
var bodyParser = require("body-parser");
var favicon = require('serve-favicon');
var fs = require('fs');
var app = express();
app.use(express.static(__dirname+'/public'));

//Define the port to use
var DEFAULT_PORT = 8888;
var port = process.env.PORT || DEFAULT_PORT

//set the view engine to ejs
app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({
    "extended": false
}));
app.use(favicon(__dirname+'/favicon.ico'));
app.use(function (req,res,next) {
    console.log(req.path);
    next();
})

require('./api/Command')(app);
require('./api/User')(app);

app.get('/',function (req, res) {
    res.render('index');
    });

app.get('/download',function (req, res) {
var file = __dirname + '/Command.log';
  res.download(file); // Set disposition and send it.

})
// catch 404 and forward to error handler
app.use(function(req, res, next) {
    res.render('404');
    //res.status(404).send('404 : Page Not Found')
});

app.listen(port, function () {
    console.log("Server started port "+port);
});
