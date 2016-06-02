var sequelize = require("./sequelize");

module.exports = sequelize.import("user",function (sequelize, Datatypes) {
    return sequelize.define("User",{
        id_user: {
            type : Datatypes.BIGINT,
            primaryKey : true,
            autoIncrement : true
        },
        token : {
            type : Datatypes.UUID
        },
        name : {
            type : Datatypes.STRING
        }
    },{
        paranoid : true,
        freezeTableName : true,
        underscored : true
    });
});
