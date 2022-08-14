const Sharp = require('sharp');
const fs = require('fs');

console.log('start...');

const QUALITY = 75

fs.readFile('T:/Work/CreativeAthletes/Oster_Recetas/pic123.jpg', function (err, data) {
    Sharp(data)
        .webp({ quality: +QUALITY })
        .toBuffer().then(data => {
        fs.writeFileSync('T:/Work/CreativeAthletes/Oster_Recetas/result.jpg', data);
        console.log('sharp done...');
        });
});

console.log('end...');

//run using "node webp_local.js"