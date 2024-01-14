$().ready(function () {
function autoPay () {
	let memberName = $('#memberName').val();
	let memberEmail = $('#memberEmail').val();
	let memberPhoneNumber = $('#memberPhoneNumber').val();
	let memberId = $('#memberId').val();
	BootPay.request({
		price: 0, // 0으로 해야 한다.
		application_id: "65a160cc00c78a001d3460d7",
		name: '자동 결제', //결제창에서 보여질 이름
		pg: 'nicepay',
		method: 'card_rebill', // 빌링키를 받기 위한 결제 수단
		show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
		user_info: {
			username: memberName,
			email: memberEmail,
			phone: memberPhoneNumber
		},
		order_id: '자동결제'+memberId+new Date(), //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
		params: {},
		extra: {
			//start_at: '2019-05-10', // 정기 결제 시작일 - 시작일을 지정하지 않으면 그 날 당일로부터 결제가 가능한 Billing key 지급
			//end_at: '2022-05-10' // 정기결제 만료일 -  기간 없음 - 무제한
		}
	}).error(function (data) {
		//결제 진행시 에러가 발생하면 수행됩니다.
		location.reload();
		console.log(data);
	}).cancel(function (data) {
		//결제가 취소되면 수행됩니다.
		location.reload();
		console.log(data);
	}).done(function (data) {
		//console.log(data);
		let billingKey = data["billing_key"];
		 $('#memberCard').val(billingKey);
		//console.log("value: ",$('#memberCard').val)
		document.getElementById('sendForm').submit();
	});
};
});