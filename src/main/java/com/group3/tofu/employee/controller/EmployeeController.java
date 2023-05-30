package com.group3.tofu.employee.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group3.tofu.employee.model.CheckDto;
import com.group3.tofu.employee.model.CheckSearchVO;
import com.group3.tofu.employee.model.CheckService;
import com.group3.tofu.employee.model.Checks;
import com.group3.tofu.employee.model.Employee;
import com.group3.tofu.employee.model.EmployeeSearchVO;
import com.group3.tofu.employee.model.EmployeeService;

//@RestController
@Controller
@RequestMapping(path = { "/employee" })
//@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CheckService checkService;

//	@Autowired
//	HttpSession session;

	@GetMapping("/")
	public String toEmployeePage() {
		return "employee/mainpage";
	}

	@GetMapping("/edit_my_profile")
	public String toEditPage() {
		return "employee/edit_profile";
	}

	@GetMapping("/check")
	public String toCheckInPage() {
		return "employee/checkin";
	}

	@GetMapping("/allcheckin")
	public String toCheckRecordPage() {
		return "employee/check_record";
	}

//	@ResponseBody
//	@GetMapping("/all")
//	public String listAll(Model model) {
//
//		List<Employee> employeeList = employeeService.findAll();
//		model.addAttribute("employeeList", employeeList);
//
//		return "employee/employee";
//	}

	@ResponseBody
	@GetMapping("/allJson")
	public List<Employee> listAllJson(Model model) {

		List<Employee> employeeList = employeeService.findAll();
		model.addAttribute("employeeList", employeeList);

		return employeeList;
	}

	// 測試一下資料有沒有拿到
