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

CommandUtils.prototype.GetCommandbyLevel = function (uuid_user, level, callback) {
    var command = models.Command;
    command.findAll({
        where: {
            uuid_user : uuid_user,
            level : level
            }
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined,err);
    })
};

CommandUtils.prototype.CheckUUID = function (uuid) {
    var command = models.Command;
    command.findAll({
        where: {
            uuid_user : uuid
        }
    }).then(function (result){
        if (result) {
            return true;
        }
        return false
    }).catch(function (err) {
        return false
    })
};

module.exports = new CommandUtils;
