const AWS = require('aws-sdk');
const fs = require('fs');

const downloadPath = "T:/temp/oster/images/original/";

var params = {
    Bucket: "recipe-original-pics-dev",
    MaxKeys: 1500
};
let count = 0;
// get reference to S3 client
const s3 = new AWS.S3();

await s3.listObjects(params, function (err, data) {
    if (err) {
      console.log(err, err.stack);
    }
    else {
      // console.log(data);
      data.Contents.forEach(c => {
        console.log(c.Key);
        // download(c);
        count ++;
      });
    }
  });

  console.log('total '+count);

function download(c){
    if (c.Key.split('/')[1].endsWith('.jpg')) {
        const imgparam = {
            Bucket: 'recipe-original-pics-dev',
            Key: c.Key
        };
        s3.getObject(imgparam, function (err, data) {
            if (err) {
              console.log(err);
              return;
            }
            fs.writeFileSync(downloadPath+c.Key.split('/')[1], data.Body);
          });
    }
}