let ws = null;
$(document).ready(function(){
	connectWs();
	memHelp();
});


function connectWs(){
    // 웹소켓 연결
    ws = new WebSocket("ws://localhost:8085/ws/alarm");
	
	
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
    
    let sendMsg = "<li><div class='dropdown-item title'>";
	sendMsg += data[0];
	sendMsg += "<p><span class='title' style='padding: 0 10px;'>";
	sendMsg += data[1];
	sendMsg += '</span><button type="button" id="altCheck" class="btn btn-primary" style="background-color: var(--sub); font-size: 0.8rem;">확인</button>';
	sendMsg +=  '</p></div></li>';
    let sendCount = "<em>1</em>";
    
	if ($("#alarmIsEmpty").length) {
    	$("#msgStack").html(sendMsg);
    	$("#alarmCount").append(sendCount);
	} else {
	    $("#msgStack").append(sendMsg);
	    let count = ($("#alarms li").length)-1;
		$("#alarmCount em").text(count);
	}
	
	
	
   	$("#alarms").on("click", "li button", function() {
    	$(this).closest('li').remove();
 		
    	if ($("#alarms li").length == 1){
			$("#alarmCount em").remove();
			let emptyMsg = '<li id="alarmIsEmpty"><span class="title" style="padding: 0 10px;"> 알림이 없습니다. </span></p></li>'
			$("#msgStack").html(emptyMsg);
		}else{
			let count = ($("#alarms li").length)-1;
			$("#alarmCount em").text(count);
		}
	});
};	

function memHelp(){
	$('#insertBtn').click(function(e){
    //cmd, send 계정, title, content
 	ws.send("memHelp"+","+"wldmx3@gmail.com"+","+"1대1 문의"+","+"1대1 문의가 등록되었습니다.");
	});
};