var stompClient = null;
var user = null;
var menuData = [[{
    text: "发送消息",
    func: function () {
        console.log($(this).attr("id"));
    }
}]]
function connect() {
    var ws = null;

    if ('WebSocket' in window) {
        ws = new WebSocket("ws://localhost:8080/chatServer");
    } else {
        ws = new SockJS("http://localhost:8080/chatServer");
    }
    stompClient = Stomp.over(ws);
    stompClient.connect({}, function (frame) {
        var headObj = frame.headers;
        // 从连接信息中取得当前用户信息并解析成obj
        user = JSON.parse(headObj['user-name']);

        stompClient.subscribe("/topic/chat", function (event) {
            var message = JSON.parse(event.body);
            printMessage(message);

        });
        // 登录到聊天室后获取在线用户
        stompClient.subscribe("/app/userlist", function (event) {
            var list = JSON.parse(event.body);
            printUserlist(list);
        });
        // 登录到聊天室后获取聊天记录
        stompClient.subscribe("/app/messageHistory", function (event) {
            var messagelist = JSON.parse(event.body);
            printMessageHistory(messagelist);

        });
        // 用户下线进行用户列表的删除
        stompClient.subscribe("/topic/useroffline", function (event) {
            var userId = event.body;
            userOffline(userId);
        });
        // 用户上线进行用户列表的添加
        stompClient.subscribe("/topic/useronline", function (event) {
            var user = JSON.parse(event.body);
            userOnline(user);
        })
        stompClient.subscribe("/user/queue/personal", function (event) {
            alert(event.body);
        })
        window.setInterval(updateMessageTime, 60000);

    });
    stompClient.ws.onclose(function () {
        user = null;
        alert("close");
    });
}
window.onload = connect;
// 显示用户在线列表
function printUserlist(userlist) {
    var length = userlist.length;

    for (var i = 0; i < length; i++) {
        var _user = getUserHtml(userlist[i]);

        $('#chat-users').append(_user);
    }

}
// 用户下线
function userOffline(userId) {
    $('#user-' + userId).remove();
}
// 用户上线
function userOnline(user) {
    // 此处_user为jquery对象,表示用户信息的一个节点
    var _user = getUserHtml(user);
    $('#chat-users').append(_user);
}
// 显示他人发送来的信息(他人的信息在左边,自己在右边)
function printMessage(message) {
    var messagehtml = getMessageHtml(message);

    $('#group-chat-body').append(messagehtml);

}
// 显示历史聊天记录
function printMessageHistory(messagelist) {
    var length = messagelist.length;
    for (var i = 0; i < length; i++) {
        var messagehtml = getMessageHtml(messagelist[i]);
        $('#group-chat-body').append(messagehtml);
    }

}
// 发送消息
function sendMessage() {

    if (stompClient != null) {
        var messageBox = $('#message');
        var message = messageBox.val();
        if (message.length == 0) {
            return;
        }
        messageBox.val('');
        var chatMessage = {
            from: user,
            to: "lisi",
            content: message,
        }
        stompClient.send("/app/whisper", {}, JSON.stringify(chatMessage));

    } else {
        alert('connection not established, please connect.');
    }

}
// 更新消息的间隔时间，每一分钟更新一次，在定时器中设置该function
function updateMessageTime() {
    $(".sendtime").each(function (index) {
        var _this = $(this);
        var messageTime = _this.html();
        var time = getTimeBetween(messageTime);
        _this.next().html(time);
    });
}
// 获取用户信息的html文本
function getUserHtml(user) {
    /*	var html = "<div id=user-" + user.id + " class=\"user\">"
     + "<div class=\"avatar\">"
     + "<img src=\"http://bootdey.com/img/Content/avatar/avatar6.png\""
     + "alt=\" User name\" />" + "<div class=\"status off\"></div>"
     + "</div>" + "<div class=\"name\">" + user.username + "</div>"
     + "<div class=\"mood\">User mood</div>" + "</div>";*/
    var html = "<div class=\"avatar\">"
        + "<img src=\"http://bootdey.com/img/Content/avatar/avatar6.png\""
        + "alt=\" User name\" />" + "<div class=\"status off\"></div>"
        + "</div>" + "<div class=\"name\">" + user.username + "</div>"
        + "<div class=\"mood\">User mood</div>" + "</div>";
    var _user = $('<div></div>');
    _user.html(html).addClass("user").attr("id", "user-".concat(user.id))
    _user.smartMenu(menuData);
    return _user;

}
// 获取消息的html 文本
function getMessageHtml(message) {
    var from = message.from;
    var location = from.username == user.username ? "right" : "left";
    var time = getTimeBetween(message.sendTime);
    var html = "<div class=\"answer " + location + "\">"
        + "<div class=\"avatar\">"
        + "<img src=\"http://bootdey.com/img/Content/avatar/avatar1.png\""
        + "alt=\"User name\" />" + "<div class=\"status offline\"></div>"
        + "</div>" + "<div class=\"name\">" + from.username + "</div>"
        + "<div class=\"text\">" + message.content + "</div>"
        + "<div class=\"sendtime\" style=\"display:none\">"
        + message.sendTime + "</div>" + "<div class=\"time\">" + time
        + "</div>" + "</div>";
    return html;
}
// 根据当前时间计算消息的间隔时间
function getTimeBetween(messageTime) {
    var minutes = parseInt((new Date().getTime() - messageTime) / (1000 * 60));
    var time = minutes > 0 ? "".concat(minutes).concat(minutes > 1 ? " mins" : " min ")
        .concat(" ago") : '';
    return time;
}