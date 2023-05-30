package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.book.model.BookDao;
import com.group3.tofu.maintenance.Maintenance;
import com.group3.tofu.maintenance.MaintenanceDao;
import com.group3.tofu.order.model.dao.OrderDAO;

@Service
@Transactional(rollbackFor = { Exception.class })
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private MaintenanceDao maintenanceDao;
	
	@Autowired
	private OrderDAO orderDao;

	// 計算有幾筆預約賞車資料
	public long countBookTask() {
		return bookDao.count();
	}

	// 計算有幾筆維修申請資料
	public long countMtnTask() {
		return maintenanceDao.count();
	}

	// 查詢所有預約賞車資料
	public List<Book> findAllBooks() {
		return bookDao.findAll();
	}
	
	// 查詢所有維修申請資料
	public List<Maintenance> findAllMtns() {
		return maintenanceDao.findAll();
	}

	// 新增一筆員工資料
	public void addAnNewEmployee(Employee emp) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密碼加密
		emp.setPassword("{bcrypt}" + passwordEncoder.encode(emp.getPassword())); // 以加密後的密碼覆蓋原輸入的密碼
		employeeDao.save(emp);
	}

	// 根據員工ID查詢一筆員工資料
	public Employee findEmployeeById(Integer id) {
		Optional<Employee> op = employeeDao.findById(id);

		if (op.isPresent()) {
			return op.get();
		}

		return null;
	}

	// 根據ID刪除員工資料
	public void deleteEmployeeById(Integer id) {
		employeeDao.deleteById(id);
	}

	// 查詢所有員工
	public List<Employee> findAll() {
		return employeeDao.findAll();
	}

	// 查詢是否有該ID員工存在
	public boolean exists(Integer id) {
		return employeeDao.findById(id) != null;
	}

	// 修改資料
//	@Transactional
	public Employee updateEmployeeById(Integer id, Employee emp) {

		Optional<Employee> option = employeeDao.findById(id);
		if (option.isPresent()) {
			Employee employee = option.get();
			
			if (emp.getPassword().equals(employee.getPassword())) {
				employee.setPassword(emp.getPassword());
			} else {
				// 處理密碼加密
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密碼加密
				employee.setPassword("{bcrypt}" + passwordEncoder.encode(emp.getPassword())); // 以加密後的密碼覆蓋原輸入的密碼
			}
			
			// 在hibernate裡面，物件在persistence狀態可以直接透過set值去修改
			employee.setEid(emp.getEid());
			employee.setFirstName(emp.getFirstName());
			employee.setLastName(emp.getLastName());
			employee.setAccount(emp.getAccount());
			//employee.setPassword(emp.getPassword());
			employee.setGender(emp.getGender());
			employee.setBirthday(emp.getBirthday());
			employee.setEmail(emp.getEmail());
			employee.setPhone(emp.getPhone());
			employee.setDepartment(emp.getDepartment());
			employee.setPosition(emp.getPosition());
			employee.setHireDate(emp.getHireDate());
			employee.setSalary(emp.getSalary());
			employee.setEnabled(emp.getEnabled());
			employee.setAuthority(emp.getAuthority());
//			employee.setPhoto(uploadedPhoto);
			employee.setPhoto(emp.getPhoto());

			employeeDao.save(employee);
			return employee;
		}

		return null;
//		try {
//			JSONObject obj = new JSONObject(json);
//			Integer id = obj.isNull("id") ? null : obj.getInt("id");
//			String name = obj.isNull("name") ? null : obj.getString("name");
//			Double price = obj.isNull("price") ? null : obj.getDouble("price");
//			String make = obj.isNull("make") ? null : obj.getString("make");
//			Integer expire = obj.isNull("expire") ? null : obj.getInt("expire");
//
//			ProductBean update = productDao.select(id);
//			update.setName(name);
//			update.setPrice(price);
//			update.setMake(null);
//			update.setMake(DatetimeConverter.parse(make, "yyyy-MM-dd"));
//			update.setExpire(expire);
//			
//			return productDao.update(update);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;

	}

	// 多條件查詢測試
