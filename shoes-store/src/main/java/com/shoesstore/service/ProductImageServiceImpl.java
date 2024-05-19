package com.shoesstore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoesstore.model.Product;

import com.shoesstore.model.ProductImage;
import com.shoesstore.repository.ProductImageRepository;

@Service
public class ProductImageServiceImpl implements ProductImageService {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/products";
	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public void saveProductImages(Product product, List<MultipartFile> images) throws IOException {
		// Save each file to the uploadDirectory
		for (MultipartFile file : images) {
			byte[] bytes;
			try {
				bytes = file.getBytes();
				Path path = Paths.get(uploadDirectory, file.getOriginalFilename());
				Files.write(path, bytes);

				// luu thong tin hinh anh cua san pham vao co so du liẹu
				ProductImage productImage = new ProductImage(file.getOriginalFilename().toString(), product);
				productImageRepository.save(productImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// xoá ảnh khi cập nhật thông tin sản phẩm
	@Override
	public void deleteProductImages(List<Integer> deletedImages) throws IOException {
		for (Integer imageId : deletedImages) {
		   //lấy ra ảnh cần xoá
			
		   ProductImage toDelImage = productImageRepository.findById(imageId).get();
		   if (toDelImage != null) {
			   //xử lý xoá trong thư mục
			   Path oldImgPath = Paths.get(uploadDirectory, toDelImage.getImageUrl());
			   Files.deleteIfExists(oldImgPath);
			   //xử lý xoá trong cơ sở dữ liệu
			   productImageRepository.delete(toDelImage);
		   }
				   
		}
		
	}

}
