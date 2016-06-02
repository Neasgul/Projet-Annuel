var LogType = function () {}

LogType.prototype.addLogExe = function (res) {
    return "The user has execute the file " + res;
};

LogType.prototype.addLogDelete = function (res) {
    return "The user has delete the file " + res;
};

LogType.prototype.addLogCreate = function (res) {
    return "The user has create the file " + res;
};

LogType.prototype.addLogRename = function (oldres, res) {
    return "The user has rename the file " + oldres + " in " + res;
};

LogType.prototype.addLogResearch = function (res) {
    return "The user has research in the browser " + res;
};
LogType.prototype.addLog = function (type, info, res) {
    return "" + info + res;
};

module.exports = LogType;
