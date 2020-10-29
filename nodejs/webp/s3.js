const AWS = require('aws-sdk');
const Sharp = require('sharp');

let s = "original/axy.jpb";
s = s.replace(".jpg", ".webp").replace('original/', 'webp/');
// console.log(s);

const QUALITY = 50;

var params = {
  Bucket: "recipe-original-pics-dev",
  MaxKeys: 1500
};
let count = 0;
// get reference to S3 client
const s3 = new AWS.S3();

s3.listObjects(params, function (err, data) {
  if (err) {
    console.log(err, err.stack);
  }
  else {
    // console.log(data);
    data.Contents.forEach(c => {
      // console.log(c.Key);
      // convert(c);
      compressJpg(c);
    });
  }
});

function compressJpg(c) {
  if (c.Key.split('/')[1].endsWith('.jpg')) {
    const destKey = c.Key.replace('original/', 'webp/');
    const imgparam = {
      Bucket: 'recipe-original-pics-dev',
      Key: c.Key
    };
    s3.getObject(imgparam, function (err, data) {
      if (err) {
        console.log(err);
        return;
      }
      Sharp(data.Body)
        .jpeg({ quality: +QUALITY })
        .toBuffer().then(res => {

          s3.putObject({
            Body: res,
            Bucket: 'rpicsd',
            ContentType: 'image/jpeg',
            Key: destKey
          }).promise().then(data => {
            count = count + 1;
            console.log(count + " upload success " + destKey);
          });
        });
    });
  }
}

function convert(c) {

  if (c.Key.split('/')[1].endsWith('.jpg')) {
    const destKey = c.Key.replace(".jpg", ".webp").replace('original/', 'webp/');
    // console.log(c.Key);
    // console.log(destKey);

    const imgparam = {
      Bucket: 'recipe-original-pics-dev',
      Key: c.Key
    };
    s3.getObject(imgparam, function (err, data) {
      if (err) {
        console.log(err);
        return;
      }
      Sharp(data.Body)
        .webp({ quality: +QUALITY })
        .toBuffer().then(res => {

          s3.putObject({
            Body: res,
            Bucket: 'rpicsd',
            ContentType: 'image/webp',
            Key: destKey
          }).promise().then(data => {
            count = count + 1;
            console.log(count + " upload success " + destKey);
          });
        });
    });
  }
}