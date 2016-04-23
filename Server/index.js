var express = require("express");
var bodyParser = require("body-parser");

var app = express();

app.use(bodyParser.urlencoded({
    "extended": false
}));

app.use(function (req,res,next) {
    console.log(req.path);
    next();
})

require('./Command')(app);

app.listen(8888, function () {
    console.log("Server started port 8888...");
});
