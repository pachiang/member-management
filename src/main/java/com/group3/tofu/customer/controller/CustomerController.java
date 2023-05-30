package com.group3.tofu.customer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.customer.model.Customer;
import com.group3.tofu.customer.service.CustomerService;
import com.group3.tofu.employee.model.Employee;
import com.group3.tofu.order.model.bean.Order;
import com.group3.tofu.product.model.Product;

//@SessionAttributes(names= {"accountName"})
@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

//	@Autowired
//	private OrderService orderService;

	// 尋找屬於該customer的所有訂單
	@GetMapping(path = "/customer/queryOrder")
	public String findAllCustomerByOrderId(Model model, HttpSession session) {

		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		Integer cid = customer.getCustomer_id();

		List<Order> orders = customerService.findByCustomerId(cid);

		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Employee> employees = new ArrayList<Employee>();

		for (Order order : orders) {
			System.out.println("----------------------------");
			System.out.println(order);
			Integer f_product_id = order.getF_product_id();
			Integer f_employee_id = order.getF_employee_id();

			// product productDao 用f_product_id找到Product物件
			Product product = customerService.findProductById(f_product_id);

			// employee EmployeeDao 用f_employee_id找到Employee物件
			Employee employee = customerService.findEmployeeById(f_employee_id);

			products.add(product);

			employees.add(employee);

		}
		session.setAttribute("orders", orders);
		session.setAttribute("products", products);
		session.setAttribute("employees", employees);

		System.out.println("查詢歷史訂單成功!");
		return "customer/queryOrder";
	}

	// 尋找屬於該customer的所有預約賞車紀錄
	@GetMapping(path = "/customer/queryBook")
	public String findAllCustomerByBookId(Model model, HttpSession session) {

		Customer customer = (Customer) session.getAttribute("loggedInCustomer");
		Integer cid = customer.getCustomer_id();

		List<Book> books = customerService.findBookByCustomerId(cid);

		ArrayList<Product> products = new ArrayList<Product>();
		ArrayList<Employee> employees = new ArrayList<Employee>();

		for (Book book : books) {

			Integer f_product_id = book.getF_product_id();
			
			Integer f_employee_id = book.getF_employee_id();

			// product productDao 用f_product_id找到Product物件
			Product product = customerService.findProductById(f_product_id);
			if (product == null) {
				Product product2 = new Product();
				products.add(product2);
			} else {
				products.add(product);
			}

			// employee EmployeeDao 用f_employee_id找到Employee物件
			if(f_employee_id == null) {
				Employee employee = new Employee();
				employees.add(employee);
			}else {
				Employee employee = customerService.findEmployeeById(f_employee_id);
				employees.add(employee);
			}


		}
		model.addAttribute("books", books);
		model.addAttribute("products", products);
		model.addAttribute("employees", employees);

		System.out.println("查詢預約賞車表單成功!");
		return "customer/queryBook";
	}

	// 顯示圖片在畫面上的controller，專門處理圖片的
	// 尋找customer的photo
	@GetMapping(path = "/downloadPhoto/{customer_id}")
	public ResponseEntity<byte[]> downloadPhoto(@PathVariable Integer customer_id) throws IOException {

		// 先從CustomerService裡面找findPhotoById的方法
		Customer photo = customerService.findPhotoById(customer_id);

		// 透過photo將file取出來
		byte[] photoFile = photo.getPhoto();

		// 重點是為了改header裡面的結構
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		// 資料, header, 回應標頭的狀態碼
		return new ResponseEntity<byte[]>(photoFile, headers, HttpStatus.OK);
	}






	// 更新個人資料
	@PostMapping(path = "customer/update")
	public String updateProfile(@ModelAttribute Customer c, @RequestParam(required = false) MultipartFile uploadImg,
			HttpSession session, Model m) throws IOException {

		customerService.updateProfile(c, uploadImg, session);
		m.addAttribute("success", "true");
		System.out.println(m);

		return "customer/updateProfile";
	}

	// 測試的controller
	@GetMapping("/michael")
	public String michael() {
		System.out.println("近來michael了");
		return "customer/login";
	}

	

}
