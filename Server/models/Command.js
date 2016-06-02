var sequelize = require("./sequelize");

module.exports = sequelize.import("command",function (sequelize, Datatypes) {
    return sequelize.define("Command",{
        id_Command: {
            type : Datatypes.BIGINT,
            primaryKey : true,
            autoIncrement : true
        },
        id_user: {
            type : Datatypes.BIGINT
        },
        timestamps : {
            type : Datatypes.DATE
        },
        level : {
            type : Datatypes.STRING
        },
        message : {
            type : Datatypes.STRING
        }
    },{
        paranoid : true,
        freezeTableName : true,
        underscored : true
    });
});
