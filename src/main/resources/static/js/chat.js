var websocket;
var lockReconnect = false;
var sendUserId;
var targetUserId;
var realname;
var headerUrl;
var tmps = "";
function connectSocket() {
    sendUserId = localStorage.getItem("userId")
    let socketUrl = "ws://localhost:8080/myWebSocketServer/" + sendUserId ;
    if (window.WebSocket) {
        console.log("您的浏览器支持多个websocket通信的实例");
         }
    else {
        console.log("您的浏览器不支持多个websocket通信的实例,建议使用火狐浏览器或者谷歌浏览器");
         }
    websocket = new WebSocket(socketUrl);
    websocket.onopen = function (evt) {
        //已经建立连接
        heartCheck.reset().start()
        console.log(evt)
    };
    websocket.onclose = function (evt) {
        if (21 != evt.code){
            reconnect()
        }
        //已经关闭连接
        console.log(evt)
    };
    websocket.onmessage = function (evt) {
        //收到服务器消息，使用evt.
        let result = $.parseJSON(evt.data);
        if (result == "连接成功")return;
        tmps += "<div><span>" + result.content + "</span></div>"
        $("#chat_list").html(tmps)
        console.log(evt.data)
    };

    websocket.onerror = function (evt) {
        //产生异常
        console.log(evt)
    };
}
function sendMessage() {
    let intent = location.search
    targetUserId = intent.substring(intent.indexOf("=") + 1)
    realname = localStorage.getItem("realname")
    headerUrl = localStorage.getItem("headerUrl")
    let inputStr = $("#input_str").val()
    let sendData = {sendUserId:sendUserId
        ,targetUserId:targetUserId
        ,realname:realname
        ,headerUrl:headerUrl
        ,content:inputStr
        ,extend:""}
    websocket.send(JSON.stringify(sendData))
}

function reconnect() {
    if (lockReconnect) return;
    lockReconnect = true;
    //没连接上会一直重连，设置延迟避免请求过多
    setTimeout(function() {
        connectSocket();
        lockReconnect = false;
    }, 2000);
}
//心跳检测
var heartCheck = {
    timeout: 60000, //60秒
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function() {
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        return this;
    },
    start: function() {
        var self = this;
        this.timeoutObj = setTimeout(function() {
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            // websocket.send("心跳测试");
            self.serverTimeoutObj = setTimeout(function() { //如果超过一定时间还没重置，说明后端主动断开了
                websocket.close(21); //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
            }, self.timeout)
        }, this.timeout)
    }
}
window.onunload = function () {
    websocket.close(21)
}

