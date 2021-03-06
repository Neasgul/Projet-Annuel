var DBUtils = require('../DBUtils');

String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
}

module.exports = function (app) {

    app.post('/command', function (req, res) {
        var CommandUtils = DBUtils.Command;
        var uuid = req.body.uuid;
        var level = req.body.level;
        var message = req.body.message;
        var check = CommandUtils.CheckUUID(uuid)
        if(check){
            CommandUtils.CreateCommandEntry(uuid, level, message, function (result, err) {
                if(result){
                    res.json({
                        code: 0,
                        result: result
                    })
                }else {
                    res.json({
                        code: 1,
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
    });
    app.get('/CommandLog/:uuid_user',function (req,res) {
        var uuid_user = req.params.uuid_user;
        var CommandUtils = DBUtils.Command;

        var check = CommandUtils.CheckUUID(uuid_user)
        if(check){
            CommandUtils.GetCommandbyUUID(uuid_user, function (result, err) {
                if(result){
                    res.json(result)
                }
                else {
                    res.json({
                        result : err
                    })
                }
            })
        }
        else {
            res.json({
                err:"UUID not recognisable"
            })
        }
    });
    app.get('/CommandLog/:uuid_user/:level',function (req,res) {
        var uuid_user = req.params.uuid_user;
        var level = req.params.level;
        var CommandUtils = DBUtils.Command;

        var check = CommandUtils.CheckUUID(uuid_user)
        if(check){
            CommandUtils.GetCommandbyLevel(uuid_user, level,function (result, err) {
                if(result){
                    res.json(result)
                }
                else {
                    res.json({
                        result : err
                    })
                }
            })
        }
        else {
            res.json({
                err:"UUID not recognisable"
            })
        }
    });
};
