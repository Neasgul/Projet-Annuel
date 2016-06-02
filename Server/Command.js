var DBUtils = require('./DBUtils');

String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
}

module.exports = function (app) {

    app.post('/command', function (req, res) {
        var CommandUtils = DBUtils.Command;
        var uuid = req.headers.uuid;
        var level = req.headers.level;
        var message = req.headers.message;
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
    });
    app.get('/CommandLog/:uuid_user',function (req,res) {
        var uuid_user = req.params.uuid_user;
        var CommandUtils = DBUtils.Command;
        CommandUtils.GetCommandbyUUID(uuid_user, function (result, err) {
            if(result){
                console.log(result);
                res.json({
                    code : 0,
                    result : result
                })
            }
            else {
                console.log(err);
                res.json({
                    code : 1,
                    result : err
                })
            }
        })
    });
};
