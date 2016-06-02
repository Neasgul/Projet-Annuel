var Sequelize = require('sequelize');

module.exports = new Sequelize("CommandLog","root","root",{
    pool : false,
    //dialect : 'mariadb',
    host : "localhost",
    //port : 55555
    port : 3306
});
