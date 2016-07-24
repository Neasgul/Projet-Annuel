var DBUtils = require('../DBUtils');

module.exports = function (app) {
    app.post('/user/add',function (req, res) {
        var UserUtils = DBUtils.User;
        var uuid = req.body.uuid;
        var name = req.body.name;

        var check = CommandUtils.CheckUUID(uuid_user)
        if(!check){
            UserUtils.CreateUser(uuid,name, function (result, err) {
                if(result){
                    res.json({
                        code:0,
                        result:result
                    })
                }else {
                    res.json({
                        code:1,
                        err : err
                    })
                }
            })
        }
        else {
            res.json({
                err:"UUID already use"
            })
        }
    })
    app.post('/user/delete', function (req,res) {
        var UserUtils = DBUtils.User;
        var uuid = req.body.uuid;
        var check = CommandUtils.CheckUUID(uuid_user)
        if(check){
            UserUtils.DeleteUser(uuid,function (result,err) {
                if(result){
                    res.json({
                        code:0,
                        result:result
                    })
                }else {
                    res.json({
                        code:1,
                        err : err
                    })
                }
            })
        }
        else {
            res.json({
                err:"UUID not recognisable"
            })
        }
    })
};