//	@ResponseBody
//	@GetMapping("/all")
//	public List<Employee> listAll(Model model) {
//		List<Employee> employeeList = employeeService.findAll();
//		model.addAttribute("employeeList", employeeList);
//		return employeeList;
//	}

	// 一般回傳的都是文字，所以需要另一個controller來回傳圖片
	// 裡面放圖片byte[]，所以ResponseEntity<byte[]>
	@GetMapping("/getEmployeePhoto/{eid}")
	public ResponseEntity<byte[]> getEmployeePhotoByEID(@PathVariable Integer eid) {

//		ResponseEntity的要素一：資料
		Employee employee = employeeService.findEmployeeById(eid);
		byte[] empPhoto = employee.getPhoto();

//		ResponseEntity的要素二：回應標頭header，如果要回傳圖片，要改contentType
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
//		ResponseEntity的要素三：http狀態代碼，response status
		// 資料, header, response status
		return new ResponseEntity<byte[]>(empPhoto, headers, HttpStatus.OK);

	}

	@GetMapping("/edit/{id}")
	public String toEmployeeEditPage(@PathVariable(name = "id") Integer id, Model model) {
		Employee employee = employeeService.findEmployeeById(id);
		model.addAttribute("employee", employee);
		return "employee/employee_update";
	}

	@ResponseBody
	@GetMapping("/findemp/{id}")
	public Employee findById(@PathVariable(name = "id") Integer id, Model model) {
		Employee employee = employeeService.findEmployeeById(id);
		model.addAttribute("employee", employee);
//		model.addAttribute("employee", new Gson().toJson(employee));
		return employee;
	}

	// 接收修改頁面的put請求
	@ResponseBody
	@PutMapping("/edit/{id}")
	public ResponseEntity<Employee> putEditEmployee(@PathVariable("id") Integer id, @RequestBody Employee emp) {
//		Employee employee = new Employee(emp.getEid(), emp.getFirstName(), emp.getLastName(), emp.getAccount(), emp.getPassword(), emp.getGender(), emp.getBirthday(), emp.getEmail(), emp.getPhone(), emp.getDepartment(), emp.getPosition(), emp.getHireDate(), emp.getSalary(), emp.getLevel(), emp.getPhoto());

		// 處理圖片格式
//		String partSeparator = ",";
//		if (emp.getPhoto().contains(partSeparator)) {
//		  String encodedImg = data.split(partSeparator)[1];
//		  byte[] decodedImg = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
//		  Path destinationFile = Paths.get("/path/to/imageDir", "myImage.png");
//		  Files.write(destinationFile, decodedImg);
//		}
//		String s = new String(Base64.getDecoder().decode(emp.getPhoto().toString().replace("\r\n", "")),"utf-8");
//		byte[] uploadedPhoto = Base64.getDecoder().decode(emp.getPhoto());

		employeeService.updateEmployeeById(id, emp);

		// 要去找controller的話，就用redirect
//		return "redirect:/employee/all";
		return ResponseEntity.ok(emp);
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public String remove(@PathVariable(name = "id") Integer id) {

		if (employeeService.exists(id)) {
//			responseJson.put("message", "資料不存在");
//			responseJson.put("success", false);
//		} else {
//			boolean product = 
			employeeService.deleteEmployeeById(id);
//			if(product) {
//				responseJson.put("message", "刪除成功");
//				responseJson.put("success", true);
//			} else {
//				responseJson.put("message", "刪除失敗");
//				responseJson.put("success", false);
//			}
		}
		System.out.println("有呼叫到刪除功能唷");
//		return responseJson.toString();
		return "delete function called";
	}

	@GetMapping("/all")
	public String listAllEmployee() {
		return "employee/employees";
	}

//	@GetMapping("/side")
//	public String toSide() {
//		return "employee/sidebar";
//	}

	// 連到新增員工的頁面
	@GetMapping("/create")
	public String toEmployeeCreatePage() {
		return "employee/employee_create";
	}

	// 新增員工
	@PostMapping("/create")
	public String createEmployee(@RequestBody Employee emp, Model model) {

		employeeService.addAnNewEmployee(emp);

//		這邊要記得new新的，不然按下送出按鈕後，文字不會消失（雖然按下送出還是會寫進資料庫）
		Employee employee = new Employee();

		List<Employee> employeeList = employeeService.findAll();
		model.addAttribute("employeeList", employeeList);

		return "redirect:/employee/all";
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
//		Page<Employee> resultPage = employeeService.searchEmployee(pageNumber, pageSize, searchEmp);
//		List<Employee> resultEmpList = resultPage.getContent();
//
//		return resultEmpList;
//	}

	// 注意這邊回傳的格式要跟原本allJson那張表格的格式一樣
	@ResponseBody
	@PostMapping("/search")
	public ResponseEntity<List<Employee>> searchEmp(@RequestBody EmployeeSearchVO employeeSearchVO) throws Exception {

		EmployeeSearchVO searchEmp = new EmployeeSearchVO(employeeSearchVO.getName(),
				employeeSearchVO.getHireDateStart(), employeeSearchVO.getHireDateEnd(),
				employeeSearchVO.getDepartment(), employeeSearchVO.getPosition(), employeeSearchVO.getSalaryMin(),
				employeeSearchVO.getSalaryMax());
		List<Employee> resultList = employeeService.searchEmployeeWithoutPage(searchEmp);
		return ResponseEntity.status(HttpStatus.OK).body(resultList);
	}

	// 根據員工ID與當天日期查詢打卡紀錄（確認今天是否已打卡）
	@ResponseBody
	@GetMapping("/todayscheck")
	public ResponseEntity<Checks> ifCheckedToday(Authentication authentication) {

		Integer eid = employeeService.findIdByName(authentication.getName());
		Checks todaysCheck = checkService.findTodaysCheck(eid);

		return ResponseEntity.status(HttpStatus.OK).body(todaysCheck);
	}

	// 根據員工ID查詢打卡紀錄（員工頁面可看到自己的出勤紀錄用）
	@ResponseBody
	@GetMapping("/checks")
	public ResponseEntity<List<Checks>> getChecksByEmpId(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		Employee employee = employeeService.findEmployeeById(eid);
		List<Checks> checks = employee.getChecks();
		return ResponseEntity.status(HttpStatus.OK).body(checks);
	}

	// 查詢所有打卡紀錄（主管頁面可看到所有人的出勤紀錄用）
//	@ResponseBody
//	@GetMapping("/checkrecord")
//	public ResponseEntity<List<Checks>> getCheckRecord() {
//		List<Checks> checkRecord = checkService.findAll();
//		return ResponseEntity.status(HttpStatus.OK).body(checkRecord);
//	}

	@ResponseBody
	@GetMapping("/checkrecord")
	public ResponseEntity<List<CheckDto>> getCheckRecord() {
		// List<Checks> checkRecord = checkService.findAll();
		List<Employee> emplist = employeeService.findAll();
		List<CheckDto> checkDtoList = new ArrayList<>();
		// boolean ifStop = false;

		for (Employee emp : emplist) {

			for (Checks check : emp.getChecks()) {

				CheckDto checkDto = new CheckDto(check.getCid(), emp.getEid(), emp.getFirstName(), emp.getLastName(),
						check);
				checkDtoList.add(checkDto);
			}

		}

//		Set<CheckDto> dtoSet = new HashSet<CheckDto>();
//		for (CheckDto cd : checkDtoList) {
//			dtoSet.add(cd);
//		}

		return ResponseEntity.status(HttpStatus.OK).body(checkDtoList);
	}

	// 新增一筆打卡資料
	@ResponseBody
	@PostMapping("/check")
	public ResponseEntity<List<Checks>> addAnCheckIn(@RequestBody Checks checks, Authentication authentication) {

		Integer eid = employeeService.findIdByName(authentication.getName());
		Employee employee = employeeService.findEmployeeById(eid);
		checkService.addAnNewCheck(eid, checks.getCheckInTime());
		List<Checks> allChecks = employee.getChecks();

		return ResponseEntity.status(HttpStatus.OK).body(allChecks);
	}

	// 接收修改下班時間的put請求
	@ResponseBody
	@PutMapping("/check")
	public ResponseEntity<List<Checks>> addAnCheckOut(@RequestBody Checks checks, Authentication authentication) {

		Integer eid = employeeService.findIdByName(authentication.getName());
		checkService.updateCheckOutTime(eid, checks.getCheckOutTime());
		Employee employee = employeeService.findEmployeeById(eid);
		List<Checks> allChecks = employee.getChecks();

		return ResponseEntity.status(HttpStatus.OK).body(allChecks);
	}

	// 多條件查詢打卡紀錄
//	@ResponseBody
//	@GetMapping("/search_check")
//	public List<Checks> searchChecks(@RequestParam(value = "name", required = false) String name,
//			@RequestParam(value = "checkDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkDate,
//			@RequestParam(value = "checkInTime", required = false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime checkInTime,
//			@RequestParam(value = "checkOutTime", required = false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime checkOutTime,
//			@RequestParam(defaultValue = "1", value = "pageNumber") Integer pageNumber,
//			@RequestParam(defaultValue = "20", value = "pageSize") Integer pageSize) throws Exception {
//
//		CheckSearchVO searchCheck = new CheckSearchVO(name, checkDate, checkInTime, checkOutTime);
//		Page<Checks> resultPage = checkService.searchChecks(pageNumber, pageSize, searchCheck);
//		List<Checks> resultEmpList = resultPage.getContent();
//
//		return resultEmpList;
//	}

	@ResponseBody
	@PostMapping("/search_check")
	public ResponseEntity<List<CheckDto>> searchChecks(@RequestBody CheckSearchVO checkSearchVO) throws Exception {

		CheckSearchVO searchCheck = new CheckSearchVO(checkSearchVO.getName(), checkSearchVO.getCheckDate(),
				checkSearchVO.getCheckInTime(), checkSearchVO.getCheckOutTime());
		List<Checks> resultList = checkService.searchChecksWithoutPage(searchCheck);
		// List<Employee> emplist = employeeService.findAll();
		List<CheckDto> checkDtoList = new ArrayList<>();
		List<Integer> cidList = new ArrayList<>();

		for (Checks check : resultList) {

			if (!cidList.contains(check.getCid())) {
				CheckDto checkDto = new CheckDto(check.getCid(), check.getEmployee().getEid(),
						check.getEmployee().getFirstName(), check.getEmployee().getLastName(), check);
				cidList.add(check.getCid());
				checkDtoList.add(checkDto);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(checkDtoList);
	}

	// 查詢所有部門及職位
	@ResponseBody
	@GetMapping("/listForMulSearch")
	public ResponseEntity<Map<String, List<String>>> getListForMulSearch() {
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.getDepAndPos());

	}

	// 查詢員工的業績
	@ResponseBody
	@GetMapping("/sales")
	public ResponseEntity<Map<String, List<?>>> getSalesData() {

		List<Employee> empList = employeeService.findAll();
		Map<String, List<?>> salesData = new HashMap<>();
		// String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
		// "Sep", "Oct", "Nov", "Dec"};
		// new String[12];
		// List<?> monthList = new ArrayList<>(Arrays.asList(months));
		// List<?> salesList = new ArrayList<>();

		// salesList.add();
		List<Integer> eidList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<Integer> salesList = employeeService.getSalesOfEmp();

		for (Employee emp : empList) {
			eidList.add(emp.getEid());
			nameList.add(emp.getFirstName() + " " + emp.getLastName());
		}

		salesData.put("eid", eidList);
		salesData.put("name", nameList);
		salesData.put("sales", salesList);
		return ResponseEntity.status(HttpStatus.OK).body(salesData);
	}

}
