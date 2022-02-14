/**
 * 
 */
var stompClient = null;

function setConnected(connected) {
	$("#connect1").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect(username, authToken) {
	console.log('username 11', username);
	var socket = new SockJS('ws://localhost:8080/qkonnekt');
	stompClient = Stomp.over(socket);
	stompClient.connect({
		'X-Authorization' : authToken
	}, function(frame) {
		var url = stompClient.ws._transport.url;
		console.log('url is:', url);
		setConnected(true);
		console.log('Connected: ' + frame);
		// /user/T1/mychat
		stompClient.subscribe('/user/' + username + '/mychat', function(
				greeting) {
			showGreeting(JSON.parse(greeting.body).content);
		}, {
			'X-Authorization' : authToken
		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendMessage(authToken) {
	console.log('send auth token is ', authToken);
	showGreeting('Me: ' + $("#message").val());
	stompClient.send("/chat/trocks", {'X-Authorization' : authToken}, JSON.stringify({
		'name' : $("#name").val(),
		'content' : $("#message").val(),
		'to' : $("#to").val()
	}));
}

function showGreeting(message) {
	$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	/*
	 * $("#connect").click(function() { connect(); });
	 */
	$("#disconnect").click(function() {
		disconnect();
	});
	/*$("#send").click(function() {
		sendName();
	});*/
	
	$(window).on('beforeunload', function() {
		socket.close();
	});
});