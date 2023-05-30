package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = { Exception.class })
public class CheckService {

	@Autowired
	private CheckDao checkDao;

	@Autowired
	private EmployeeDao employeeDao;

	// 計算有幾筆資料
	public long count() {
		return checkDao.count();
	}

	// 新增一筆打卡資料
	public void addAnNewCheck(Integer eid, Date checkInTime) {

		Employee emp = employeeDao.findById(eid).get();

		LocalDate todaysDate = LocalDate.now();
		Checks todaysCheck = checkDao.findTodaysCheck(eid, todaysDate);

		if (todaysCheck == null) {
			Checks newCheck = new Checks(emp, checkInTime);
			checkDao.save(newCheck);
		}

	}

	// 查詢今天是否已經打卡
//	public boolean findIfCheckedToday(Integer eid, LocalDate checkintime) {
//		LocalDate todaysDate = LocalDate.now();
//		boolean ifCheckedToday = false;
//		Checks todaysCheck = checkDao.findTodaysCheck(eid, todaysDate);
//		if (todaysCheck != null) {
//			ifCheckedToday = true;
//			return ifCheckedToday;
//		}
//		return ifCheckedToday;
//	}

	public Checks findTodaysCheck(Integer eid) {
		LocalDate todaysDate = LocalDate.now();
		Checks todaysCheck = checkDao.findTodaysCheck(eid, todaysDate);
		if (todaysCheck != null) {
			return todaysCheck;
		}
		return null;
	}

	// 修改下班時間
	public Checks updateCheckOutTime(Integer eid, Date checkOutTime) {

		LocalDate todaysDate = LocalDate.now();
		Checks todaysCheck = checkDao.findTodaysCheck(eid, todaysDate);
		Employee emp = employeeDao.findById(eid).get();
		List<Checks> checks = emp.getChecks();
		if (todaysCheck != null) {
			// 更新 check
			// 直接 get 出來 set
			todaysCheck.setCheckOutTime(checkOutTime);
			// 用 @modifying 的做法來update，稍嫌麻煩
//			checkDao.updateCheckOutTime(checkOutTime, eid, todaysDate);
			// 更新 employee
			checks.get(checks.size() - 1).setCheckOutTime(checkOutTime);
			todaysCheck.setEmployee(emp);
			return todaysCheck;
		}
		return null;

	}

	// 查詢所有員工的所有打卡紀錄
	public List<Checks> findAll() {
		return checkDao.findAll();
	}

	// 多條件查詢功能
	public Page<Checks> searchChecks(Integer pageNumber, int size, CheckSearchVO searchCheck) throws Exception {

		// 分頁功能
		Pageable pageable = PageRequest.of(pageNumber - 1, size, Sort.by(Direction.ASC, "cid"));
		
		Specification<Checks> specification = new Specification<Checks>() {
			private static final long serialVersionUID = 1L;

			// 建立查詢條件
			// checks裡面沒有員工名字怎麼找到...
			@Override
			public Predicate toPredicate(Root<Checks> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

				// Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
				Join<Checks, Employee> joinEmpRoot = root.join("employee");

				List<Predicate> list = new ArrayList<>();

				if (StringUtils.isNotBlank(searchCheck.getName())) {
					// list.add(cb.like(joinEmpRoot.get("firstName").as(String.class), "%" +
					// searchCheck.getName() + "%"));
					list.add(cb.or(cb.like(joinEmpRoot.get("firstName").as(String.class), "%" + searchCheck.getName() + "%"), cb.like(joinEmpRoot.get("lastName").as(String.class), "%" + searchCheck.getName() + "%")));
				}

				if (searchCheck.getCheckDate() != null && !"".equals(searchCheck.getCheckDate().toString())) {
					list.add(cb.and(cb.greaterThanOrEqualTo(root.get("checkInTime").as(LocalDate.class), searchCheck.getCheckDate()), cb.lessThanOrEqualTo(root.get("checkInTime").as(LocalDate.class), searchCheck.getCheckDate().plusDays(1))));
//					list.add(cb.greaterThanOrEqualTo(root.get("checkInTime").as(LocalDate.class),
//							searchCheck.getCheckDate()));
				}
//				if (searchCheck.getCheckInTime() != null && !"".equals(searchCheck.getCheckInTime().toString())) {
//					list.add(cb.greaterThanOrEqualTo(root.get("checkInTime").as(LocalTime.class), searchCheck.getCheckInTime())));
//				}
				
				return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
			}
		};

		Page<Checks> resultPage = checkDao.findAll(specification, pageable);
		return resultPage;

	}
	
	public List<Checks> searchChecksWithoutPage(CheckSearchVO searchCheck) throws Exception {

		Specification<Checks> specification = new Specification<Checks>() {
			private static final long serialVersionUID = 1L;

			// 建立查詢條件
			@Override
			public Predicate toPredicate(Root<Checks> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

				Join<Checks, Employee> joinEmpRoot = root.join("employee");
				List<Predicate> list = new ArrayList<>();

				if (StringUtils.isNotBlank(searchCheck.getName())) {
					list.add(cb.or(cb.like(joinEmpRoot.get("firstName").as(String.class), "%" + searchCheck.getName() + "%"), cb.like(joinEmpRoot.get("lastName").as(String.class), "%" + searchCheck.getName() + "%")));
				}

				if (searchCheck.getCheckDate() != null && !"".equals(searchCheck.getCheckDate().toString())) {
					list.add(cb.and(cb.greaterThanOrEqualTo(root.get("checkInTime").as(LocalDate.class), searchCheck.getCheckDate()), cb.lessThanOrEqualTo(root.get("checkInTime").as(LocalDate.class), searchCheck.getCheckDate().plusDays(1))));
				}
				
				return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
			}
		};

		List<Checks> resultPage = checkDao.findAll(specification);
		return resultPage;

	}

//	// 根據員工ID查詢一筆員工資料
//	public Employee findEmployeeById(Integer id) {
//		Optional<Employee> op = employeeDao.findById(id);
//
//		if (op.isPresent()) {
//			return op.get();
//		}
//
//		return null;
//	}
//
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
//	// 建立分頁排序請求
////	private PageRequest buildPageRequest(int page, int size) {
////		Sort sort = Sort.by(Direction.ASC, "eid");
////		return PageRequest.of(page, size, sort);
////	}

//	public Integer findIdByName(String account) {
//		Employee emp = employeeDao.findByAccount(account);
//
//		if (emp != null) {
//			return emp.getEid();
//		}
//
//		return null;
//	}

}
