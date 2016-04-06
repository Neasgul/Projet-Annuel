var express = require("express");
var bodyParser = require("body-parser");

var app = express();

app.listen(8888, function () {
    console.log("Server started port 8888...");
})