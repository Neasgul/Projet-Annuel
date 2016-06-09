var express = require("express");
var bodyParser = require("body-parser");
var fs = require('fs');

var app = express();

app.use(bodyParser.urlencoded({
    "extended": false
}));

app.use(function (req,res,next) {
    console.log(req.path);
    next();
})

require('./api/Command')(app);
require('./api/User')(app);

app.get('/',function (req, res) {
    fs.readFile('public/index.html',function (err, data){
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
  res.send('404: Page Not Found', 404)
});
app.listen(8888, function () {
    console.log("Server started port 8888...");
});
