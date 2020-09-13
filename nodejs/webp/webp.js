// dependencies
const AWS = require('aws-sdk');
// const webp = require('webp-converter');
// const fs = require('fs');
const Sharp = require('sharp');

console.log('welcome...');
// get reference to S3 client
const s3 = new AWS.S3();

exports.handler = async (event, context, callback) => {
    const srcBucket = event.Records[0].s3.bucket.name;
    const srcKey = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, " "));
    // const srcBucket = "picsbucket";
    // const srcKey = "original/stock-photo.jpg";
    // const dstBucket = srcBucket + "-resized";
    // const dstKey    = "resized-" + srcKey;
    console.log("source bucket is " + srcBucket);
    console.log("source key is " + srcKey);

    // Download the image from the S3 source bucket. 
    const downloadPath = "/tmp/" + srcKey.split("/")[1];
    console.log("downloadPath is " + downloadPath);
    try {
        const params = {
            Bucket: srcBucket,
            Key: srcKey
        };
        console.log("before get...");
        const img = await s3.getObject(params).promise();
        console.log("before write...");
        // fs.writeFileSync(downloadPath, img.Body);
        console.log("download success");

        const destBucket = "picsbucket";
        let destKey = srcKey.split("/")[1];
        destKey = destKey.split(".")[0];

        const webpPath = "/tmp/" + destKey + ".webp";

        destKey = "optimized/" + destKey + ".webp";
        console.log("dest key is " + destKey);
        console.log("downloadPath is " + downloadPath);

        const QUALITY = 75

        // let sharpImageBuffer;
        const sharpImageBuffer = await Sharp(img.Body)
            .webp({ quality: +QUALITY })
            .toBuffer();
        // Sharp(img.Body)
        //     .webp({ quality: +QUALITY })
        //     .toBuffer().then(data => {
        //         sharpImageBuffer = data;
        //     });
        //   sharpImageBuffer.

        await s3.putObject({
            Body: sharpImageBuffer,
            Bucket: destBucket,
            ContentType: 'image/webp',
            Key: destKey
        }).promise();

        console.log("done...");
        // s3.putObject({
        //     Body: sharpImageBuffer,
        //     Bucket: destBucket,
        //     ContentType: 'image/webp',
        //     Key: destKey
        // }).promise().then(data => {
        //     console.log("upload success" + data);
        // });

        // this will grant 755 permission to webp executables
        // webp.grant_permission();
        /*
        const result = await webp.cwebp(downloadPath, webpPath, "-q 80");
        result.then(response => {
            console.log('webp conversion result ' + response);

            fs.readFile(webpPath, function (err, data) {
                if (err) {
                    console.log(err, "error");
                    return;
                }
                const destParams = {
                    Bucket: destBucket,
                    Key: destKey,
                    Body: data
                };
                s3.upload(destParams, function (err, data) {
                    if (err) {
                        console.log("upload failed");
                        console.log(err, "failed");
                    } else {
                        console.log("upload successful.");
                        console.log(data);
                    }
                });
            });
        }) */
    } catch (error) {
        console.log("download failed");
        console.log(error);
        return;
    }


    console.log("completed successfully");
    /*
webp.buffer2webpbuffer(buf, ".jpg", "-q 80").then(result => {

const destParams = {
Bucket: destBucket,
Key: destKey,
Body: result
};

s3.upload(destParams, function (err) {
if (err) {
console.log(err, "failed");
return;
} else {
console.log("successfully converted to webp");
}
});
});*/
};
