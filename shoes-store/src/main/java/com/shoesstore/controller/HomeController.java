package com.shoesstore.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoesstore.model.Brand;
import com.shoesstore.model.Category;
import com.shoesstore.model.Comment;
import com.shoesstore.model.CustomUserDetails;
import com.shoesstore.model.Order;
import com.shoesstore.model.Product;
import com.shoesstore.model.User;
import com.shoesstore.service.BrandService;
import com.shoesstore.service.CategoryService;
import com.shoesstore.service.CommentService;
import com.shoesstore.service.FavoriteService;
import com.shoesstore.service.OrderService;
import com.shoesstore.service.ProductService;
import com.shoesstore.service.ProductSizeService;
import com.shoesstore.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductSizeService productSizeService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private CommentService commentService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//	url lưu ảnh sản phẩm
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/products";

	@RequestMapping("/")
	public String showHomePage(Model model) {
		List<Product> productList = productService.getProducts("Đang bán", null);
		model.addAttribute("products", productList);
		return "/shopper/index";
	}

	@RequestMapping("/index")
	public String showIndex(Model model) {

		return "redirect:/";
	}

	@GetMapping("user-list")
	public String showUsers(Model model) {
		// hien thi danh sach cac user trong he thong
		model.addAttribute("users", userService.getAllUsers());
		return "user-list";

	}

	@RequestMapping("/sign-in")
	public String showLoginForm(HttpServletRequest request) {

		return "redirect:/";
	}

	// check xem có thông tin người dùng đăng nhập chưa
	@GetMapping("signed-in")
	@ResponseBody
	public String checkSignInStatus() {
		// Lấy thông tin xác thực hiện tại
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Kiểm tra xem người dùng đã đăng nhập hay chưa
		if (authentication.isAuthenticated()) {
			// Nếu đã đăng nhập, trả về tên người dùng hoặc thông tin khác
			String username = authentication.getName();
			return username;
		} else {
			// Nếu chưa đăng nhập, trả về một giá trị thể hiện việc không đăng nhập
			return "";
		}
	}

	@GetMapping("/category")
	@ResponseBody
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();

		return ResponseEntity.ok().body(categories);
	}

	@GetMapping("/brands")
	@ResponseBody
	public ResponseEntity<List<Brand>> getAllBrands() {
		List<Brand> brands = brandService.getAllBrands();

		return ResponseEntity.ok().body(brands);
	}

	// tìm kiếm theo nhiều yếu tố (lọc sản phẩm ở trang sản phẩm)
	@GetMapping("/product-listing")
	public String showProductLis(Model model, @RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(name = "categoryId", required = false) Integer category_id,
			@RequestParam(name = "brandId", required = false) Integer brand_id,
			@RequestParam(value = "priceMin", required = false) Double minPrice,
			@RequestParam(value = "priceMax", required = false) Double maxPrice,
			@RequestParam(value = "size", required = false) String size) {
		List<Brand> brands = brandService.getAllBrands();
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		// Brand được chọn để lọc
		Brand brand = null;
		if (brand_id != null) {
			brand = brandService.getBrandById(brand_id).get();

		}
		model.addAttribute("brand", brand);
		// Category được chọn để lọc
		Category category = null;
		if (category_id != null) {
			category = categoryService.getCategoryById(category_id).get();
		}
		model.addAttribute("category", category);
		List<Product> productList = productService.getFilteredProducts("Đang bán", name, category_id, brand_id,
				minPrice, maxPrice, size);
		model.addAttribute("products", productList);

		model.addAttribute("sizes", productSizeService.getAllSizes());

		return "shopper/product-list";
	}

	@GetMapping("/search")
	public String searchProductByName(@RequestParam(name = "q", required = false) String name, Model model) {
		List<Product> products = productService.getProducts("Đang bán", name);
		List<Brand> brands = brandService.getAllBrands();
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		model.addAttribute("products", products);
		model.addAttribute("sizes", productSizeService.getAllSizes());
		return "shopper/product-list";
	}

	@GetMapping("images/{imageUrl}")
	@ResponseBody
	public ResponseEntity<Resource> getProductImage(@PathVariable("imageUrl") String url) throws IOException {
		// Get the image from the company object
		Path imagePath = Paths.get(uploadDirectory, url);
		// Fetching the image from that particular path
		Resource resource = new FileSystemResource(imagePath.toFile());
		;
		String contentType = Files.probeContentType(imagePath);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
	//trang chi tiết sản phẩm dành cho khách xem
	@GetMapping("product-details/{id}")
	public String showProductDetails(@PathVariable("id") int productId, Model model) {
		Product product = productService.findById(productId);
		List<Comment> comments = commentService.getComment(productId);
		// Lấy thông tin xác thực hiện tại
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if ( authentication.isAuthenticated()) {
		
//			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
//					.getPrincipal();
		 SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			if (favoriteService.isLoved(currentUser.getId(), productId)) product.setFavorite(true);
	//	int userId = userService.findUserByEmail(authentication.getName()).getId(); 
	//	if (favoriteService.isLoved(userId, productId)) product.setFavorite(true);
	}
	if (!(authentication instanceof AnonymousAuthenticationToken)) {
	    //String currentUserName = authentication.getName();
			int userId = userService.findUserByEmail(authentication.getName()).getId(); 
			if (favoriteService.isLoved(userId, productId)) product.setFavorite(true);
			model.addAttribute("isLoggedIn", true);
	}
		model.addAttribute("product", product);
		model.addAttribute("comments",comments);
		return "shopper/product";
	}

	// Hiển thị thông tin cá nhân & danh sách đơn hàng
	@GetMapping("my-account")
	public String showAccoundInfonOrder(Model model) {
		// Lấy thông tin xác thực hiện tại

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User user = currentUser.getUser();
		
		List<Order> listOrders = orderService.getOrdersOfUserId(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("orders", listOrders);
		return "shopper/account";
	}

	// Cập nhật thông tin cá nhân (địa chỉ)
	@GetMapping("my-account/edit")
	public String showUpdateInfoPage(Model model) {
		// Lấy thông tin xác thực hiện tại

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User user = currentUser.getUser();

		model.addAttribute("user", user);
		return "shopper/account_address_fields";
	}
	@PostMapping("my-account/edit")
	//Xử lý cập nhật thông tin tài khoản
	public String processUpdateInfo(@RequestParam(name="fullname") String fullname, 
            @RequestParam(name="phone") String phone, 
            @RequestParam(name="address") String address, Model model) {
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userService.update(currentUser.getId(), fullname, phone, address) ;
		if ( user!= null) {

			model.addAttribute("user", user);
			return "shopper/account_address_fields";
		}
		return null;
		
			
	}
	//chi tiết đơn đặt hàng (huỷ đơn nếu trạng thái của đơn là chưa xét duyệt)
	@GetMapping("my-account/order/{id}")
	public String showOrderDetail(@PathVariable("id") int id,Model model) {
		Order order = orderService.getOrderById(id);
		if(order.getShippingStatus().equalsIgnoreCase("Chưa xét duyệt")) model.addAttribute("btnCancel", true);
		model.addAttribute("order", order);
		return "shopper/order-detail";
	}
	
	
	//xử lý yêu cầu huỷ đơn từ khách hàng nếu đơn hàng còn ở trạng thái chưa xét duyệt
	@PostMapping("my-account/order/cancel/{id}")
	public ResponseEntity<Object> processCancleOrder(@PathVariable("id") int id){
		Order order = orderService.cancelOrder(id);
		if(order != null)
		return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
	}
	
	//xem trang danh sách các sản phẩm yêu thích của một user
	@GetMapping("my-account/wishlist")
	public String showFavoriteList(Model model) {
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Product> favList = favoriteService.getFavoritedList(currentUser.getId());
		model.addAttribute("products" ,favList);
		return "shopper/wishlist";
	}
}
