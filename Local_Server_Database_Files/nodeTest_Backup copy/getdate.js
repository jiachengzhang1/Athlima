var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var connection = mysql.createConnection({  //config your local database    
host     : 'localhost',    
user     : 'root',    
password : '19960504',    
database : 'db'  
});

// router middle side
router.use(function timeLog(req, res, next) {  
console.log('Time: ', Date.now());  //get current time  
next();
});

router.get('/', function(req, res) {
res.send('hello');
});

router.get('/id=:id', function(req, res) {
//connection.connect();
var  sql = 'SELECT * FROM user WHERE id = ' + req.params.id;    
console.log('ID:', req.params.id);  //搜索的ID    
connection.query(sql,function (err, result) {        
if(err){          
console.log('[SELECT ERROR] - ',err.message);          
next('router');
return;       
}       
console.log('--------------------------SELECT----------------------------');       
console.log(result);       
console.log('------------------------------------------------------------\n\n');        
// connection.end();       
res.send(result); 
});
});

router.get('/searchEvent=:name', function(req, res) {
//connection.connect();
var  sql = 'SELECT * FROM event WHERE name = ' + req.params.name; 
connection.query(sql,function (err, result) {            
console.log(result);              
// connection.end();       
res.send(result); 
});
});

router.get('/searchAllEvent', function(req, res) {
//connection.connect();
var  sql = 'SELECT * FROM event'; 
connection.query(sql,function (err, result) {            
console.log(result);              
// connection.end();       
res.send(result); 
});
});

router.get('/searchUser=:email', function(req, res) {
var  sql = 'SELECT * FROM user WHERE emailAddress = ' + req.params.email; 
connection.query(sql,function (err, result) { 
	console.log(result); 
	res.send(result); 
});
});

router.get('/searchAllUser', function(req, res) {
var  sql = 'SELECT * FROM user'; 
connection.query(sql,function (err, result) { 
	console.log(result); 
	res.send(result); 
});
});

router.get('/searchSports=:name', function(req, res) {
var  sql = 'SELECT * FROM sport WHERE sportName = ' + req.params.name; 
connection.query(sql,function (err, result) { 
	console.log(result); 
	res.send(result); 
});
});

router.get('/searchAllSports', function(req, res) {
var  sql = 'SELECT * FROM sport'; 
connection.query(sql,function (err, result) { 
	console.log(result); 
	res.send(result); 
});
});

router.get('/createUser=:email,pwd=:pwd,name=:name', function(req, res) {
});
router.get('/createUser=:email,pwd=:pwd,name=:name', function(req, res) {});

module.exports = router;

