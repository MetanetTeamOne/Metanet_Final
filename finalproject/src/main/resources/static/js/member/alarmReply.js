/*function getCookie(cookieName) {
    var name = cookieName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookieArray = decodedCookie.split(';');
    for (var i = 0; i < cookieArray.length; i++) {
        var cookie = cookieArray[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}*/

let socket = null;
$(document).ready(function(){
	connectWs();
});


function connectWs(){
	// 토큰 값 가져오기
	//let token = getCookie('token');
    // 웹소켓 연결
    let ws = new WebSocket("ws://localhost:8085/ws/alarm");

	ws.onopen = function() {
		console.log("연결완료");
 		//ws.send(token);
	};
	
    // 데이터를 전달 받았을때 
    console.log("11111");
    ws.onmessage = onMessage; // toast 생성
    console.log("222222");
    
    ws.onclose = function() {
	    console.log('연결종료');
	};
	
};

// toast생성 및 추가
function onMessage(evt){
    let data = evt.data.split(',');
    
    let sendMsg = "<li><div class='dropdown-item title'>";
	sendMsg += data[0];
	sendMsg += "<p><span class='title' style='padding: 0 10px;'>";
	sendMsg += data[1];
	sendMsg += "</span></p></div></li>";
    let sendCount = "<em>1</em>";
    /*// toast
    let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
    toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
    toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
    toast += "<span aria-hidden='true'>&times;</span></button>";
    toast += "</div> <div class='toast-body'>" + data + "</div></div>";
    */
   /*console.log(document.getElementById("alarms").querySelectorAll('li').length);*/
	$("#msgStack").html(sendMsg);
	$("#alarmCount").append(sendCount);
	/*$("#msgStack").append(sendMsg);
    $(".toast").toast({"animation": true, "autohide": false});
    $('.toast').toast('show');*/
};	

