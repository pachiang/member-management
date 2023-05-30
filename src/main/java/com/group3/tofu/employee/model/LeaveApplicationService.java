package com.group3.tofu.employee.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.book.model.BookDao;
import com.group3.tofu.maintenance.Maintenance;
import com.group3.tofu.maintenance.MaintenanceDao;

@Service
@Transactional(rollbackFor = { Exception.class })
public class LeaveApplicationService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private LeaveDao leaveDao;

	@Autowired
	private LeaveApplicationDao leaveApplicationDao;

	// 計算有幾筆預約賞車資料
//	public long countBookTask() {
//		return bookDao.count();
//	}

	// 計算有幾筆維修申請資料
//	public long countMtnTask() {
//		return maintenanceDao.count();
//	}

//	// 查詢所有預約賞車資料
//	public List<Book> findAllBooks() {
//		return bookDao.findAll();
//	}
//	
//	// 查詢所有維修申請資料
//	public List<Maintenance> findAllMtns() {
//		return maintenanceDao.findAll();
//	}
//
	// 新增一筆員工資料
	public void newLeaveApp(LeaveApplication lapp) {
		leaveApplicationDao.save(lapp);
	}

	// 根據假期類別ID查詢一種假期
	public Leave findLeaveById(Integer id) {
		
		if (id != null) {
			Optional<Leave> op = leaveDao.findById(id);

			if (op.isPresent()) {
				return op.get();
			}

			return null;
		} 
		return null;
		
		
	}

	// 查詢可以請的假別
	public List<Leave> getAvailableLeaveCat(Employee emp) {

		// 計算出此員工的年資(依月份計算)
		LocalDate todaysDate = LocalDate.now();
		long longMonth = ChronoUnit.MONTHS.between(emp.getHireDate(), todaysDate);
		Integer monthTenure = Integer.parseInt(String.valueOf(longMonth));

		Leave annualLeave = leaveDao.findAnnualLeaveDaysAvailable(monthTenure);
		List<Leave> leaveAvailable = leaveDao.findLeaveAvailable(monthTenure);
		leaveAvailable.add(annualLeave);

		return leaveAvailable;
	}

	// 計算現在剩下的特休天數:也可回傳integer
	// 確認是否有剩下的特休天數
	public boolean checkIfThereAreLeftAnnualDays(Employee emp, Integer leaveDays) {

//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(asDate(emp.getHireDate()));
//		calendar.add(Calendar.MONTH, 3);
//		System.out.println("算出現在的特休要在哪一天前請完：" + date2String(calendar.getTime()));
		// System.out.println(DateUtils.date2String("yyyy-MM-dd", calendar.getTime()));

		// 計算出此員工的年資(依月份計算)
		LocalDate todaysDate = LocalDate.now();
		long longMonth = ChronoUnit.MONTHS.between(emp.getHireDate(), todaysDate);
		Integer monthTenure = Integer.parseInt(String.valueOf(longMonth));

		// 找出這個員工這階段的特休最多可以請幾天
		Leave annualLeave = leaveDao.findAnnualLeaveDaysAvailable(monthTenure);
		Integer annualMaxDays = 0;
		if (annualLeave != null) {
			annualMaxDays = annualLeave.getMaxLeaveDays();
		} else {
			return false;
		}

		// 找出這個員工的特休剩下幾天可以請
		
			Integer leaveId = annualLeave.getLid();
		
//		List<Integer> usedDaysList = leaveApplicationDao.findUsedAnnualLeave(emp.getEid(), leaveId);
//		Integer annualUsedDays = null;
//		if (usedDaysList != null) {
//			for(Integer usedDays : usedDaysList) {
//				annualUsedDays = annualUsedDays + usedDays;
//			}
//		}

		// Integer annualUsedDays = Integer.parseInt(String.valueOf(longUsedDays));

		Integer annualUsedDays = leaveApplicationDao.findUsedAnnualLeave(emp.getEid(), leaveId);
		Integer annualLeftDays = annualMaxDays - annualUsedDays;

		if (annualLeftDays > 0) {

			if (leaveDays <= annualLeftDays) {
				System.out.println("現階段特休天數上限：" + annualMaxDays);
				System.out.println("之前請過幾天特休：" + annualUsedDays);
				System.out.println("剩餘的特休天數：" + annualLeftDays);
				System.out.println("目前提出申請的特休天數：" + leaveDays);
				// return annualLeftDays-leaveDays;
				return true;
			} else {
				System.out.println("請假的天數大於剩餘特休天數，剩餘特休天數不足");
				return false;
			}
		} else {
			System.out.println("請假的天數大於剩餘特休天數，剩餘特休天數不足");
			return false;
		}

		// 找出此員工特休在年資第幾個月時會重整
//		Integer currentAlInvalidTenure = leaveDao.findWhenAnnualLeaveAvailable(monthTenure);
//		// 找出現階段特休不能用的那一天日期
//		LocalDate annualEndDate = emp.getHireDate().plusMonths(currentAlInvalidTenure);
//		System.out.println("hireDate:" + emp.getHireDate());
//		System.out.println("現階段的特休哪一天之前要請完:" + annualEndDate);

	}

	// 判斷員工請假期間是否已經請過假
	public boolean ifDateIsAlreadyTaken(LocalDate beginDate, Integer eid) {
		Integer applyFound = leaveApplicationDao.checkLeaveDateAlreadyTaken(beginDate, eid);
		System.out.println(applyFound);
		if (applyFound > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 查詢所有請假申請
	public List<LeaveApplication> findAll() {
		return leaveApplicationDao.findAll();
	}

	// 查詢所有等待審核的請假申請
	public List<LeaveApplication> findWaitingApprovalList() {
		return leaveApplicationDao.findWaitingApprovalList();
	}

	// 查詢所有處理過的請假申請
	public List<LeaveApplication> findApprovedOrRejectedList() {
		return leaveApplicationDao.findApprovedOrRejectedList();
	}

	// 根據員工ID查詢請假申請紀錄
	public List<LeaveApplication> findLeaveAppByEid(Integer eid) {

		List<LeaveApplication> myAppList = leaveApplicationDao.findMyApplicationList(eid);
		if (myAppList != null) {
			return myAppList;
		}
		return null;
	}

//	// 查詢是否有該ID請假申請存在
	public boolean exists(Integer id) {
		return leaveApplicationDao.findById(id) != null;
	}

	// 拒絕假單
	public LeaveApplication rejectLeaveApplication(Integer lid) {
		Optional<LeaveApplication> op = leaveApplicationDao.findById(lid);
		if (op.isPresent()) {
			LeaveApplication lapp = op.get();
			lapp.setManagerApproved(0);
			return lapp;
		}
		return null;
	}

	// 核准假單
	public LeaveApplication approveLeaveApplication(Integer lid) {
		Optional<LeaveApplication> op = leaveApplicationDao.findById(lid);
		if (op.isPresent()) {
			LeaveApplication lapp = op.get();
			lapp.setManagerApproved(1);
			return lapp;
		}
		return null;
	}

//	  public static Date asDate(LocalDate localDate) {
//		    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//		  }
//	  public static String date2String(Date date) {
//		  //Date date1 = Calendar.getInstance().getTime();  
//		  //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd"); 
//		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		  String strDate = dateFormat.format(date);  
//		    return strDate;
//		  }

//	// 根據ID刪除員工資料
//	public void deleteEmployeeById(Integer id) {
//		employeeDao.deleteById(id);
//	}
//
//	// 查詢所有員工
//	public List<Employee> findAll() {
//		return employeeDao.findAll();
//	}
//
//	// 查詢是否有該ID員工存在
//	public boolean exists(Integer id) {
//		return employeeDao.findById(id) != null;
//	}
//
//	// 修改資料
////	@Transactional
//	public Employee updateEmployeeById(Integer id, Employee emp) {
//
//		Optional<Employee> option = employeeDao.findById(id);
//		if (option.isPresent()) {
//			Employee employee = option.get();
//			
//			if (emp.getPassword().equals(employee.getPassword())) {
//				employee.setPassword(emp.getPassword());
//			} else {
//				// 處理密碼加密
//				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密碼加密
//				employee.setPassword("{bcrypt}" + passwordEncoder.encode(emp.getPassword())); // 以加密後的密碼覆蓋原輸入的密碼
//			}
//			
//			// 在hibernate裡面，物件在persistence狀態可以直接透過set值去修改
//			employee.setEid(emp.getEid());
//			employee.setFirstName(emp.getFirstName());
//			employee.setLastName(emp.getLastName());
//			employee.setAccount(emp.getAccount());
//			//employee.setPassword(emp.getPassword());
//			employee.setGender(emp.getGender());
//			employee.setBirthday(emp.getBirthday());
//			employee.setEmail(emp.getEmail());
//			employee.setPhone(emp.getPhone());
//			employee.setDepartment(emp.getDepartment());
//			employee.setPosition(emp.getPosition());
//			employee.setHireDate(emp.getHireDate());
//			employee.setSalary(emp.getSalary());
//			employee.setEnabled(emp.getEnabled());
//			employee.setAuthority(emp.getAuthority());
////			employee.setPhoto(uploadedPhoto);
//			employee.setPhoto(emp.getPhoto());
//
//			employeeDao.save(employee);
//			return employee;
//		}
//
//		return null;
////		try {
////			JSONObject obj = new JSONObject(json);
////			Integer id = obj.isNull("id") ? null : obj.getInt("id");
////			String name = obj.isNull("name") ? null : obj.getString("name");
////			Double price = obj.isNull("price") ? null : obj.getDouble("price");
////			String make = obj.isNull("make") ? null : obj.getString("make");
////			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");
////
////			ProductBean update = productDao.select(id);
////			update.setName(name);
////			update.setPrice(price);
////			update.setMake(null);
////			update.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
////			update.setExpire(expire);
////			
////			return productDao.update(update);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		return null;

}
