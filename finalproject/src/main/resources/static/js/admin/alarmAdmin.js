let ws = null;
$(document).ready(function(){
	connectWs();
	subscribeAlarm();
	memReply();
	orderState();
});

function connectWs(){
    // 웹소켓 연결
	ws = new WebSocket('ws://localhost:8085/ws/alarm');

	ws.onopen = function() {
		console.log("연결완료");
	};
	
    // 데이터를 전달 받았을때 
    ws.onmessage = function (evt) {
        if (evt.data) {
            onMessage(evt);
        }
    };
    
    ws.onclose = function() {
	    console.log('연결종료');
	};
	
};

// 메시지 생성 및 추가
function onMessage(evt){
    let data = evt.data.split(',');
    	
	let sendMsg = '<div class="toast-container end-0 p-3" style="postion:absolute">';
	sendMsg += '<div class="toast" role="alert" aria-live="assertive" aria-atomic="true">';
    sendMsg += '<div class="toast-header">';
    sendMsg += '<strong class="me-auto">'+data[0]+'</strong>';
    sendMsg += '<small class="text-body-secondary">just now</small>';
    sendMsg += '<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>';
    sendMsg += '</div><div class="toast-body">'+data[1] +'</div></div></div>'; 

	$("#msgStack").append(sendMsg);
   	$(".toast").toast({"animation": true, "autohide": false});
    $('.toast').toast('show');
    
    $("#msgStack").on("click", ".toast button", function() {
    	$(this).closest('.toast-container').remove();
	});

};	


function subscribeAlarm(){
	$('#notifySendBtn').click(function(e){
    ws.send("sub"+","+"wldmx@naver.com"+","+"구독안내"+","+"구독 만료까지 10일 남았습니다.");	
	});
};
function memReply(){
	$('#insertBtn').click(function(e){
    ws.send("memReply"+","+"wldmx@naver.com"+","+"문의답변"+","+"문의하신 내용에 답변이 달렸습니다.");	
	});
};

function orderState(){
	$('#orderStateChangeButton').click(function(e){
    ws.send("ordState"+","+"wldmx@naver.com"+","+"주문상태"+","+"배송이 시작되었습니다.");	
	});
};