$().ready(function () {
    $('#addresButton').on('click', function () {
        Swal.fire({
            title: '주소 수정',
            html: `
                 <form id="sendForm" class="base_info_sec" method="post" action="/member/address/update2">
                     <input type="hidden" id="newAddressId" name="addressId">
                    <div class="col-sm-4" style="margin-top: 0!important;" >
						<label for="firstName" class="form-label title"></label>
						<input type="text" class="form-control title inputData" id="addressZipcode" placeholder="우편번호"
							 name="addressZipcode" readonly>
						<div class="invalid-feedback title">
							우편번호를 입력하세요
						</div>
						<button type="button" class="btn_post_search" onclick="daumPostcode()">
                           우편번호 찾기
                        </button>
					</div>

					<div class="col-sm-4" style="margin-top: 0!important;">
						<label for="lastName" class="form-label title"></label>
						<input type="text" class="form-control title inputData" id="addressRoad" placeholder="도로명 주소"
							 name="addressRoad" readonly>
						<div class="invalid-feedback title">
							도로명 주소를 입력하세요
						</div>
					</div>

					<div class="col-sm-4" style="margin-top: 0!important;">
						<label for="lastName" class="form-label title"></label>
						<input type="text" class="form-control title" id="addressContent" placeholder="상세주소"
							 name="addressContent" required="">
						<div class="invalid-feedback title">
							상세주소를 입력하세요
						</div>
					</div>
					<div class="col-sm-4" style="margin-top: 0!important;">
						<label for="lastName" class="form-label title"></label>
						<input type="text" class="form-control title" id="addressDetail" placeholder="요청사항"
							 name="addressDetail" required="">
						<div class="invalid-feedback title">
							요청사항을 입력하세요
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
                document.getElementById('sendForm').submit();

            }
        });
    });
});