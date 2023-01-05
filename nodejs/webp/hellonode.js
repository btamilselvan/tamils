var async = require("async");

console.log('start');

function add(x){
    console.log('inside add - async');
    return new Promise((resolve)=>{
        setTimeout(() => {
            resolve(Math.pow(x,2));
        }, 2000);
    })
}

async function output(x){
    const ans = await add(x);
    console.log(ans);
}

output(10);
console.log('end');
