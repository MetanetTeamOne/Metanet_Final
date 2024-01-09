$().ready(function () {
    $('#cardButton').on('click', function () {
        Swal.fire({
            title: '카드 추가',
            html: `
                 <form id="sendForm" method="post" action="/member/card/insert" style="overflow-x: hidden;">
	                    <div class="row gy-3" id="directPaymentForm" style="display: flex;">
							<div class="col-md-12" style=" align-items: center;
							    display: flex;
							    flex-direction: column;
							    flex-wrap: nowrap;
							    justify-content: center;">
								<label for="cc-number" class="form-label title">카드번호</label>
								<input type="text" class="form-control" id="cc-number" placeholder="-없이 작성해 주세요" required="">
								<div class="invalid-feedback title">
									카드번호는 필수입니다
								</div>
							</div>

							<div class="col-md-4">
								<label for="cc-name" class="form-label title">카드사</label>
								<!--<input type="text" class="form-control" id="cc-name" placeholder="" required="">
							<small class="text-body-secondary">국민, 신한, 농협, BC카드 결제 가능합니다.</small>-->
								<select class="form-select" id="cc-company" required="">
									<option value="">선택하세요.</option>
									<option>국민</option>
									<option>신한</option>
									<option>농협</option>
									<option>BC</option>
								</select>
								<div class="invalid-feedback title">
									카드사는 필수입니다
								</div>
							</div>

							<div class="col-md-4">
								<label for="cc-expiration" class="form-label title">유효날짜</label>
								<input type="month" class="form-control" id="cc-expiration" placeholder="ex) 04/28" required="">
								<div class="invalid-feedback title">
									유효날짜는 필수입니다
								</div>
							</div>

							<div class="col-md-4">
								<label for="cc-cvv" class="form-label title">CVC</label>
								<input type="text" class="form-control" id="cc-cvv" placeholder="ex)000" required="">
								<div class="invalid-feedback title">
									CVC 번호는 필수입니다
								</div>
							</div>
						</div>
					</form>
                `,
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            buttonsStyling: false,
            customClass: {
                confirmButton: 'swal-button',
                cancelButton: 'swal-button-cancel'
            },
        }).then((result) => {
            if (result.isConfirmed) {
                // This block will execute when the "확인" button is clicked.
                // You can open another modal or perform other actions here.
                Swal.fire({
                    title: swTitle,
                    text: swComment,
                    icon: swImage,
                    buttonsStyling: false,
                    customClass: {
	                confirmButton: 'swal-button',
	            	},
                }).then((result) => {
		            if (result) {
		                document.getElementById('sendForm').submit();
		            }
		        });
            }
        });
    });
});