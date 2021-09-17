/**
 * 
 */
var stompClient = null;
var sessionId;
var uid;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8080/qkonnekt');
    stompClient = Stomp.over(socket);
	
    stompClient.connect({
		'origin' : 'localhost:8080'
	}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
		
		var urlarray = socket._transport.url.split('/');
		var index = urlarray.length - 2;
		sessionId = urlarray[index];
	
		console.log('sessionId', sessionId);
		
		uid = $("#name").val();
		
		stompClient.subscribe('/qkonnekt/user/'+uid+'/posts', function (greeting) {
			console.log('received....');
			var data = JSON.parse(greeting.body);
            showGreeting(data.post);
        });
		
		//register the session id with the server...
		register(sessionId);
    });
}

function register(sessionId){
	var uid = $("#name").val();
	var message = {
		"sessionId": sessionId,
		"uid": uid
	};
	var registrationMessage = JSON.stringify(message);
	stompClient.send("/qkonnekt/register", {}, registrationMessage);
}

function unregister(sessionId){
	var uid = $("#name").val();
	var message = {
		"sessionId": sessionId,
		"uid": uid
	};
	var registrationMessage = JSON.stringify(message);
	stompClient.send("/qkonnekt/unregister", {}, registrationMessage);
}

function disconnect() {
    if (stompClient != null) {
		
		unregister(sessionId);
				
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/myprefix/incoming", {}, JSON.stringify({'name': $("#name").val(), 'content': $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});