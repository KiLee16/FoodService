let a: number = 10;
let b: number = 20;
let c: String = "abhi";
//let vs var
// let will have scope of block
// var will have scope of file

console.log(a);
console.log(b);
console.log(c);

//ts code for writing purpose and for execution we have to use js since browser will understand only js files
//

const test = () => 10 + 20;
//function name / content
console.log(test());

var test1 = function () {
  return 10 + 20;
};
console.log(test1());
