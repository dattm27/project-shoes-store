var datatable = $('.kt-datatable').KTDatatable({
	// datasource definition
	data: {
		type: 'remote',
		source: {
			read: {
				url: '/admin/products/data',
				map: function(raw) {
					// sample data mapping
					var dataSet = raw;
					if (typeof raw.data !== 'undefined') {
						dataSet = raw.data;
					}
					return dataSet;
				},
			},
		},
		pageSize: 10,
		serverPaging: true,
		serverFiltering: true,
		serverSorting: true,
	},

	// layout definition
	layout: {
		scroll: false,
		footer: false,
	},

	// column sorting
	sortable: true,

	pagination: true,

	search: {
		input: $('#generalSearch'),
		delay: 500,
	},

	// columns definition
	columns: [
		{
			field: 'id',
			title: '#',
			sortable: 'asc',
			width: 40,
			type: 'number',
			selector: false,
			textAlign: 'center',
		},
		{
			field: 'name',
			title: 'Name',
		},
		{
			field: 'version',
			title: 'Version',
		},
		{
			field: 'price',
			title: 'Price',
		},
		// 					{
		// 						field: 'updatedAt',
		// 						title: 'updated_at',
		// 						type: 'date',
		// 						format: 'MM/DD/YYYY',
		// 					}, 
		// 					{
		// 						field: 'isDelete',
		// 						title: 'Status',
		// 						// callback function support for column rendering
		// 						template: function (row) {
		// 							var status = {
		// 								false: { 'title': 'Active', 'class': 'kt-badge--success' },
		// 								true: { 'title': 'Inactive', 'class': ' kt-badge--danger' },
		// 							};
		// 							return '<span class="kt-badge ' + status[row.isDelete].class + ' kt-badge--inline kt-badge--pill">' + status[row.isDelete].title + '</span>';
		// 						},
		// 					}, 
		{
			field: 'status',
			title: 'Status',
			//			},
			// callback function support for column rendering
			template: function(row) {
				////				console.log(row);
				var status = {
					'Nghỉ bán': { 'title': 'Nghỉ bán', 'state': 'danger' },
					'Đang bán': { 'title': 'Đang bán', 'state': 'success' },
					'Sắp có': { 'title': 'Sắp có', 'state': 'accent' },
					'Ẩn trên webshop': { 'Ẩn trên webshop': 'Direct', 'state': 'primary' },
					'Ngừng kinh doanh': { 'Ngừng kinh doanh': 'Direct', 'state': 'accent' },
				};
				return '<span class="kt-badge kt-badge--' + status[row.status].state + ' kt-badge--dot"></span>&nbsp;<span class="kt-font-bold kt-font-' + status[row.status].state + '">' +
					status[row.status].title + '</span>';
			},
		},
		{
			field: 'Actions',
			title: 'Actions',
			sortable: false,
			width: 130,
			overflow: 'visible',
			textAlign: 'center',
			template: function(row, index, datatable) {
				var dropup = (datatable.getPageSize() - index) <= 4 ? 'dropup' : '';
				var html = '<div class="dropdown ' + dropup + '">\
                        <a href="#" class="btn btn-clean btn-icon btn-pill" data-toggle="dropdown">\
                            <i class="la la-ellipsis-h"></i>\
                        </a>\
                        <div class="dropdown-menu dropdown-menu-right">\
                            <a class="dropdown-item" onclick="startSale(' + row.id + ')"><i class="la la-edit"></i> Mở bán</a>\
                            <a class="dropdown-item" onclick="stopSale(' + row.id + ')"><i class="la la-leaf"></i> Nghỉ bán</a>\
                            <a class="dropdown-item" ><i class="la la-print"></i> Generate Report</a>\
                        </div>\
                    </div>\
                    <a class="btn btn-clean btn-icon btn-pill" title="Edit details" onclick="editProduct('+row.id+')">\
                        <i class="la la-edit"></i>\
                    </a>\
                    <a onclick="deleteProduct(' + row.id + ')" class="btn btn-clean btn-icon btn-pill" title="Delete">\
                        <i class="la la-trash"></i>\
                    </a>';

				return html;

			}
		},
	],

});


$('#kt_form_status').on('change', function() {
	datatable.search($(this).val(), 'status');
});

$('#kt_form_type').on('change', function() {
	datatable.search($(this).val().toLowerCase(), 'Type');
});


// Hàm thực hiện AJAX khi click vào liên kết "Mở bán"
function startSale(productId) {
	var url = '/product/start-sale/' + productId;

	// Gửi yêu cầu AJAX
	$.ajax({
		url: url,
		method: 'POST', // Hoặc 'GET' tùy thuộc vào cách bạn cấu hình server
		data: {}, // Dữ liệu nếu cần
		success: function(response) {
			//alert("Nghỉ bán thành công");
			// Xử lý kết quả thành công nếu cần

			//Đổi trạng thái trên bảng thành nghỉ bán
			// Cập nhật trạng thái của hàng tương ứng trong DataTable thành "Nghỉ bán"
			var row = $(this).closest('tr');
			datatable.row(row).data().status = 'Đang bán'
			datatable.reload(); // Tải lại DataTable để cập nhật giao diện
		},
		error: function(xhr, status, error) {
			// Xử lý lỗi nếu có
			console.error(xhr.responseText);
		}
	});
}

// Hàm thực hiện AJAX khi click vào liên kết "Nghỉ bán"
function stopSale(productId) {
	var url = '/product/stop-sale/' + productId;

	// Gửi yêu cầu AJAX
	$.ajax({
		url: url,
		method: 'POST', // Hoặc 'GET' tùy thuộc vào cách bạn cấu hình server
		data: {}, // Dữ liệu nếu cần
		success: function(response) {
			//alert("Nghỉ bán thành công");
			// Xử lý kết quả thành công nếu cần

			//Đổi trạng thái trên bảng thành nghỉ bán
			// Cập nhật trạng thái của hàng tương ứng trong DataTable thành "Nghỉ bán"
			var row = $(this).closest('tr');
			datatable.row(row).data().status = 'Nghỉ bán';
			datatable.reload(); // Tải lại DataTable để cập nhật giao diện
		},
		error: function(xhr, status, error) {
			// Xử lý lỗi nếu có
			console.error(xhr.responseText);
		}
	});
}

// Hàm thực hiện AJAX khi click vào liên kết "Xoá sản phẩm"
function deleteProduct(productId) {
	if (confirm("Bạn có chắc chắn muốn xoá?")) {
		var url = '/product/delete/' + productId;

		// Gửi yêu cầu AJAX
		$.ajax({
			url: url,
			method: 'POST', 
			data: {}, // Dữ liệu nếu cần
			success: function(response) {
				//alert("Nghỉ bán thành công");
				// Xử lý kết quả thành công nếu cần

				datatable.reload(); // Tải lại DataTable để cập nhật giao diện sau khi xoá
			},
			error: function(xhr, status, error) {
				// Xử lý lỗi nếu có
				console.error(xhr.responseText);
			}
		});
	}
}