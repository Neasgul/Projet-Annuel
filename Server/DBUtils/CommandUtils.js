var models = require("../models");

var CommandUtils = function () {}

CommandUtils.prototype.CreateCommandEntry = function (uuid_user, level,message, callback) {
    var command = models.Command;
    command.create({
        uuid_user : uuid_user,
        level : level,
        message : message
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined,err);
    })
};

CommandUtils.prototype.GetAllCommand = function (callback) {
    var command = models.Command;
    command.findAll().then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined,err);
    })
};

CommandUtils.prototype.GetCommandbyUUID = function (uuid_user, callback) {
    var command = models.Command;
    command.findAll({
        where: {
            uuid_user : uuid_user
            }
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined,err);
    })
};


module.exports = new CommandUtils;
