const fs = require('fs-extra');
const webp = require('webp-converter');
const Sharp = require('sharp')

fs.copy('T:/Git_Repository/tamil/tamils/nodejs/webp/node_modules/webp-converter/', 'T:/temp/webp-converter/', err => {
    if (err) return console.error(err);
    console.log('success!');
});

var exec = require('child_process').execFile;

// exec('T:/temp/webp-converter/src/webpconverter.js', function (err, data) {
//     console.log(err)
//     console.log(data.toString());
// });
console.log("before execute...");
// const fileName = "T:/temp/webp-converter/src/webpconverter.js";
const fileName = "cwebp";
const params = ["T:/Git_Repository/tamil/tamils/nodejs/webp/sources/brocolis-al-graten.jpg",
    "T:/Git_Repository/tamil/tamils/nodejs/webp/images/brocolis-al-graten.webp", "-q 80"];

exec(fileName, params, { cwd: "T:/temp/webp-converter/bin/libwebp_linux/bin" }, (err, data) => {
    if (err) {
        console.log("unable to execute..");
        console.log(err);
    }
    else {
        console.log("hello "+data);
    }
});

// const result = webp.cwebp("sources/vegetales-varios.jpg","images/vegetales-varios.webp","-q 80");