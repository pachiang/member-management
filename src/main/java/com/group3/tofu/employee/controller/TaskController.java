package com.group3.tofu.employee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.customer.model.Customer;
import com.group3.tofu.employee.model.Employee;
import com.group3.tofu.employee.model.EmployeeService;
import com.group3.tofu.employee.model.TaskService;
import com.group3.tofu.maintenance.Maintenance;
import com.group3.tofu.product.model.Product;

//@RestController
@Controller
@RequestMapping(path = { "/employee/task" })
public class TaskController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TaskService taskService;

//	@Autowired
//	private LeaveApplicationService leaveApplicationService;
	// 記得不能指派給沒有上班的人

	@GetMapping("/management_mtn")
	public String toMtnTaskManagementPage() {
		return "employee/task_mtn_assign";
	}

	@GetMapping("/management_book")
	public String toBookTaskManagementPage() {
		return "employee/task_book_assign";
	}

	@GetMapping("/todo_mtn")
	public String toMtnTaskListPage() {
		return "employee/task_mtn_todo";
	}

	@GetMapping("/todo_book")
	public String toBookTaskListPage() {
		return "employee/task_book_todo";
	}

	// 取得待主管分配的任務數量
	@ResponseBody
	@GetMapping("/waiting_assign")
	public ResponseEntity<Map<String, Integer>> getWaitingAssignCount() {
		Map<String, Integer> waitingCount = taskService.countWaitingAssignTasks();
		return ResponseEntity.status(HttpStatus.OK).body(waitingCount);
	}

	// 取得自己被分配到的任務數量
	@ResponseBody
	@GetMapping("/waiting_todo")
	public ResponseEntity<Map<String, Integer>> getWaitingTodoCount(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		Map<String, Integer> waitingCount = taskService.countWaitingTodoTasks(eid);
		return ResponseEntity.status(HttpStatus.OK).body(waitingCount);
	}

	// 主管查詢所有維修申請資料
	@ResponseBody
	@GetMapping("/mtnlist")
	public ResponseEntity<List<Maintenance>> getMtnRecord() {
		List<Maintenance> mtnRecord = taskService.findAllMtns();
		return ResponseEntity.status(HttpStatus.OK).body(mtnRecord);
	}

	// 員工查詢待完成的維修申請資料
	@ResponseBody
	@GetMapping("/mtn_todolist")
	public ResponseEntity<List<Maintenance>> getMtnTodos(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Maintenance> mtnTodos = taskService.findMyMtnTodos(eid);
		return ResponseEntity.status(HttpStatus.OK).body(mtnTodos);
	}

	// 主管查詢所有預約賞車資料
	@ResponseBody
	@GetMapping("/booklist")
	public ResponseEntity<List<Book>> getBookRecord() {
		List<Book> bookRecord = taskService.findAllBooks();
		return ResponseEntity.status(HttpStatus.OK).body(bookRecord);
	}

	// 員工查詢待完成的預約賞車資料
	@ResponseBody
	@GetMapping("/book_todolist")
	public ResponseEntity<List<Book>> getBookTodos(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Book> bookTodos = taskService.findMyBookTodos(eid);
		return ResponseEntity.status(HttpStatus.OK).body(bookTodos);
	}

	// 取得「顧客預約當天」沒有請假的員工資料
	@ResponseBody
	@GetMapping("/available_emplist/mtn/{mid}")
	public ResponseEntity<List<Employee>> getMtnAvailableEmpList(@PathVariable(name = "mid") Integer mid) {

		Maintenance mtn = taskService.findMtnById(mid);
		// 這邊其實應該還要判斷是否為Null，因為可能全部人都請假無法上班
		List<Employee> emplist = taskService.getAvailableEmp(mtn.getAppointment());
		return ResponseEntity.status(HttpStatus.OK).body(emplist);
	}

	@ResponseBody
	@GetMapping("/available_emplist/book/{bid}")
	public ResponseEntity<List<Employee>> getBookAvailableEmpList(@PathVariable(name = "bid") Integer bid) {
		
		Book book = taskService.findBookById(bid);
		// 這邊其實應該還要判斷是否為Null，因為可能全部人都請假無法上班
		System.out.println(book.getBook_date());
		List<Employee> emplist = taskService.getAvailableEmp(book.getBook_date());
		return ResponseEntity.status(HttpStatus.OK).body(emplist);
	}

	// 接收指派員工的put請求
	@ResponseBody
	@PutMapping("/management_mtn/{mid}")
	public ResponseEntity<List<Maintenance>> assignEmployeeToMtn(@PathVariable(name = "mid") Integer mid,
			@RequestBody Maintenance maintenance) {

		if (taskService.ifMtnExists(mid)) {
			taskService.setEmployeeToMtnTask(mid, maintenance.getEid());
		}

		List<Maintenance> mtnlist = taskService.findAllMtns();

		return ResponseEntity.status(HttpStatus.OK).body(mtnlist);
	}

	@ResponseBody
	@PutMapping("/management_book/{bid}")
	public ResponseEntity<List<Book>> assignEmployeeToBook(@PathVariable(name = "bid") Integer bid,
			@RequestBody Book book) {

		if (taskService.ifBookExists(bid)) {
			taskService.setEmployeeToBookTask(bid, book.getF_employee_id());
		}

		List<Book> booklist = taskService.findAllBooks();

		return ResponseEntity.status(HttpStatus.OK).body(booklist);
	}

	// 接收員工完成任務的put請求
	@ResponseBody
	@PutMapping("/management_mtn/finished/{mid}")
	public ResponseEntity<List<Maintenance>> setClosedToMtn(@PathVariable(name = "mid") Integer mid,
			Authentication authentication) {

		if (taskService.ifMtnExists(mid)) {
			taskService.setClosedToMtnTask(mid);
		}

		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Maintenance> mtnTodos = taskService.findMyMtnTodos(eid);

		return ResponseEntity.status(HttpStatus.OK).body(mtnTodos);
	}

	@ResponseBody
	@PutMapping("/management_book/finished/{bid}")
	public ResponseEntity<List<Book>> setClosedToBook(@PathVariable(name = "bid") Integer bid,
			Authentication authentication) {

		if (taskService.ifBookExists(bid)) {
			taskService.setClosedToBookTask(bid);
		}

		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Book> bookTodos = taskService.findMyBookTodos(eid);

		return ResponseEntity.status(HttpStatus.OK).body(bookTodos);
	}
	
	// 修改客戶預約賞車的時間
	@ResponseBody
	@PutMapping("/management_book/edit/{bid}")
	public ResponseEntity<List<Book>> setDateToBook(@PathVariable(name = "bid") Integer bid, @RequestBody Book book,
			Authentication authentication) {

		if (taskService.ifBookExists(bid)) {
			taskService.setDateToBookTask(bid, book.getBook_date());
		}

		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Book> bookTodos = taskService.findMyBookTodos(eid);

		return ResponseEntity.status(HttpStatus.OK).body(bookTodos);
	}
	
	@ResponseBody
	@PutMapping("/management_mtn/edit/{mid}")
	public ResponseEntity<List<Maintenance>> setDateToMtn(@PathVariable(name = "mid") Integer mid, @RequestBody Maintenance maintenance,
			Authentication authentication) {

		if (taskService.ifMtnExists(mid)) {
			taskService.setDateToMtnTask(mid, maintenance.getAppointment());
		}

		Integer eid = employeeService.findIdByName(authentication.getName());
		List<Maintenance> mtnTodos = taskService.findMyMtnTodos(eid);

		return ResponseEntity.status(HttpStatus.OK).body(mtnTodos);
	}
	

	// 取得所有客戶資料
	@ResponseBody
	@GetMapping("/customerlist")
	public ResponseEntity<List<Customer>> getCustomerList() {
		List<Customer> cuslist = taskService.findAllCustomers();
		return ResponseEntity.status(HttpStatus.OK).body(cuslist);
	}

	// 取得所有產品資料
	@ResponseBody
	@GetMapping("/productlist")
	public ResponseEntity<List<Product>> getProductList() {
		List<Product> productlist = taskService.findAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(productlist);
	}
	
	// 取得產品圖片
	@GetMapping("/getProductPhoto/{pid}")
	public ResponseEntity<byte[]> getEmployeePhotoByEID(@PathVariable Integer pid) {

		byte[] productSamplePhoto = taskService.findPhotoByProductId(pid);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new ResponseEntity<byte[]>(productSamplePhoto, headers, HttpStatus.OK);

	}

	// 多條件查詢員工
