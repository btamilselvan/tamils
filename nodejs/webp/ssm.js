// dependencies
const AWS = require('aws-sdk');
AWS.config.update({region:'us-east-1'});
var ssm = new AWS.SSM();

var params = {
    Name: '/config/application/aws/s3/pics',
    WithDecryption: false
};
ssm.getParameter(params, function (err, data) {
    if (err) console.log(err, err.stack);
    else console.log(data.Parameter.Value);
});
