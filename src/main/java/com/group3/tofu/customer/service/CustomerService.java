package com.group3.tofu.customer.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.book.model.BookDao;
import com.group3.tofu.customer.model.Customer;
import com.group3.tofu.customer.model.CustomerDao;
import com.group3.tofu.employee.model.Employee;
import com.group3.tofu.employee.model.EmployeeDao;
import com.group3.tofu.order.model.bean.Order;
import com.group3.tofu.order.model.dao.OrderDAO;
import com.group3.tofu.product.model.Product;
import com.group3.tofu.product.model.ProductDao;

@Service
public class CustomerService {

	// 先找到專屬的Dao
//	@Autowired
//	private CustomerDao customerDao;

	private final OrderDAO orderDAO;
	private final ProductDao productDAO;
//	private final GiftDAO giftDAO;
	private final EmployeeDao employeeDAO;
	private final CustomerDao customerDao;
//	private final MailService mailService;
	private final BookDao bookDao;

	public CustomerService(CustomerDao customerDao, OrderDAO orderDAO, ProductDao productDAO,
			EmployeeDao employeeDAO, BookDao bookDao) {
		this.customerDao = customerDao;
		this.orderDAO = orderDAO;
		this.productDAO = productDAO;
		this.employeeDAO = employeeDAO;
		this.bookDao = bookDao;
	}

	// findAllCustomer
	public List<Customer> findAllCustomer() {
		return customerDao.findAll();
	}

	// findPhotoById
	public Customer findPhotoById(Integer customer_id) {

		Optional<Customer> option = customerDao.findById(customer_id);

		if (option.isPresent()) {
			Customer customerPhoto = option.get();
			return customerPhoto;
		}
		return null;
	}

	// find Customer by email and password
	public Customer findEmailAndPassword(String email, String password) {

		Customer findByEmailAndPassword = customerDao.findByEmailAndPassword(email, password);

		if (findByEmailAndPassword == null) {
			return null;
		}
		return findByEmailAndPassword;
	}

	// find Customer by email
	public Optional<Customer> findCustomerByEmail(String email) {
		return Optional.ofNullable(customerDao.findByEmail(email));
	}

	// create member
	public Customer createMember(Customer customer) {
		return customerDao.save(customer);
	}

	@Transactional
	public Customer updateMember(Customer customer) {
		return customerDao.save(customer);
	}

	// 檢查帳號是否已經被註冊(一般的controller用)
	public Optional<Customer> findByAccount(String account) {
		return customerDao.findCustomerByAccount(account);
	}

	
	

	

	@Modifying
	@Transactional
	public void enableAccount(String email) {
		// 如果找不到就回傳空值
		Optional<Customer> optional = findCustomerByEmail(email);
		if (optional.isEmpty()) {
			return;
		}

		// 如果有驗證成功，就將enabled狀態設為1
		Customer customer = optional.get();
		customer.setEnabled(true);

	}
	
	
	//將coustomer 的 權限(enabled) 從 1 -> 0
	@Modifying
	@Transactional
	public void enableAccountStop(Integer id) {
		Optional<Customer> option = customerDao.findById(id);
		if(option.isPresent()) {
			Customer customer = option.get();
			
			if(customer.getEnabled() == true) {
				customer.setEnabled(false);
			}
			
			return;
		}
			
	}
	
	//將coustomer 的 權限(enabled) 從 0 -> 1
	@Modifying
	@Transactional
	public void enableAccountRecover(Integer id) {
		Optional<Customer> option = customerDao.findById(id);
		if(option.isPresent()) {
			Customer customer = option.get();
			
			if(customer.getEnabled() == false) {
				customer.setEnabled(true);				
			}
			
			return;
		}
			
	}

	// find Customer by email and password isEnabled
	public boolean isEnabled(String email, String password) {

		Customer customer = customerDao.findByEmailAndPassword(email, password);

		if (customer != null) {
			return customer.getEnabled();
		}
		return false;
	}

	// update profile
	@Transactional
	public Customer updateProfile(Customer c, MultipartFile uploadImg, HttpSession session) throws IOException {
		// 先把之前存在session裡面的物件拿出來，因為我要透過email找人
		Customer oldCustomer = (Customer) session.getAttribute("loggedInCustomer");

		// 把email設進去
		String oldEmail = oldCustomer.getEmail();

		System.out.println(c.getName() + " " + c.getPhone());

		Optional<Customer> option = findCustomerByEmail(oldEmail);

		// 如果email存在，我就透過使用者輸入的值，並將其設值進去
		if (option.isPresent()) {
			Customer customer = option.get();
			customer.setName(c.getName());
			customer.setPhone(c.getPhone());
			customer.setBirthday(c.getBirthday());
			customer.setAge(c.getAge());
			customer.setGender(c.getGender());
			customer.setAddress(c.getAddress());

			System.out.println(c.getGender());
			// 單獨判斷圖片，如果有上傳圖片，我就設圖片給他，反之如果沒有設圖片進去，我就用預設圖片就好
			if (!uploadImg.isEmpty()) {
				customer.setPhoto(uploadImg.getBytes());
			}

			System.out.println("修改個人資料成功!!");

			session.setAttribute("loggedInCustomer", customer);

			return customer;
		}

		return null;

	}

	// 查詢歷史訂單，透過order裡面的customerId找人
	public List<Order> findByCustomerId(Integer customerId) {
		return orderDAO.findByCustomerId(customerId);
	}

	// findCustomerById
	public Customer findCustomerById(Integer id) {
		Optional<Customer> op = customerDao.findById(id);
		if (!op.isEmpty()) {
			return op.get();
		}
		return null;
	}

	// findProductById
	public Product findProductById(Integer id) {
		Optional<Product> op = productDAO.findById(id);
		if (!op.isEmpty()) {
			return op.get();
		}
		return null;
	}

	

	// findEmployeeById
	public Employee findEmployeeById(Integer id) {
		Optional<Employee> op = employeeDAO.findById(id);
		if (!op.isEmpty()) {
			return op.get();
		}
		return null;
	}

	// 查詢預約賞車的表單，透過book裡面的customerId找人
	public List<Book> findBookByCustomerId(Integer customerId) {
		return bookDao.findBookByCustomerId(customerId);
	}
	
	
	//製作分頁功能
	public Page<Customer> findByPage(Integer pageNumber){
		
		//拿到Pageable物件
		PageRequest pgb = PageRequest.of(pageNumber-1 , 8);
		
		Page<Customer> page = customerDao.findAll(pgb);
		
		return page;
	
	}
	
	
	


}
