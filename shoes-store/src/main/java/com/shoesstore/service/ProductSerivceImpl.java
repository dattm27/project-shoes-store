package com.shoesstore.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.Product;
import com.shoesstore.repository.ProductRepository;

@Service
public class ProductSerivceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private BrandService brandService;

	@Override
	public List<Product> getProducts(String status, String name) {
		if (status == null && name == null)
			return productRepository.findAllByDeleted(false);
		Specification<Product> spec = Specification.where(null);
		if (status != null) {
			spec = spec.and(ProductSpecifications.hasStatus(status));
		}
		if (name != null) {
			spec = spec.and(ProductSpecifications.hasName(name));
		}
		return productRepository.findAll(spec);
	};

	@Override
	public Product createProduct(Product product) {

		return productRepository.save(product);
	}

	// thay đổi trạng thái của mặt hàng thành nghỉ bản
	@Override
	public boolean updateProductStatus(int id, String status) {
		// lấy ra đối tượng mặt hàng đó trong cơ sở dữ liệu
		Product product = productRepository.findById(id).get();
		if (product != null) {
			product.setStatus(status);
			productRepository.save(product);
			return true;
		}
		return false;

	}

	// xoá tạm một sản phẩm bằng cách chuyển is_delete của sản phẩm đó thành true ->
	// ẩn khỏi bảng quản lý
	@Override
	public boolean deleteProductStatus(int id) {
		// lấy ra đối tượng mặt hàng đó trong cơ sở dữ liệu
		Product product = productRepository.findById(id).get();
		if (product != null) {
			product.setDeleted(true);
			productRepository.save(product);
			return true;
		}
		return false;
	}

	// lấy ra thông tin chi tiết của một sản phẩm
	@Override
	public Product findById(int id) {
		return productRepository.findById(id).get();
	}

	@Override
	public void updateProduct(int productId, String productName, double productPrice, String productVersion,
			int categoryId, int brandId, String description, List<MultipartFile> productImages,
			List<Integer> deletedImages) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		product.setName(productName);
		product.setPrice(productPrice);
		product.setVersion(productVersion);
		// product.setCreatedAt(new Date());
		product.setUpdatedAt(new Date());
		product.setDescription(description);

		// Lấy thông tin về danh mục và thương hiệu từ cơ sở dữ liệu
		Category category = categoryService.getCategoryById(categoryId).get();
		Brand brand = brandService.getBrandById(brandId).get();

		// Gán danh mục và thương hiệu cho sản phẩm
		product.setCategory(category);
		product.setBrand(brand);

		// Lưu sản phẩm vào cơ sở dữ liệu
		Product savedProduct = productRepository.save(product);

		// xử lý lưu trữ ảnh
		try {
			productImageService.saveProductImages(product, productImages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// xử lý xoá ảnh cũ
		if (deletedImages != null) {
			try {

				productImageService.deleteProductImages(deletedImages);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Product> listProducts(Integer category_id, Integer brand_id) {
		if (category_id != null) {
			if(brand_id != null) return productRepository.findAllByBrandIdAndCategoryId(brand_id, category_id);
			else return  productRepository.findAllByCategoryId(category_id);
		}
		else {
			if(brand_id != null) return productRepository.findAllByBrandId(brand_id);
			else return productRepository.findAllByDeleted(false);
		}
	

	}

}
