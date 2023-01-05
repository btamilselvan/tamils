// dependencies
const AWS = require("aws-sdk");
const Sharp = require("sharp");

// get reference to S3 client
const s3 = new AWS.S3();

//get reference to SSM client
// var ssm = new AWS.SSM();

exports.handler = async (event, context, callback) => {
  const srcBucket = event.Records[0].s3.bucket.name;

  /*The actual image name may have had spaces init. 
  Those spaces would have been encoded when the AWS triggered this event. 
  Decode the image before processing it.
  */
  const srcKey = decodeURIComponent(
    event.Records[0].s3.object.key.replace(/\+/g, " ")
  );
  console.log("src bucket is " + srcBucket + ", src key is " + srcKey);

  try {
    const params = {
      Bucket: srcBucket,
      Key: srcKey,
    };

    // get the image from S3
    const img = await s3.getObject(params).promise();

    console.log("pic downloaded from src bucket.. ready to convert...");

    // //get pics bucket name from SSM
    // var ssmParams = {
    //   Name: "/config/application/aws/s3/pics",
    //   WithDecryption: false,
    // };

    // console.log("get ssm parameters...");

    // let destBucket;

    // const ssmResponse = await ssm.getParameter(ssmParams).promise();
    // destBucket = ssmResponse.Parameter.Value;

    // console.log("destbucket is " + destBucket);

    let destKey = srcKey.split("/")[1];
    // destKey = destKey.split(".")[0];
    destKey = "webp/" + destKey + ".webp";

    // let destJepgKey = srcKey.replace("original", "webp");

    const QUALITY = 50;

    // console.log("dest jpeg key is " + destJepgKey);

    // convert the image to webp
    const sharpImageBuffer = await Sharp(img.Body)
      .webp({ quality: +QUALITY })
      .toBuffer();

    console.log(
      "Image conversion successful...before writing the converted image..."
    );

    // write the converted image to the picsbcuket webp folder
    await s3
      .putObject({
        Body: sharpImageBuffer,
        Bucket: destBucket,
        ContentType: "image/webp",
        Key: destKey,
      })
      .promise();

    console.log("webp uploaded successfully...");

    // compress the source image
    // const sharpJepgImageBuffer = await Sharp(img.Body)
    //   .jpeg({ quality: +QUALITY })
    //   .toBuffer();

    // write the compressed jpeg image to the picsbcuket webp folder
    // await s3
    //   .putObject({
    //     Body: sharpJepgImageBuffer,
    //     Bucket: destBucket,
    //     ContentType: "image/jpeg",
    //     Key: destJepgKey,
    //   })
    //   .promise();

    // console.log("converted jpeg uploaded successfully...");
  } catch (error) {
    console.log("download failed");
    console.log(error);
    return;
  }
  console.log("completed successfully");
};
