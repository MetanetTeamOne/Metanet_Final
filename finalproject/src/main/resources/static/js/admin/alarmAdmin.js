let ws = null;
$(document).ready(function(){
	connectWs();
	subscribeAlarm();
});

function connectWs(){
    // 웹소켓 연결
	ws = new WebSocket('ws://localhost:8085/ws/alarm');

	ws.onopen = function() {
		console.log("연결완료");
		//cmd, send 계정, title, content
 		//ws.send("memHelp"+","+"wldmx3@gmail.com"+","+"1대1 문의"+","+"1대1 문의가 등록되었습니다.");
	};
	
    // 데이터를 전달 받았을때 
    ws.onmessage = function (evt) {
        if (evt.data) {
			console.log("dddddddddddddd");
            onMessage(evt);
        }
    };
    
    
    
    /*ws.onclose = function() {
	    console.log('연결종료');
	};*/
	
};

// 메시지 생성 및 추가
function onMessage(evt){
    let data = evt.data.split(',');
    	
	let sendMsg = '<div class="toast-container end-0 p-3" style="postion:absolute">';
	sendMsg += '<div class="toast" role="alert" aria-live="assertive" aria-atomic="true">';
    sendMsg += '<div class="toast-header">';
    sendMsg += '<strong class="me-auto">Bootstrap</strong>';
    sendMsg += '<small class="text-body-secondary">just now</small>';
    sendMsg += '<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>';
    sendMsg += '</div><div class="toast-body">See? Just like this.</div></div></div>'; 

	$("#msgStack").append(sendMsg);
   	$(".toast").toast({"animation": true, "autohide": false});
    $('.toast').toast('show');
    
    $("#msgStack").on("click", ".toast button", function() {
    	$(this).closest('.toast-container').remove();
	});

};	


function subscribeAlarm(){
	$('#notifySendBtn').click(function(e){
    //let modal = $('.modal-content').has(e.target);
    ws.send("sub"+","+"wldmx@naver.com"+","+"구독안내"+","+"구독 만료까지 10일 남았습니다.");	

    /*let target = 3;
    let content = 'hello';
    let type = '70';*/
    //let target = modal.find('.modal-body input').val();
    //let content = modal.find('.modal-body textarea').val();
    /*let url = '/';*/
    // 전송한 정보를 db에 저장	
    /*$.ajax({
        type: 'get',
        url: '/',
        //dataType: 'text',
        data: {
            target: target,
            content: content,
            type: type,
            url: url
        },
        success: function(){
        }
    });
    */
    /*$.ajax({
        type: 'post',
        url: '${contextPath}/member/saveNotify.do',
        dataType: 'text',
        data: {
            target: target,
            content: content,
            type: type,
            url: url
        },
        success: function(){    // db전송 성공시 실시간 알림 전송
            // 소켓에 전달되는 메시지
            // 위에 기술한 EchoHandler에서 ,(comma)를 이용하여 분리시킨다.
            socket.send("관리자,"+target+","+content+","+url);	
        }
    });*/
    //modal.find('.modal-body textarea').val('');	// textarea 초기화
});
}

