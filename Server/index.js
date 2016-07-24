var express = require("express");
var bodyParser = require("body-parser");
var favicon = require('serve-favicon');
var fs = require('fs');
var app = express();
app.use(express.static(__dirname+'/public'));
app.set('views',__dirname+'/views')
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

app.get('/about',function (req, res) {
    res.render('about');
})

app.get('/plugins',function (req, res) {
    var path = __dirname+"/Plugins"
    fs.readdir(path, function (err, items) {
        if(items){
            console.log(items);
            res.render('plugins',{pluginslist:items})
        }else {
            console.log(items);
            res.render('plugins',{pluginslist:null});
        }
    })

})
app.get('/download/',function (req, res) {
    console.log(__dirname);
var file = 'YVOX/YVOX.jar';
  res.download(file); // Set disposition and send it.

})
app.get('/download/:plugin',function (req, res) {
var file = __dirname + '/Plugins/'+req.params.plugin;
  res.download(file); // Set disposition and send it.

})


// catch 404 and forward to error handler
app.use(function(req, res, next) {
    res.render('404');
});

app.listen(port, function () {
    console.log("Server started port "+port);
});
