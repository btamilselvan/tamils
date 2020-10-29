/*
const imagemin = require("imagemin");
const webp = require("imagemin-webp");

imagemin(["sources/*.jpg"], "images", {
  use: [
    webp({
      quality: 75
    })
  ]
}).then(function() {
  console.log("Images converted!");
});
*/

const webp = require('webp-converter');
const fs = require('fs');
const Sharp = require('sharp');

// this will grant 755 permission to webp executables
webp.grant_permission();

const result = webp.cwebp("sources/vegetales-varios.jpg", "images/vegetales-varios.webp", "-q 50");
result.then((response) => {
  console.log(response);
});
const QUALITY = 80;
/*
fs.readFile('sources/vegetales-varios.jpg', function (err, data) {
  Sharp(data)
    .webp({ quality: +QUALITY })
    .toBuffer().then(data => {
      fs.writeFileSync('images/vegetales-varios2.webp', data);
      console.log('sharp done...');
    });

});
*/
fs.readFile('sources/arroz-con-mariscos_2.jpg', function (err, data) {
  Sharp(data)
    .jpeg()
    .toBuffer().then(data => {
      fs.writeFileSync('images/arroz-con-mariscos_80.jpg', data);
      console.log('sharp done...');
    });

});

