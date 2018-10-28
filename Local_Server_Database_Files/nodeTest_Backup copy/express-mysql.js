var express = require('express');
var app = express();
var mysql = require('mysql');
var GetData = require('./getdata');
var connection = mysql.createConnection({   //config your local database 
host     : 'localhost',  
user     : 'root',  
password : 'root',  
database : 'db1'
});
connection.connect();  //keep connectting
var server = app.listen(3000, function () {  
var host = server.address().address;  
var port = server.address().port;  
console.log('Css436 app listening at http://%s:%s', host, port);
console.log('Console Log:\n');
});
app.use('/get',GetData);   //here is express middle side