//	@ResponseBody
//	@GetMapping("/search")
//	public List<Employee> searchEmp(@RequestParam(value = "firstName", required = false) String firstName,
//			@RequestParam(value = "lastName", required = false) String lastName,
//			@RequestParam(value = "hireDateStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateStart,
//			@RequestParam(value = "hireDateEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateEnd,
//			@RequestParam(value = "department", required = false) String department,
//			@RequestParam(value = "position", required = false) String position,
//			@RequestParam(value = "salaryMin", required = false) Integer salaryMin,
//			@RequestParam(value = "salaryMax", required = false) Integer salaryMax,
//			@RequestParam(defaultValue = "1", value = "pageNumber") Integer pageNumber,
//			@RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) throws Exception {
//
//		EmployeeSearchVO searchEmp = new EmployeeSearchVO(firstName, lastName, hireDateStart, hireDateEnd, department,
//				position, salaryMin, salaryMax);
////		System.out.println(searchEmp);
//		Page<Employee> resultPage = employeeService.searchEmployee(pageNumber, pageSize, searchEmp);
////		Page<Employee> resultPage = employeeService.searchEmployee(pageNumber, pageSize, firstName, lastName, hireDateStart, hireDateEnd, department, position);
//		List<Employee> resultEmpList = resultPage.getContent();
//
//		return resultEmpList;
//	}

}
