// dependencies
const Sharp = require("sharp");
// const fs = require("fs");
// const path = require("path");
const AWS = require("aws-sdk");
// var async = require("async");

const QUALITY = 50;
const S3_BUCKET_NAME = "trocks-images";
// get reference to S3 client
const s3 = new AWS.S3();

//uncomment the below block to convert the images from the local directory
/*
fs.readdir('T:/temp/yumitos/images/original/', function(err, fileNames) {
    fileNames.forEach(function(fileName){
        console.log(fileName);

        fs.readFile('T:/temp/yumitos/images/original/'+fileName, function (err, data) {

            Sharp(data)
            .webp({ quality: +QUALITY })
            .toBuffer().then(data => {
                fs.writeFileSync('T:/temp/yumitos/images/converted/'+fileName, data);
                console.log('sharp done...');
              });
          });
    });
});
*/

async function process() {
  console.log("begin process");
  var params = {
    Bucket: S3_BUCKET_NAME,
  };

  s3.listObjects(params, function (err, data) {
    if (err) {
      console.log("unable to list objects");
      console.log(err, err.stack);
    } else {
      data.Contents.forEach(async (c) => {
        console.log("process: ", c.Key);
        await downloadConvertUpload(c);
      });
    }
  });
  console.log("end process");
}

async function downloadConvertUpload(s3Object) {
  if (s3Object.Key.split("/").endsWith(".webp")) {
    const param = {
      Bucket: S3_BUCKET_NAME,
      Key: s3Object.Key,
    };

    const img = await s3.getObject(param).promise();

    const sharpImageBuffer = await Sharp(img.Body)
      .webp({ quality: +QUALITY })
      .toBuffer();

    let destKey = s3Object.Key.split("/")[1];

    destKey = "optimized/" + destKey;
    console.log("dest key is " + destKey);

    await s3
      .putObject({
        Body: sharpImageBuffer,
        Bucket: S3_BUCKET_NAME,
        ContentType: "image/webp",
        Key: destKey,
      })
      .promise();
  }
}
process();
