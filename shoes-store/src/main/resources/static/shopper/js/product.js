$('ul.size_list li.size_number:eq(0)').addClass('active');

if ($('.productSizeId').length > 0) {
	let sizeId = $('.productSizeId:eq(0)').val();
	$('#addToCart').attr('href', '/add-to-cart?size_id=' + sizeId);

} else {
	
}

$('.size_number').click(function (e) { 
    e.preventDefault();
    let sizeId = $(this).children('.productSizeId').val();
    $('#addToCart').attr('href', '/add-to-cart?size_id=' + sizeId);
});

$('#addToCart').click(function(e) {
    e.preventDefault();
	let url = $(this).attr('href') + "&product_id="+document.getElementById('productId').value;
    $.ajax({
		url: '/signed-in',
		type: 'GET',
		dataType: 'html',
		data: [
			{ topshoe: 'topshoe' }
		]
	})
	.done(function(res) {
		if (res != 'anonymousUser') {
			addToCart(url);
		} else {
			alert("Vui lòng đăng nhập để tiếp tục");
			window.location.replace("/sign-in");
			
		}
	})
	.fail(function() {
		console.log("error");
	})
	.always(function() {
		preventSharp();
	});
});

function addToCart (url) {
	fixPositionTopRightToast();
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'html',
		data: [
			{ topshoe: 'topshoe' }
		]
	})
	.done(function (res) {
		if (!res) {
			getCartHeaederContent();
			toastr.success("Thêm vào giỏ hàng thành công!");
		} else {
			toastr.warning(res);
		}
	})
	.fail(function () {
		console.error("Error add to cart");
		toastr.error("Lỗi thêm giỏ hàng!");
	})
	.always(function () {

	});
}

function fixPositionTopRightToast() {
	if (!$('.tt-stuck-nav').hasClass('stuck')) {
		toastr.options.positionClass = 'toast-top90-right';
	} else {
		toastr.options.positionClass = 'toast-top50-right';
	}
}

setTimeout(function () {

	$.ajax({
		url: '/product-view/' + $('#productId').val(),
		type: 'POST',
		dataType: 'html',
		data: [
			{ topshoe: 'topshoe' }
		]
	})
	.done(function() {

	})
	.fail(function() {
		console.log("error");
	})
	.always(function() {

	});
	
}, 3000)

$('#toogle-wishlist').click(function(event) {
	event.preventDefault();

	let wishlistIcon = $(this).children('i');
	let wishlistText = $(this).children('span');
	if (wishlistIcon.attr('class') == 'icon-n-072') {
		wishlistIcon.attr('class', 'icon-h-13');
		wishlistText.text('Đã thích');
	} else if (wishlistIcon.attr('class') == 'icon-h-13'){
		wishlistIcon.attr('class', 'icon-n-072');
		wishlistText.text('Yêu thích');
	}
	toggleWishlist($('#productId').val());
});


// comment
// Biến để lưu trữ giá trị rating
let userRating = 5

// Bắt sự kiện submit để sử dụng giá trị rating khi gửi form
document.getElementById('comment-form').addEventListener('submit', function(event) {
    event.preventDefault();

    // Lấy giá trị từ form, bao gồm userRating
    const commentText = document.getElementById('comment').value;

    // Gửi dữ liệu (bạn có thể thêm userRating vào data để gửi lên server)

    // Tiến hành hiển thị bình luận (bao gồm userRating) trong danh sách
    displayComment(userRating, commentText);
});
; 
// Hàm để đặt giá trị rating khi người dùng bấm vào sao
function setRating(rating) {
   
    userRating = rating;
    
    // Cập nhật giao diện ngôi sao (ví dụ: làm sao để highlight sao được chọn)
    const stars = document.querySelectorAll('.star');
    stars.forEach((star, index) => {
        star.classList.toggle('selected', index < rating);
    });
}


// Hàm để trả về chuỗi các ngôi sao tương ứng với rating
function getRatingStars(rating) {
    let stars = '';
    for (let i = 0; i < 5; i++) {
        stars += i < rating ? '★' : '☆';
    }
    return stars;
}

function addComment() {
    // Lấy giá trị từ form
   	const username = document.getElementById('name').value;
    const commentText = document.getElementById('comment').value;

	    // Tạo một đối tượng Date để lấy thời gian hiện tại
	    const currentTime = new Date();
	    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    	const timestamp = `on ${currentTime.toLocaleDateString('en-US', options)}`;
		
		
		    // Lấy giá trị số sao từ biến userRating
    const rating = userRating;
	     // Tạo phần tử mới cho bình luận
	    const commentElement = document.createElement('div');
	    commentElement.classList.add('tt-item');
		
	    // Hiển thị thông tin người bình luận, thời gian và nội dung bình luận
	    commentElement.innerHTML = `
	        <div class="tt-avatar">
	            <a href="#"><img src="images/product/single/review-comments-img-02.jpg" alt=""></a>
	        </div>
	        <div class="tt-content">
	            <div class="tt-rating" style="color: gold" >
	                ${getRatingStars(rating)}
	            </div>
	            <div class="tt-comments-info">
	                <span class="username">by <span>${username}</span></span>
	                <span class="time"> ${timestamp}</span>
	            </div>
	            <p>${commentText}</p>
	        </div>
	    `;
	
	     // Thêm bình luận vào danh sách
   		 document.querySelector('.tt-review-comments').appendChild(commentElement);
    
}
