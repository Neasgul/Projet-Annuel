var models = require("../models");



var UserUtils = function () {}

UserUtils.prototype.CreateUser = function (uuid, name,callback) {
    var User = models.User;
    User.create({
        token : uuid,
        name : name
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined,err);
    })
};
UserUtils.prototype.GetAllUser = function (callback) {
    var User = models.User;
    User.findAll().then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined, err);
    })
};

UserUtils.prototype.GetUserbyUUID = function (uuid_user,callback) {
    var User = models.User;
    User.findAll({
        where : {token:uuid_user}
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined, err);
    })
};

module.exports = new UserUtils;
