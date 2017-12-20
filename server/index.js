var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var obsX;

server.listen(8080, function() {
	console.log("Server is running");
});

io.on('connection', function(socket) {
	console.log("Player Connected");
	socket.emit('socketID', { id: socket.id }); //emits to one client
	socket.emit('getPlayers', players);

	socket.on('getObsX', function() {
	    //console.log(obsX);
        socket.emit('getObsX', { obsSpace: obsX });
	});

	socket.broadcast.emit('newPlayer', { id: socket.id }); //emits to all clients
	socket.on('playerMoved', function(data) {
	    data.id = socket.id;
	    socket.broadcast.emit('playerMoved', data);
	    for (var i = 0; i < players.length; i++) {
	        if(players[i].id == data.id) {
	            players[i].x = data.x;
	            players[i].y = data.y;
	        }
	    }
	});

	socket.on('disconnect', function() {
		console.log("Player Disconnected");
		socket.broadcast.emit('playerDisconnected', { id: socket.id}); //sends it to every other connected client
		for (var i = 0; i < players.length; i++) {
		    if (players[i].id == socket.id){
		        players.splice(i, 1);
		    }
		}
	});
	players.push(new player(socket.id, 0, 0));
});

function player(id, x, y) {
    this.id = id;
    this.x = x;
    this.y = y;
}

setInterval(function(){
    obsX = Math.floor((Math.random()* (-1536))-384);
 }, 2000);