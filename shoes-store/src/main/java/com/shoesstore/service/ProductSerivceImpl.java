package com.shoesstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shoesstore.model.Product;
import com.shoesstore.repository.ProductRepository;

@Service
public class ProductSerivceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	
	@Override
	public List<Product> getProducts(String status, String name) {
		if (status == null && name == null) return productRepository.findAllByDeleted(false); 
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
	//thay đổi trạng thái của mặt hàng thành nghỉ bản
	@Override
	public boolean updateProductStatus(int id, String status) {
	//lấy ra đối tượng mặt hàng đó trong cơ sở dữ liệu
		Product product = productRepository.findById(id).get();
		if (product!= null) {
			product.setStatus(status);
			productRepository.save(product);
			return true;
		}
		return false;
		
	}
	//xoá tạm một sản phẩm bằng cách chuyển is_delete của sản phẩm đó thành true -> ẩn khỏi bảng quản lý
	@Override
	public boolean deleteProductStatus(int id) {
		//lấy ra đối tượng mặt hàng đó trong cơ sở dữ liệu
		Product product = productRepository.findById(id).get();
		if (product!= null) {
			product.setDeleted(true);
			productRepository.save(product);
			return true;
		}
		return false;
	}

}
