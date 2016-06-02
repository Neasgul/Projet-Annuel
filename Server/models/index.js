var sequelize = require("./sequelize");
var User = require('./User');
var Command = require('./Command');

sequelize.sync();
module.exports = {
    "sequelize" : sequelize,
    "User" : User,
    "Command" : Command
};
