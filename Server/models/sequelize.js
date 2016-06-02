var Sequelize = require('sequelize');

module.exports = new Sequelize("Calendrier","root","root",{
    pool : false,
    //dialect : 'mariadb',
    host : "localhost",
    //port : 55555
    port : 3306
});
