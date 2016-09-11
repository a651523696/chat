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
				});
				//登录到聊天室后获取在线用户
				stompClient.subscribe("/app/userlist",function(event){
						var list = JSON.parse(event.body);
						printUserlist(list);
				});
				//用户下线进行用户列表的删除
				stompClient.subscribe("/topic/useroffline",function(event){
					var userId = event.body;
					userOffline(userId);
				});
				//用户上线进行用户列表的添加
				stompClient.subscribe("/topic/useronline",function(event){
					var user = JSON.parse(event.body);
					userOnline(user);
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
	function printUserlist(userlist){
		var length = userlist.length;
		
		for(var i=0;i<length;i++){
			var userhtml = getUserHtml(userlist[i]);
			$('#chat-users').append(userhtml);
		}
		
	}
	function userOffline(userId){
		$('#user-'+userId).remove();
	}
	function userOnline(user){
		var userhtml = getUserHtml(user);
		$('#chat-users').append(userhtml);
	}
	//显示他人发送来的信息(他人的信息在左边,自己在右边)
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
	function getUserHtml(user){
		var html = "<div id=user-"+user.id+" class=\"user\">"+
		"<div class=\"avatar\">"+
		"<img src=\"http://bootdey.com/img/Content/avatar/avatar6.png\""+
		"alt=\" User name\" />"+
		"<div class=\"status off\"></div>"+
		"</div>"+
		"<div class=\"name\">"+user.username+"</div>"+
		"<div class=\"mood\">User mood</div>"+
		"</div>";
		return html;

	}