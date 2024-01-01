// 탭 : 공통
function openTab(e, id) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for(i=0; i<tabcontent.length; i++){
        tabcontent[i].style.display="none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for(i=0; i<tablinks.length; i++){
        tablinks[i].className = tablinks[i].className.replace(" active","");
    }
    document.getElementById(id).style.display="block";
    e.currentTarget.className += " active";
}

// 리뉴얼 카테고리_END
$(document).ready(function () {
	
	// 카테고리 GNB
	$(function () {
		var chuCategory = $(".chu_cate");
		var backLayer = $('.back_layer');
		if ( $(".main_slide").length > 0 ) {
			chuCategory.css("display", "block");
			$(".chu_cate .chu_content").css("opacity", "0.9");
			$(".chu_cate .chu_content").css("border-left", "none");
			$(".chu_cate .chu_content").css("border-right", "none");
		} else {
			chuCategory.css("display", "none");
			backLayer.css("display", "none");
			$(function () {
				$("#menuClickChu").mouseover(function () {
					chuCategory.show();
					backLayer.show();
				});
				$("#menuClickChu").mouseleave(function () {
					chuCategory.hide();
					backLayer.hide();
				});
			});
		}
	});
	
});