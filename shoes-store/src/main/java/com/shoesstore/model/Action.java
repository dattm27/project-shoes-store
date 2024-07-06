package com.shoesstore.model;

public class Action {
	private String title;
	private String classCss;
	private String status;
	public String getTitle() {
		return title;
	}

	public String getClassCss() {
		return classCss;
	}

	public String getStatus() {
		return status;
	}

	public Action() {
		super();
	}

	public Action(String title, String status) {
		super();
		this.title = title;
		this.status = status;
		//đặt class css tương ứng
		if (title.equalsIgnoreCase("Huỷ đơn hàng")) {
			this.classCss = "btn-danger";
		}
		else if (title.equalsIgnoreCase("Duyệt đơn hàng này") || title.equalsIgnoreCase("Giao hàng thành công")) {
			this.classCss = "btn-success";
		}
		else if (title.equalsIgnoreCase("Bắt đầu giao hàng") || title.equalsIgnoreCase("Giao hàng lần 2")) {
			this.classCss = "btn-primary";
		}
		
		else if (title.equalsIgnoreCase("Giao hàng thất bại, chờ giao lần 2") || title.equalsIgnoreCase("Giao hàng không thành công")) {
			this.classCss= "btn-warning";
		}
	
		
	}
	
	
	
}
