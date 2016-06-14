var express = require("express");
var bodyParser = require("body-parser");
var favicon = require('serve-favicon');
var fs = require('fs');

var app = express();
var DEFAULT_PORT = 8888;
var port = process.env.PORT || DEFAULT_PORT

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
    fs.readFile('Server/public/index.html',function (err, data){
        res.writeHeader(200, {"Content-Type": "text/html"});
        res.write(data);
        res.end();
    });

})
app.get('/download',function (req, res) {
var file = __dirname + '/Command.log';
  res.download(file); // Set disposition and send it.

})
// catch 404 and forward to error handler
app.use(function(req, res, next) {
  res.status(404).send('404 : Page Not Found')
});

app.listen(port, function () {
    console.log("Server started port "+port);
});
