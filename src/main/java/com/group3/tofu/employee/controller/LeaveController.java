package com.group3.tofu.employee.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
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
import org.springframework.web.multipart.MultipartFile;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.customer.model.Customer;
import com.group3.tofu.employee.model.CheckService;
import com.group3.tofu.employee.model.Checks;
import com.group3.tofu.employee.model.Employee;
import com.group3.tofu.employee.model.EmployeeSearchVO;
import com.group3.tofu.employee.model.EmployeeService;
import com.group3.tofu.employee.model.Leave;
import com.group3.tofu.employee.model.LeaveApplication;
import com.group3.tofu.employee.model.LeaveApplicationService;
import com.group3.tofu.employee.model.TaskService;
import com.group3.tofu.maintenance.Maintenance;
import com.group3.tofu.product.model.Product;

@Controller
@RequestMapping(path = { "/employee/leave" })
public class LeaveController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LeaveApplicationService leaveApplicationService;

	@GetMapping("/application")
	public String toLeaveFormPage() {
		return "employee/leave_form";
	}

	@GetMapping("/approval")
	public String toApprovalPage() {
		return "employee/leave_approval";
	}

	// 目前登入的員工詳細資料
	@ResponseBody
	@GetMapping("/my_info")
	public ResponseEntity<Employee> getMyInfo(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		Employee currentEmp = employeeService.findEmployeeById(eid);
		return ResponseEntity.status(HttpStatus.OK).body(currentEmp);
	}

	@ResponseBody
	@PostMapping("/application")
	public ResponseEntity<Map<String, String>> submitLeaveForm(@RequestBody LeaveApplication leaveapplication, Authentication authentication) {
//	public ResponseEntity<Map<String, String>> submitLeaveForm(@RequestParam("category") Integer category,
//			@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate,
//			@RequestParam("reason") String reason,
//			@RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile,
//			Authentication authentication) throws IOException {

		Map<String, String> messages = new HashMap<>();

		Integer eid = employeeService.findIdByName(authentication.getName());
		Employee currentEmp = employeeService.findEmployeeById(eid);

		
		//如果每個值都有填寫
		if (leaveapplication.getLeave().getLid() != null && leaveapplication.getBeginDate() != null && leaveapplication.getEndDate() != null) {
			
			//Leave leaveCat = leaveApplicationService.findLeaveById(category);
			Leave leaveCat = leaveApplicationService.findLeaveById(leaveapplication.getLeave().getLid());
			
			
			//LocalDate localDateBegin = LocalDate.parse(beginDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			//LocalDate localDateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate localDateBegin = leaveapplication.getBeginDate();
			LocalDate localDateEnd = leaveapplication.getEndDate();
			
			
			LeaveApplication lapp = new LeaveApplication();
			long dayslong = localDateBegin.until(localDateEnd.plusDays(1), ChronoUnit.DAYS);
			Integer days = Integer.parseInt(String.valueOf(dayslong));

			boolean ifAvailable = leaveApplicationService.checkIfThereAreLeftAnnualDays(currentEmp, days);
			boolean ifTaken = leaveApplicationService.ifDateIsAlreadyTaken(localDateBegin, eid);
				
			
			// 如果這期間沒請過假
			if (ifTaken == false) {
				
				// 請假一定是要是零天以上
				if (localDateBegin != null && localDateEnd != null && localDateBegin.isAfter(localDateEnd) != true) {
					
					// 如果請的假別是特休
					if (leaveCat.getLeaveCategory() != null && leaveCat.getLeaveCategory().equals("特別休假")) {
						
						// 如果還有剩餘的特休可請
						if (ifAvailable == true) {
							lapp.setEmployee(currentEmp);
							lapp.setLeave(leaveCat);
							lapp.setBeginDate(localDateBegin);
							lapp.setEndDate(localDateEnd);
							lapp.setLeaveDays(days);
							if (leaveapplication.getLeaveReason() != "" && leaveapplication.getLeaveReason() != null) {
								lapp.setLeaveReason(leaveapplication.getLeaveReason());
							}
							
							if (leaveapplication.getFileAttached() != null) {

								try {
									lapp.setFileAttached(leaveapplication.getFileAttached());
								} catch (Exception e) {
									System.out.println("Error");
								}

							} else {
								lapp.setFileAttached(null);
							}

							leaveApplicationService.newLeaveApp(lapp);
							messages.put("submitResult", "您已成功提交請假申請。");
							return ResponseEntity.status(HttpStatus.OK).body(messages);
						} else { //沒有剩餘的特休可請
							String failMessage = "剩餘特休天數不足。";
							// String failMessage = "剩餘特休天數不足，您的剩餘特休天數為 " + +" 天。";
							messages.put("submitResult", failMessage);
							return ResponseEntity.status(HttpStatus.OK).body(messages);
						}
						
					} else { // 如果請的假別不是特休
						
						lapp.setEmployee(currentEmp);
						lapp.setLeave(leaveCat);
						lapp.setBeginDate(localDateBegin);
						lapp.setEndDate(localDateEnd);
						lapp.setLeaveDays(days);
						if (leaveapplication.getLeaveReason() != "" && leaveapplication.getLeaveReason() != null) {
							lapp.setLeaveReason(leaveapplication.getLeaveReason());
						}
						
						if (leaveapplication.getFileAttached() != null) {

							try {
								lapp.setFileAttached(leaveapplication.getFileAttached());
							} catch (Exception e) {
								System.out.println("Error");
							}

						} else {
							lapp.setFileAttached(null);
						}

						leaveApplicationService.newLeaveApp(lapp);
						messages.put("submitResult", "您已成功提交請假申請。");
						return ResponseEntity.status(HttpStatus.OK).body(messages);
					}
					
					
					
					
				} else {  //請假時間低於0天
					String failMessage = "請確認您的請假天數大於 0 日。";
					messages.put("submitResult", failMessage);
					return ResponseEntity.status(HttpStatus.OK).body(messages);
				}


				

				
				
			} else {
				String failMessage = "此日期期間已提出過請假申請。";
				messages.put("submitResult", failMessage);
				return ResponseEntity.status(HttpStatus.OK).body(messages);
			}
			
		} else {
			String failMessage = "請檢查是否有漏填欄位。";
			messages.put("submitResult", failMessage);
			return ResponseEntity.status(HttpStatus.OK).body(messages);
		}
		
		

	}

	// 取得此員工可以請的假別
	@ResponseBody
	@GetMapping("/leaveCat")
	public ResponseEntity<List<Leave>> getLeaveAvailable(Authentication authentication) {

		Integer eid = employeeService.findIdByName(authentication.getName());
		Employee currentEmp = employeeService.findEmployeeById(eid);
		List<Leave> leavelist = leaveApplicationService.getAvailableLeaveCat(currentEmp);

		return ResponseEntity.status(HttpStatus.OK).body(leavelist);
	}

	// 主管取得等待審核的請假申請
	@ResponseBody
	@GetMapping("/waitinglist")
	public ResponseEntity<List<LeaveApplication>> getWaitingList() {
		List<LeaveApplication> waitingApprovalList = leaveApplicationService.findWaitingApprovalList();
		return ResponseEntity.status(HttpStatus.OK).body(waitingApprovalList);
	}

	// 主管取得已經處理過的請假申請
	@ResponseBody
	@GetMapping("/processedlist")
	public ResponseEntity<List<LeaveApplication>> getProcessedList() {
		List<LeaveApplication> processedList = leaveApplicationService.findApprovedOrRejectedList();
		return ResponseEntity.status(HttpStatus.OK).body(processedList);
	}

	// 員工查詢自己送出過的假單資料
	@ResponseBody
	@GetMapping("/my_leave_application")
	public ResponseEntity<List<LeaveApplication>> getmyLeaveApplication(Authentication authentication) {
		Integer eid = employeeService.findIdByName(authentication.getName());
		List<LeaveApplication> myAppList = leaveApplicationService.findLeaveAppByEid(eid);
		return ResponseEntity.status(HttpStatus.OK).body(myAppList);
	}

	// 接收拒絕請假申請的put請求
	@ResponseBody
	@PutMapping("/approve/{appid}")
	public ResponseEntity<LeaveApplication> approve(@PathVariable(name = "appid") Integer appid) {

		LeaveApplication leaveApp = null;
		if (leaveApplicationService.exists(appid)) {
			leaveApp = leaveApplicationService.approveLeaveApplication(appid);
			return ResponseEntity.status(HttpStatus.OK).body(leaveApp);
		}
		return null;
	}

	// 接收拒絕請假申請的put請求
	@ResponseBody
	@PutMapping("/reject/{appid}")
	public ResponseEntity<LeaveApplication> reject(@PathVariable(name = "appid") Integer appid) {

		LeaveApplication leaveApp = null;
		if (leaveApplicationService.exists(appid)) {
			leaveApp = leaveApplicationService.rejectLeaveApplication(appid);
			return ResponseEntity.status(HttpStatus.OK).body(leaveApp);
		}
		return null;
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
