$('#cancel_order').click(function(event) {
    // Lấy order ID từ phần tử span với ID order_id
    var orderId = $('#order_id').text().trim();

    swal.fire({
        title: 'Xác nhận hủy đơn hàng?',
        text: "",
        icon: 'error',
        showCancelButton: true,
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Quay lại'
    }).then((result) => {
        if (result.value) {
            customer_cancel_order(orderId);
        }
    });
});

function customer_cancel_order(orderId) {
    $.ajax({
        url: '/my-account/order/cancel/' + orderId,
        type: 'POST',
        dataType: 'html',
        data: {
           
           
        }
    })
    .done(function(res) {
        swal.fire(
            'Hủy đơn hàng thành công!',
            '',
            'success'
        ).then(() => {
            // Reload lại trang chi tiết đơn hàng
            location.reload();
        });
    })
    .fail(function() {
        console.log("error");
    })
    .always(function() {

    });
}

