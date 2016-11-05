$(function() {
	var socket = new WebSocket("ws://localhost:8080/week04/chat");
	socket.onopen = function() {
		$("#chats").val("Connected\n" + $("#chats").val());
	}
	socket.onmessage = function(evt) {
		var data = JSON.parse(evt.data);
		$("#chats").val(data.time + ": " + data.text + "\n" + $("#chats").val());
	}
	$("#sendBtn").on("click", function() {
		socket.send($("#message").val())
		$("#message").val("")
	})
})
