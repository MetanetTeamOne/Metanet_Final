window.daumPostcode = function daumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {

			document.getElementById('addressZipcode').value = "";
            document.getElementById("addressRoad").value = "";
            
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let roadAddr = data.roadAddress != null ? data.roadAddress : data.jibunAddress; // 도로명 주소 변수

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('addressZipcode').value = data.zonecode;
            document.getElementById("addressRoad").value = roadAddr;   
        }
    }).open();
}