//	public Page<Employee> findBySpec(int page, int size){
//		PageRequest pageReq = this.buildPageRequest(page, size);
//		Page<Employee> employees = this.employeeDao.findAll(new MySpec(), pageReq);
//		return employees;
//	}

	// 建立分頁排序請求
//	private PageRequest buildPageRequest(int page, int size) {
//		Sort sort = Sort.by(Direction.ASC, "eid");
//		return PageRequest.of(page, size, sort);
//	}

	// 多條件查詢功能
//	@Override
	public Page<Employee> searchEmployee(Integer pageNumber, int size, EmployeeSearchVO searchEmp) throws Exception {

		// 分頁功能
		Pageable pageable = PageRequest.of(pageNumber - 1, size, Sort.by(Direction.ASC, "eid"));

		Specification<Employee> specification = new Specification<Employee>() {
			private static final long serialVersionUID = 1L;

			// 建立查詢條件
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

//			List<Predicate> list = new ArrayList<>(10);
				List<Predicate> list = new ArrayList<>();

//	         if(StringUtils.isNotBlank(firstName)){
				if (StringUtils.isNotBlank(searchEmp.getName())) {
//	        	list.add(cb.like(root.get("firstName").as(String.class), "%" + firstName + "%"));
					list.add(cb.or(cb.like(root.get("firstName").as(String.class), "%" + searchEmp.getName() + "%"), cb.like(root.get("lastName").as(String.class), "%" + searchEmp.getName() + "%")));
				}

//	         if(StringUtils.isNotBlank(lastName)){
//				if (StringUtils.isNotBlank(searchEmp.getLastName())) {
////		        list.add(cb.like(root.get("lastName").as(String.class), "%" + lastName + "%"));
//					list.add(cb.like(root.get("lastName").as(String.class), "%" + searchEmp.getLastName() + "%"));
//				}

//	         if(hireDateStart != null){
				if (searchEmp.getHireDateStart() != null && !"".equals(searchEmp.getHireDateStart().toString())) {

//	         if(StringUtils.isNotBlank(searchEmp.getHireDateStart().toString())){
//	        	 if(StringUtils.isNotBlank(hireDateStart.toString())){
//	        		 list.add(cb.greaterThanOrEqualTo(root.get("hireDateStart").as(String.class), hireDateStart));
					list.add(cb.greaterThanOrEqualTo(root.get("hireDate").as(LocalDate.class),
							searchEmp.getHireDateStart()));
//	        	 }
//		     }
				}
				if (searchEmp.getHireDateEnd() != null && !"".equals(searchEmp.getHireDateEnd().toString())) {

//		         if(StringUtils.isNotBlank(searchEmp.getHireDateEnd().toString())){
//		        	 if(StringUtils.isNotBlank(hireDateStart.toString())){
//		        		 list.add(cb.lessThanOrEqualTo(root.get("hireDateStart").as(String.class), hireDateStart));
					list.add(
							cb.lessThanOrEqualTo(root.get("hireDate").as(LocalDate.class), searchEmp.getHireDateEnd()));
//		        	 }
//			     }
				}
//	         if(StringUtils.isNotBlank(hireDateEnd.toString())){
//	         if(hireDateEnd != null){
//	        	 if(StringUtils.isNotBlank(hireDateEnd.toString())){
//	        		 list.add(cb.lessThanOrEqualTo(root.get("hireDateEnd").as(LocalDate.class), hireDateEnd));
//	        	 }
//		     }

				if (StringUtils.isNotBlank(searchEmp.getPosition())) {
					list.add(cb.equal(root.get("position").as(String.class), searchEmp.getPosition()));
				}
//	         if(StringUtils.isNotBlank(position)){
//		        	list.add(cb.equal(root.get("position").as(String.class), position));
//		     }

				if (StringUtils.isNotBlank(searchEmp.getDepartment())) {
					list.add(cb.equal(root.get("department").as(String.class), searchEmp.getDepartment()));
				}
//	         if(StringUtils.isNotBlank(department)){
//		        	list.add(cb.equal(root.get("department").as(String.class), department));
//		     }

				if (searchEmp.getSalaryMin() != null && !"".equals(searchEmp.getSalaryMin().toString())) {
					list.add(cb.greaterThanOrEqualTo(root.get("salary").as(Integer.class), searchEmp.getSalaryMin()));
				}
				if (searchEmp.getSalaryMax() != null && !"".equals(searchEmp.getSalaryMax().toString())) {
					list.add(cb.lessThanOrEqualTo(root.get("salary").as(Integer.class), searchEmp.getSalaryMax()));
				}

				return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
			}
		};

		Page<Employee> resultPage = employeeDao.findAll(specification, pageable);
		return resultPage;

	}
	
	public List<Employee> searchEmployeeWithoutPage(EmployeeSearchVO searchEmp) throws Exception {

		Specification<Employee> specification = new Specification<Employee>() {
			private static final long serialVersionUID = 1L;

			// 建立查詢條件
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<>();

				if (StringUtils.isNotBlank(searchEmp.getName())) {
						list.add(cb.or(cb.like(root.get("firstName").as(String.class), "%" + searchEmp.getName() + "%"), cb.like(root.get("lastName").as(String.class), "%" + searchEmp.getName() + "%")));
					}

				if (searchEmp.getHireDateStart() != null && !"".equals(searchEmp.getHireDateStart().toString())) {

					list.add(cb.greaterThanOrEqualTo(root.get("hireDate").as(LocalDate.class),
							searchEmp.getHireDateStart()));
				}
				if (searchEmp.getHireDateEnd() != null && !"".equals(searchEmp.getHireDateEnd().toString())) {

					list.add(
							cb.lessThanOrEqualTo(root.get("hireDate").as(LocalDate.class), searchEmp.getHireDateEnd()));
				}

				if (StringUtils.isNotBlank(searchEmp.getPosition())) {
					list.add(cb.equal(root.get("position").as(String.class), searchEmp.getPosition()));
				}

				if (StringUtils.isNotBlank(searchEmp.getDepartment())) {
					list.add(cb.equal(root.get("department").as(String.class), searchEmp.getDepartment()));
				}

				if (searchEmp.getSalaryMin() != null && !"".equals(searchEmp.getSalaryMin().toString())) {
					list.add(cb.greaterThanOrEqualTo(root.get("salary").as(Integer.class), searchEmp.getSalaryMin()));
				}
				if (searchEmp.getSalaryMax() != null && !"".equals(searchEmp.getSalaryMax().toString())) {
					list.add(cb.lessThanOrEqualTo(root.get("salary").as(Integer.class), searchEmp.getSalaryMax()));
				}

				return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
			}
		};

		List<Employee> resultPage = employeeDao.findAll(specification);
		return resultPage;

	}

	public Integer findIdByName(String account) {
		Employee emp = employeeDao.findByAccount(account);

		if (emp != null) {
			return emp.getEid();
		}

		return null;
	}
	
	// 查詢所有部門及職位（for下拉選單）
	public Map<String, List<String>> getDepAndPos() {
		
		List<String> depList = employeeDao.findAllDepartment();
		List<String> posList = employeeDao.findAllPosition();
		
		Map<String, List<String>> depAndPos = new HashMap<>();
		depAndPos.put("departmentList", depList);
		depAndPos.put("positionList", posList);

		return depAndPos;
	}
	
	
	public List<Integer> getSalesOfEmp() {
		List<Integer> salesList = orderDao.getSalesOfEmp();

		return salesList;
	}
	
	
	

}
