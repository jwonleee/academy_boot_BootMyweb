//달력
$(document).ready(function () {
	$(".datepicker").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	});
	$(".datepicker").prop('readonly', false); //required사용 
});
// - 토글
$(document).ready(function() {
	$(".showtoggle").click(function() {
		
		var result = $(this).next("ul").css("display");
		if(result == "block") {
			$(this).next("ul").slideUp();
		} else {
			$(this).next("ul").slideDown();
		}

	});
});
//파일업로드
$(document).ready(function() {

	var fileTarget = $('.filebox .upload-hidden'); //jquery는 다중 태그이벤트도 한번에 처리
	fileTarget.on('change', function(){ //change이벤트
	if(window.FileReader){ // modern browser 
		var filename = $(this)[0].files[0].name; } 
	else { // old IE 
		var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출 
	} // 추출한 파일명 삽입 
	$(this).siblings('.upload-name').val(filename); });

	var imgTarget = $('.preview-image .upload-hidden'); 
	imgTarget.on('change', function() { 
		var parent = $(this).parent(); 
		// parent.children('.upload-display').remove(); 
		
		if(window.FileReader){ //image 파일만 
			if (!$(this)[0].files[0].type.match(/image\//)) return; 

			var reader = new FileReader(); 
			reader.onload = function(e){ 
				var src = e.target.result; 
				// parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>'); 
				parent.find(".upload-thumb-wrap").children().attr("src", src);
			} 
			reader.readAsDataURL($(this)[0].files[0]); 
		} else { 
			$(this)[0].select(); 
			$(this)[0].blur(); 
			var imgSrc = document.selection.createRange().text; 
			parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>'); 

			var img = $(this).siblings('.upload-display').find('img'); 
			img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")"; 
		} 
	});

});

//사이드
$(document).ready(function () {
	var sidenav = document.querySelector(".sidenav");
	var menuBtn = document.querySelector(".menuBtn");
	menuBtn.onclick = function () {
		var btn = sidenav.style.width;
		if (btn === "0px" || btn === "") {
			sidenav.style.width = "200px";
		} else {
			sidenav.style.width = "0px";
		}
	}
});

$(document).ready(function() {
	$('.sub_menu_toggle > a').click(function(){

		if(!$(this).hasClass("sub_menu_select") ){
			$(this).next('ul').slideDown();
			$(this).addClass('sub_menu_select');
		} else {
			$(this).next('ul').slideUp();
			$(this).removeClass("sub_menu_select");
		}

	});
});
/* 카테고리 */
function getCategory_List(e) {
	e.preventDefault(); //a태그니까 고유이벤트중지
	if( e.target.tagName != 'A') return; //a태그가 아니면 태그검증
	var obj = $(e.target).data("set"); //(클릭한 대상에 jquery의 data를 쓰면) 데이터셋을 가져옴, js에서는 dataSet 사용했음

	//토글색처리 - css 처리
	$(e.currentTarget).find("a").removeClass("sub_menu_select");
	$(e.target).addClass("sub_menu_select");
	//태그처리
	if(obj.category_lv == 1 || obj.category_lv == 2) { //3단 카테고리니까 1, 2까지만 클릭하면 됨
		console.log('1lv');
		$().loading(); //로딩
		$(e.currentTarget).category_remove(); //이전 카테고리삭제

		//////////////////////////////////////////////////
		//비동기콜백에서 category_create() 호출
		//비동기호출후 category_set() 호출
		//category_create(); //다음 카테고리생성
		
		console.log(obj);
		$.ajax({
			url: "../getCategoryChild/" + obj.group_id + "/" + obj.category_lv + "/" + obj.category_detail_lv,
			type: "get",
			success: function(result) {
			console.log(result);
			category_create(result);
			},
			error: function(err) {
				alert("카테고리 조회에 실패했습니다. 관리자에게 문의해주세요.");
			}
		})
		//////////////////////////////////////////////////
	}
	
	//0220 17:10
	//카테고리 키값 처리 (선택한 값의 group_id, category_pk를 처리)
	$(e.target).category_set();
	
}
//카테고리세팅
$.fn.category_set = function() {
	var category_id = this.data("set").category_id;
	var group_id = this.data("set").group_id;
	$("input[name='prod_category']").val(group_id + category_id ); //name이 prod_category인 곳에 추가
}
//이전카테고리 삭제JS
//1번 분류 다 선택하고, 2번 누르면 기존에 눌렸던거 다 없어져야 하니까
$.fn.category_remove = function() {
	while(this.next().length != 0) {
		$(this).next().remove();
	}
}
//다음카테고리 생성JS
function category_create(data) {

	//예시데이터
	/*var data = [
	 {category_lv: 2, group_id: 'B', category_detail_nm: '값선택', category_detail_parent_nm: '값선택'},
	 {category_lv: 2, group_id: 'B', category_detail_nm: '값선택', category_detail_parent_nm: '값선택'},
	 {category_lv: 2, group_id: 'B', category_detail_nm: '값선택', category_detail_parent_nm: '값선택'}
  ];*/

	var category = "";
	category += '<ul class="categoryList" style="position: relative;" onclick="getCategory_List(event);" >';
	data.forEach(function(result, index) {
		category += '<li><a href="#" data-set='+ JSON.stringify(result) +'>'+ result.category_detail_nm +'</a></li>';
	});
	category += '</ul>';

	$(".categoryListWrap").append(category);
}

/* loading JS */
$.fn.loading = function() {
	$(".loading").css({display: "block"});
	window.setTimeout(function() {
		$(".loading").css({display: "none"});
	}, 1000);
}

/* ckeditor */
  $(document).ready(function() {
        ClassicEditor.create(document.querySelector('#editor'), {
            removePlugins: ['Heading'],
            toolbar: [
                '|',
                'bold',
                'italic'],
            language: "ko"
        }).catch(error => {
            console.log(error);
        });

        ClassicEditor.create(document.querySelector('#editor2'), {
            removePlugins: ['Heading'],
            toolbar: [
                '|',
                'bold',
                'italic'],
            language: "ko"
        }).catch(error => {
            console.log(error);
        });
    });
