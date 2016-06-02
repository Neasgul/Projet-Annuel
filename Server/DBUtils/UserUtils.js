var models = require("../models");



var UserUtils = function () {}

UserUtils.prototype.CreateUser = function (firstname, lastname, familly,callback) {
    var User = models.User;
    User.create({
        lastname : lastname,
        firstname : firstname,
        id_familly : familly
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
        where : uuid_user
    }).then(function (result) {
        callback(result);
    }).catch(function (err) {
        callback(undefined, err);
    })
};

module.exports = new UserUtils;
