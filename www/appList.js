var cordova = require('cordova');

var AppList = function() {};

AppList.prototype.getAppList = function (success, error) {
    cordova.exec(success, error, 'AppList', 'getAppList', []);
};

var appList = new AppList();
module.exports = appList;