var LogType = function () {}

LogType.prototype.addLogExe = function (res) {
    return "### EXE ### The user has execute the file " + res;
};

LogType.prototype.addLogDelete = function (res) {
    return "### DELETE ### The user has delete the file " + res;
};

LogType.prototype.addLogCreate = function (res) {
    return "### CREATE ### The user has create the file " + res;
};

LogType.prototype.addLogRename = function (oldres, res) {
    return "### RENAME ### The user has rename the file " + oldres + " in " + res;
};

LogType.prototype.addLogResearch = function (res) {
    return "### RESEARCH ### The user has research in the browser " + res;
};
LogType.prototype.addLog = function (type, info, res) {
    return "### "+ type +" ### "+info+ res;
};

module.exports = LogType;
