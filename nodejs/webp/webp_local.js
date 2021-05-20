// dependencies
const AWS = require('aws-sdk');
const Sharp = require('sharp');

// get reference to S3 client
const s3 = new AWS.S3();

//get reference to SSM client
var ssm = new AWS.SSM();

const singleImageParam = {
    Bucket: "recipe-original-pics-dev",
    Key: "original/S4655-1.jpg"
  };
  
  s3.getObject(singleImageParam, function (err, data) {
    if (err) {
      console.log(err, err.stack);
    }
    else {
      console.log("image downloaded successfully...");
    //   console.log(data.Body);
    }
  });

  var ssmParams = {
    Name: '/config/application/aws/s3/pics',
    WithDecryption: false
};

console.log("get ssm parameters...");

let destBucket;

// const ssmResponse = await ssm.getParameter(ssmParams).promise();
// destBucket = ssmResponse.Parameter.Value;


console.log("get ssm parameters...");


async function myfunction() {
    console.log('Inside of myfunction');
}

function start() {
    return myfunction();
}

// Call start
(async() => {
    console.log('before start');
  
    await start();
    
    console.log('after start');
  })();


async function getSsmParameters() {
    var AWSS = require('aws-sdk');
    AWSS.config.update({region:'sa-east-1'});
    var ssmm = new AWSS.SSM();
    var ssmParamss = {
        Name: '/config/application/aws/s3/pics',
        WithDecryption: false
    };
    console.log("before start 1 ");
    const ssmResponses = await ssmm.getParameter(ssmParamss).promise();
    console.log("after start 1 ");
    console.log(ssmResponses.Parameter.Value);
};

getSsmParameters();
