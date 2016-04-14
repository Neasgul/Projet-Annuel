var LogType = function () {}

LogType.prototype.addLogExe = function (data, res) {
    return data + "### EXE ### " + new Date() + " ### The user has execute the file " + res + "\n";
};

LogType.prototype.addLogDelete = function (data, res) {
    return data + "### DELETE ### " + new Date() + " ### The user has delete the file " + res + "\n";
};

LogType.prototype.addLogCreate = function (data, res) {
    return data + "### CREATE ### " + new Date() + " ### The user has create the file " + res + "\n";
};

LogType.prototype.addLogRename = function (data, oldres, res) {
    return data + "### RENAME ### " + new Date() + " ### The user has rename the file " + oldres + " in " + res + "\n";
};

LogType.prototype.addLogResearch = function (data, res) {
    return data + "### RESEARCH ### " + new Date() + " ### The user has research in the browser " + res + "\n";
};

module.exports = LogType;