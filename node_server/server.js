var http = require('http');
var WebSocketServer = require('websocket').server;
var restify = require('restify');
var formidable = require("formidable");
var restport = 80;
var path = require('path');
var emitter = new(require('events').EventEmitter);
var mime = require('mime');
var fs = require('fs');
var bufferList = {};
var sessions = {};
function uploadTalk(req, res, next) {
	console.log(req);	
	var index = 0;
	bufferList[req.params.fileid] = [];
	console.log("upload attempted" + req.params.fileid);
	req.on('data', function(chunk) {
		bufferList[req.params.fileid].push(chunk);
	});

	req.on('end', function() {
		console.log("upload finished");
		res.send(200);
		return next();
	});
	//return false;
}

var rest = restify.createServer();
rest.use(restify.queryParser());
rest.post('/api/shout/:fileid', uploadTalk);
rest.on('clientError', function(exception){


console.log(exception);

});
rest.on('NotFound', function(req, res) {
  res.send(404, req.url + ' was not found');
});
rest.listen(restport, function() {
	console.log("rest port is ", restport);
});


