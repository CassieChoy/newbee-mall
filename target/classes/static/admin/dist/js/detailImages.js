
var index = 0;

function showImages(leftOrRight) {
	var bt = document.getElementsByClassName("p-gallery_btn");
	var eles = document.getElementsByClassName("swiper-slide");
	var length = eles.length;

	if (leftOrRight === 'left') {
		index--;
		if (index === -1) {
			index = 0;
			oLeft.className = "p-gallery_btn p-gallery_prev swiper-button-disabled" ;
			oRight.className = "p-gallery_btn p-gallery_next";
			common(index);
			
		}
	} else {
		index++;
		if (index < length-1) {
			oLeft.className = "p-gallery_btn p-gallery_prev";
			oRight.className = "p-gallery_btn p-gallery_next";
			common(index);
		}else {
			index = length - 1;
			oLeft.className = "p-gallery_btn p-gallery_prev";
			oRight.className = "p-gallery_btn p-gallery_next swiper-button-disabled";
			common(index);

		}

	}

}

function common(index){
	var oImages = document.getElementsByClassName("js-gallery-thumbnails")[0];
	var oCircle = document.getElementsByClassName("swiper-pagination-bullet");
	var oCurrentX;
	
	
	
			oCurrentX = -400 * index;
			oImages.style.transform = "translate3d(" + oCurrentX + "px,0px,0px)";
			
			for(var i = 0;i<index;i++){
				oCircle[i].className ="swiper-pagination-bullet";
				oCircle[index].className = "swiper-pagination-bullet swiper-pagination-bullet-active";
			}
			for(i=index+1;i<oCircle.length;i++){
				oCircle[i].className ="swiper-pagination-bullet";
				oCircle[index].className = "swiper-pagination-bullet swiper-pagination-bullet-active";
			}
			
			
}

function selectImage(index,_this){
	var images = document.getElementsByClassName("p-gallery_thumbs_item");
	var bt = document.getElementsByClassName("p-gallery_btn");
	var image = $(_this).css("background-image");
	image =  image.replace(/.*\s?url\([\'\"]?/, '').replace(/[\'\"]?\).*/, '');
	
	var data = {
		"image": image
	};
	
	$.ajax({
		type: 'POST',//方法类型
		url: '/goods/goodsCoverImage',
		contentType: 'application/json',
		data: JSON.stringify(data),

		success: function(result) {

			if (result.resultCode == 200) {
				var goodsImage = result.message;
				$('#goodsCoverImg').attr("src",goodsImage);
				
				
			}

			else {
				swal(result.message, {
					icon: "error",
				});
			}
		}
	});

			for(var i= 0;i<index;i++){
				images[i].className ="p-gallery_thumbs_item";
				images[index].className = "p-gallery_thumbs_item p-gallery_thumbs_item-active";
				bt.className[i]="swiper-pagination-bullet";
				bt.className[index] = "swiper-pagination-bullet swiper-pagination-bullet-active";

			}
			for(i=index+1;i<images.length;i++){
				images[i].className ="p-gallery_thumbs_item";
				images[index].className = "p-gallery_thumbs_item p-gallery_thumbs_item-active";

			}
	
}