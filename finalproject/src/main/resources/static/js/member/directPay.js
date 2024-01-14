
function directPay (price, count, items, orderId) {
  BootPay.request({
      price: price, //실제 결제되는 가격
 
      // 관리자로그인 -> 결제설치 -> 인증키 및 보안 -> WEB Application ID
      application_id: "65a160cc00c78a001d3460d7",
 
      name: count + '개의 물품 결제', //결제창에서 보여질 이름
      pg: 'nicepay',
      method: 'card', //결제수단, 입력하지 않으면 결제수단 선택부터 화면이 시작합니다.
      show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
      items: items,
      order_id: orderId, //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
  }).error(function (data) {
      //결제 진행시 에러가 발생하면 수행됩니다.
      location.reload();
      console.log("에러 발생 : ", data);
  }).cancel(function (data) {
      //결제가 취소되면 수행됩니다.
      location.reload();
      console.log("결제 취소 : ", data);
  }).close(function (data) {
      // 결제창이 닫힐때 수행됩니다. (성공,실패,취소에 상관없이 모두 수행됨)
      console.log(data);
  }).done(function (data) {
      //결제가 정상적으로 완료되면 수행됩니다
      //비즈니스 로직을 수행하기 전에 결제 유효성 검증을 하시길 추천합니다.
      $('form').submit();
      console.log("결제 완료 : ", data);
  });
}
