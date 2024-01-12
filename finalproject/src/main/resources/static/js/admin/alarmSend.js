window.addEventListener('DOMContentLoaded', event => {
const socket = new WebSocket('ws://localhost:8085/ws/alarm');

$('#notifySendBtn').click(function(e){
    //let modal = $('.modal-content').has(e.target);
    socket.send("sub"+","+"wldmx@naver.com"+","+"구독안내"+","+"구독 만료까지 10일 남았습니다.");	

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

});