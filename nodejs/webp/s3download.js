const AWS = require("aws-sdk");
const fs = require("fs");

const downloadPath = "T:/temp/yumitos/images/original/";

const bucket_name = "my-trocks-images";

var params = {
  Bucket: bucket_name,
  MaxKeys: 1500,
};
let count = 0;
// get reference to S3 client
const s3 = new AWS.S3();
console.log("begin");
s3.listObjects(params, function (err, data) {
  if (err) {
    console.log("unable to list objects");
    console.log(err, err.stack);
  } else {
    // console.log(data);
    data.Contents.forEach((c) => {
      console.log(c.Key);
      download(c);
      count++;
    });
  }
});

console.log("total " + count);

function download(c) {
  if (c.Key.split("/")[1].endsWith(".webp")) {
    const imgparam = {
      Bucket: bucket_name,
      Key: c.Key,
    };
    s3.getObject(imgparam, function (err, data) {
      if (err) {
        console.log(err);
        return;
      }
      fs.writeFileSync(downloadPath + c.Key.split("/")[1], data.Body);
    });
  }
}
