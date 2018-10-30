var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var connection = mysql.createConnection({  //config your local database    
host     : 'localhost',    
user     : 'root',    
password : 'root',    
database : 'db'  
});

// router middle side
router.use(function timeLog(req, res, next) {  
	console.log('Time: ', Date.now());  //get current time  
	next();
});

router.get('/', function(req, res) {
	res.send('You dont have any parameter');
});

router.get('/id=:id', function(req, res) {
	//connection.connect();
	console.log('ID:', req.params.id);  //搜索的ID    
	connection.query("SELECT * FROM user WHERE id = ?", [req.params.id], function (err, result) {        
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

//query by event name
router.get('/searchEvent=:name', function(req, res) {
	//connection.connect();
	connection.query("SELECT * FROM event WHERE name = ?", [req.params.name],function (err, result) {            
		console.log('--------------------------SELECT----------------------------');       
		console.log(result);       
		console.log('------------------------------------------------------------\n\n');             
		// connection.end();       
		res.send(result); 
	});
});

//query for all events
router.get('/searchAllEvent', function(req, res) {
	//connection.connect();
	var  sql = 'SELECT * FROM event'; 
	connection.query(sql,function (err, result) {            
	console.log('--------------------------SELECT----------------------------');       
	console.log(result);       
	console.log('------------------------------------------------------------\n\n');             
	// connection.end();       
	res.send(result); 
	});
});

//query by user account
router.get('/searchUser=:email', function(req, res) {
	connection.query("SELECT * FROM user WHERE emailAddress = ?", [req.params.email], function (err, result) { 
		console.log('--------------------------SELECT----------------------------');       
		console.log(result);       
		console.log('------------------------------------------------------------\n\n');  
		res.send(result); 
	});
});

//query for all users
router.get('/searchAllUser', function(req, res) {
	var  sql = 'SELECT * FROM user'; 
	connection.query(sql,function (err, result) { 
		console.log('--------------------------SELECT----------------------------');       
		console.log(result);       
		console.log('------------------------------------------------------------\n\n');  
		res.send(result); 
	});
});

//query by sport name
router.get('/searchSports=:name', function(req, res) {
	connection.query("SELECT * FROM sport WHERE sportName = ?", [req.params.name], function (err, result) { 
		console.log('--------------------------SELECT----------------------------');       
		console.log(result);       
		console.log('------------------------------------------------------------\n\n');   
		res.send(result); 
	});
});

//query for all sports
router.get('/searchAllSports', function(req, res) {
var  sql = 'SELECT * FROM sport'; 
connection.query(sql,function (err, result) { 
	console.log('--------------------------SELECT----------------------------');       
	console.log(result);       
	console.log('------------------------------------------------------------\n\n');   
	res.send(result); 
});
});

//login by account and password
router.get('/loginUserName=:username,pwd=:pwd', function(req, res) {
    connection.query("SELECT * FROM user WHERE emailAddress = ? and password = ?", [req.params.username, req.params.pwd], function (err, result) {
		console.log(result);        
        res.send(result);
    });
});

//register as new user
router.get('/createUser=:username,pwd=:pwd,readName=:name,ps=:ps,preferSport=:prefer,schoolYear=:year',function(req,res){
	 connection.query("INSERT INTO user (password,readName,emailAddress,ps,prefer_sport,schoolYear) VALUES (?,?,?,?,?,?)", 
	 	[req.params.pwd, req.params.name, req.params.username, req.params.ps, req.params.prefer, req.params.year],
	 	function(err, result){
			var checkStatus = {"boolean":true};
	 		if(err){
				console.log("ERROR:", err.message);
				checkStatus = {"boolean":false};
				res.send(checkStatus);
				return;
			}
	 		console.log(result);
	 		res.send(checkStatus);
	 		//return
	 	});
});

//create a new event
router.get('/createEvent=:eventname,date=:date,time=:time,location=:loc,skill=:skill,description=:des,teamSize=:tsize', function(req,res){
	connection.query("INSERT INTO event (name,date,time,location,skill,description,teamSize) VALUES (?,?,?,?,?,?,?)",
		[req.params.eventname, req.params.date, req.params.time, req.params.loc, req.params.skill, req.params.des, req.params.tsize],
		function(err, result){
			var checkStatus = {"boolean":true};
	 		if(err){
				console.log("ERROR:", err.message);
				checkStatus = {"boolean":false};
				res.send(checkStatus);
				return;
			}
	 		console.log(result);
	 		res.send(checkStatus);
			//return
		});
});

//update user information
router.get('/updateUser=:username,pwd=:pwd,readName=:name,ps=:ps,preferSport=:prefer,schoolYear=:year', function(req,res){
	connection.query("UPDATE user SET password = ?, readName = ?, ps = ?, prefer_sport = ?, schoolYear = ? WHERE emailAddress = ?", 
	 	[req.params.pwd, req.params.name, req.params.ps, req.params.prefer, req.params.year, req.params.username],
	 	function(err, result){
	 		var checkStatus = {"boolean":true};
	 		if(err){
				console.log("ERROR:", err.message);
				checkStatus = {"boolean":false};
				res.send(checkStatus);
				return;
			}
	 		console.log(result);
	 		res.send(checkStatus);
	 		//return
	 	});
});

//update event information
router.get('/updateEvent=:eventname,date=:date,time=:time,location=:loc,skill=:skill,description=:des,teamSize=:tsize,id=:id', function(req,res){
	connection.query("UPDATE event SET name = ?, date = ?, time = ?, location = ?, skill = ?, description = ?, teamSize = ? WHERE id = ?" ,
		[req.params.eventname, req.params.date, req.params.time, req.params.loc, req.params.skill, req.params.des, req.params.tsize, req.params.id],
		function(err, result){
			var checkStatus = {"boolean":true};
	 		if(err){
				console.log("ERROR:", err.message);
				checkStatus = {"boolean":false};
				res.send(checkStatus);
				return;
			}
	 		console.log(result);
	 		res.send(checkStatus);
			//return
		});
});

//delete event by id
router.get('/deleteEventById=:id', function(req,res){
	connection.query("DELETE from event WHERE id = ?", [req.params.id], function(err, result){
		var checkStatus = {"boolean":true};
		if(result.affectedRows == 0){
			checkStatus = {"boolean":false};
			console.log("id " + req.params.id +" is not exist");
			res.send(checkStatus);
			return;
		}
		console.log("Delete succeed");
		res.send(checkStatus);
	});
});


module.exports = router;

