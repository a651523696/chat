var stompClient = null;
	function connect() {
		var ws = null;

		if ('WebSocket' in window) {
			ws = new WebSocket("ws://localhost:8080/chatServer");
		} else {
			ws = new SockJS("http://localhost:8080/chatServer");
		}
	stompClient = Stomp.over(ws);
		stompClient.connect({}, function(frame) {
				stompClient.subscribe("/app/connect",function(data){
					console.log('subscribe connect successfully');
				});
				stompClient.subscribe("/topic/chat",function(event){
					chatBoard(event.body);
				})
		});
		stompClient.ws.onclose = function() {
			alert('close');
		}
	}
	window.onload = connect;
	function chatBoard(message){
		var chatMessage = JSON.parse(message);
		printOthers(chatMessage.content);
	}
	function printOthers(content){
		var html = "<div class=\"answer left\">"+
		"<div class=\"avatar\">"+
		"<img src=\"http://bootdey.com/img/Content/avatar/avatar1.png\""+
		"alt=\"User name\" />"+
		"<div class=\"status offline\"></div>"+
		"</div>"+
		"<div class=\"name\">Alexander Herthic</div>"+
		"<div class=\"text\">"+content+"</div>"+
		"<div class=\"time\">5 min ago</div>"+
		"</div>";
		
		$('#chat-body').append(html);

	}
	function sendMessage() {
		
		if (stompClient != null) {
			var messageBox = $('#message');
			message = messageBox.val();
			if (message.length == 0) {
				return;
			}
			messageBox.val('');
			var chatMessage = {
					from:'zhangsan',
					to:'all',
					content:message
			}
			stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));

		} else {
			alert('connection not established, please connect.');
		}
	}