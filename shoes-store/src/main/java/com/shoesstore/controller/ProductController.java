package com.shoesstore.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.model.Product;
import com.shoesstore.model.ProductSize;
import com.shoesstore.service.BrandService;
import com.shoesstore.service.CategoryService;
import com.shoesstore.service.ProductImageService;
import com.shoesstore.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductImageService productImageService;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/products";

	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<?> createProduct(@RequestParam("productName") String productName,
			@RequestParam("productPrice") double productPrice, @RequestParam("productVersion") String productVersion,
			@RequestParam("productCategory") int categoryId, @RequestParam("productBrand") int brandId,
			@RequestParam("productImages") List<MultipartFile> productImages,
			@RequestParam("productDescription") String description, @RequestParam Map<String, String> sizes) {
		// Tạo mới sản phẩm và lưu vào cơ sở dữ liệu
		Product product = new Product();
		product.setName(productName);
		product.setPrice(productPrice);
		product.setVersion(productVersion);
		product.setCreatedAt(new Date());
		product.setUpdatedAt(new Date());
		product.setStatus("Đang bán");
		product.setDescription(description);

		// Lấy thông tin về danh mục và thương hiệu từ cơ sở dữ liệu
		Category category = categoryService.getCategoryById(categoryId).get();
		Brand brand = brandService.getBrandById(brandId).get();

		// Gán danh mục và thương hiệu cho sản phẩm
		product.setCategory(category);
		product.setBrand(brand);

		// Xử lý lưu trữ size và số lượng
		for (Map.Entry<String, String> entry : sizes.entrySet()) {
			if (entry.getKey().startsWith("size")) {
				int size = Integer.parseInt(entry.getKey().substring(6, 8)); // lấy số size từ tên tham số
				int quantity = Integer.parseInt(entry.getValue());

				// Tạo và lưu đối tượng ProductSize
				ProductSize productSize = new ProductSize();
				productSize.setProduct(product);
				productSize.setSize(String.valueOf(size));
				productSize.setQuantity(quantity);
				product.addSize(productSize); // phương thức này cần được thêm vào lớp Product
			}
		}
		// Lưu sản phẩm vào cơ sở dữ liệu
		Product savedProduct = productService.createProduct(product);

		// xử lý lưu trữ ảnh
		try {
			productImageService.saveProductImages(product, productImages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Chuyển hướng lại trang danh sách sản phẩm
		return ResponseEntity.ok().body(savedProduct);
	}

	@PostMapping("/stop-sale/{id}")
	public ResponseEntity<Object> stopSale(@PathVariable("id") int id) {
		// Thực hiện logic để thay đổi trạng thái của sản phẩm có ID là productId thành
		// "Nghỉ bán"
		if (productService.updateProductStatus(id, "Nghỉ bán")) {
			// Trả về phản hồi thành công nếu cần
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.badRequest().build();

	}

	@PostMapping("/start-sale/{id}")
	public ResponseEntity<Object> startSale(@PathVariable("id") int id) {
		// Thực hiện logic để thay đổi trạng thái của sản phẩm có ID là productId thành
		// "Nghỉ bán"
		if (productService.updateProductStatus(id, "Đang bán")) {
			// Trả về phản hồi thành công nếu cần
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.badRequest().build();

	}

	// xoá tạm sản phẩm bằng cách đặt is_deleted của sản phẩm đó lên true
	@PostMapping("/delete/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id) {
		if (productService.deleteProductStatus(id)) {
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.badRequest().build();

	}

	@GetMapping("/detail/{id}")
	@ResponseBody
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Product product = productService.findById(id);

		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	// chuyển logic xử lý cập nhật vào service làm
	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestParam("productId") int productId,
			@RequestParam("productName") String productName, @RequestParam("productPrice") double productPrice,
			@RequestParam("productVersion") String productVersion, @RequestParam("productCategory") int categoryId,
			@RequestParam("productBrand") int brandId, @RequestParam("productDescription") String description,
			@RequestParam("productImages") List<MultipartFile> productImages,
			@RequestParam(name = "deletedImages", required = false) List<Integer> deletedImages, @RequestParam Map<String, String> sizes) {
		
		//Xu ly cap nhat
		productService.updateProduct(productId, productName, productPrice, productVersion, categoryId, brandId,
				description, productImages, deletedImages, sizes);

		// Trả về phản hồi thành công
		return ResponseEntity.ok("Product updated successfully");
	}
	
	
}
