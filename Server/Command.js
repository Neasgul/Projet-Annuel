var fs = require("fs");
var LogType = require("./LogType");
var filePath = "./Log.log";

var myLog = new LogType();

module.exports = function (app) {
    app.post('/exe', function (req, res) {
        var command = req.headers.command;
        fs.readFile(filePath, function (err, data) {
            if (err) {
                console.log(err);
            } else {
                fs.writeFile(filePath, myLog.addLogExe(data, command), function (err) {
                    if (err) {
                        console.log(err);
                    }
                });
            }
        });
        res.end();
    });
    
    app.post('/delete', function (req, res) {
        var command = req.headers.command;
        fs.readFile(filePath, function (err, data) {
            if (err) {
                console.log(err);
            } else {
                fs.writeFile(filePath, myLog.addLogDelete(data, command), function (err) {
                    if (err) {
                        console.log(err);
                    }
                });
            }
        });
        res.end();
    });
    
    app.post('/create', function (req, res) {
        var command = req.headers.command;
        fs.readFile(filePath, function (err, data) {
            if (err) {
                console.log(err);
            } else {
                fs.writeFile(filePath, myLog.addLogCreate(data, command), function (err) {
                    if (err) {
                        console.log(err);
                    }
                });
            }
        });
        res.end();
    });
    
    app.post('/rename', function (req, res) {
        var oldcommand = req.headers.oldcommand;
        var command = req.headers.command;
        fs.readFile(filePath, function (err, data) {
            if (err) {
                console.log(err);
            } else {
                fs.writeFile(filePath, myLog.addLogRename(data, oldcommand, command), function (err) {
                    if (err) {
                        console.log(err);
                    }
                });
            }
        });
        res.end();
    });
    
    app.post('/research', function (req, res) {
        var command = req.headers.command;
        fs.readFile(filePath, function (err, data) {
            if (err) {
                console.log(err);
            } else {
                fs.writeFile(filePath, myLog.addLogResearch(data, command), function (err) {
                    if (err) {
                        console.log(err);
                    }
                });
            }
        });
        res.end();
    });
};