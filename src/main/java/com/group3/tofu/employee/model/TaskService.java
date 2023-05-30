package com.group3.tofu.employee.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group3.tofu.book.model.Book;
import com.group3.tofu.book.model.BookDao;
import com.group3.tofu.customer.model.Customer;
import com.group3.tofu.customer.model.CustomerDao;
import com.group3.tofu.maintenance.Maintenance;
import com.group3.tofu.maintenance.MaintenanceDao;
import com.group3.tofu.photo.model.Photo;
import com.group3.tofu.photo.model.PhotoDao;
import com.group3.tofu.product.model.Product;
import com.group3.tofu.product.model.ProductDao;

@Service
@Transactional(rollbackFor = { Exception.class })
public class TaskService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private MaintenanceDao maintenanceDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private PhotoDao photoDao;

	// 記得不能指派給沒有上班的人
	@Autowired
	private LeaveApplicationDao leaveApplicationDao;

	// SideBar: 查詢待主管分派的任務數量
	public Map<String, Integer> countWaitingAssignTasks() {

		Map<String, Integer> waitingCount = new HashMap<>();
		waitingCount.put("waitingBooks", bookDao.countBookWaiting());
		waitingCount.put("waitingMtns", maintenanceDao.countMtnBookWaiting());
		waitingCount.put("waitingTotal", bookDao.countBookWaiting() + maintenanceDao.countMtnBookWaiting());

		return waitingCount;
	}

	// SideBar: 查詢自己被分派到的任務數量
	public Map<String, Integer> countWaitingTodoTasks(Integer eid) {

		Map<String, Integer> waitingTodoCount = new HashMap<>();
		waitingTodoCount.put("waitingBooks", bookDao.countBookAssigned(eid));
		waitingTodoCount.put("waitingMtns", maintenanceDao.countMtnAssigned(eid));
		waitingTodoCount.put("waitingTotal", bookDao.countBookAssigned(eid) + maintenanceDao.countMtnAssigned(eid));

		return waitingTodoCount;
	}

	// 主管查詢所有預約賞車資料
	public List<Book> findAllBooks() {
		return bookDao.findAll();
	}

	// 主管查詢所有維修申請資料
	public List<Maintenance> findAllMtns() {
		return maintenanceDao.findAll();
	}

	// 員工查詢待完成的維修申請
	public List<Maintenance> findMyMtnTodos(Integer eid) {
		return maintenanceDao.mtnAssigned(eid);
	}

	// 員工查詢待完成的賞車預約
	public List<Book> findMyBookTodos(Integer eid) {
		return bookDao.bookAssigned(eid);
	}

	public List<Customer> findAllCustomers() {
		return customerDao.findAll();
	}

	public List<Product> findAllProducts() {
		return productDao.findAll();
	}

	// 查詢是否有該ID維修申請存在
	public boolean ifMtnExists(Integer mid) {
		return maintenanceDao.findById(mid) != null;
	}

	// 查詢是否有該ID賞車預約存在
	public boolean ifBookExists(Integer bid) {
		return bookDao.findById(bid) != null;
	}

	// 設定指派員工
	public Maintenance setEmployeeToMtnTask(Integer mid, Integer eid) {
		Optional<Maintenance> op = maintenanceDao.findById(mid);
		if (op.isPresent()) {
			Maintenance maintenance = op.get();

			if (eid != null) { // 確保主管真的有給指派員工的ID之後，才能把狀態設定為assigned
				maintenance.setEid(eid);
				maintenance.setStatus("assigned");
				return maintenance;
			}
			return null;

		}
		return null;
	}

	public Book setEmployeeToBookTask(Integer bid, Integer eid) {
		Optional<Book> op = bookDao.findById(bid);
		if (op.isPresent()) {
			Book book = op.get();

			if (eid != null) {
				book.setF_employee_id(eid);
				book.setStatus("assigned");
				return book;
			}
			return null;

		}
		return null;
	}

	// 設定任務已完成
	public Maintenance setClosedToMtnTask(Integer mid) {
		Optional<Maintenance> op = maintenanceDao.findById(mid);
		if (op.isPresent()) {
			Maintenance maintenance = op.get();
			maintenance.setStatus("closed");
			return maintenance;
		}
		return null;
	}

	public Book setClosedToBookTask(Integer bid) {
		Optional<Book> op = bookDao.findById(bid);
		if (op.isPresent()) {
			Book book = op.get();
			book.setStatus("closed");
			return book;
		}
		return null;
	}

	// 修改預約日期
	public Maintenance setDateToMtnTask(Integer mid, Date newDate) {
		Optional<Maintenance> op = maintenanceDao.findById(mid);
		if (op.isPresent()) {
			Maintenance maintenance = op.get();

			if (newDate != null) { // 確保使用者真的有給新日期
				maintenance.setAppointment(newDate);
				return maintenance;
			}
			return null;

		}
		return null;
	}
	
	public Book setDateToBookTask(Integer bid, Date newDate) {
		Optional<Book> op = bookDao.findById(bid);
		if (op.isPresent()) {
			Book book = op.get();

			if (newDate != null) { // 確保使用者真的有給新日期
				book.setBook_date(newDate);
				return book;
			}
			return null;

		}
		return null;
	}

	// 找出顧客預約當天沒有請假的員工資料
	public List<Employee> getAvailableEmp(Date reseverdDate) {
		// System.out.println("有呼叫到我唷");
		LocalDate reseverdLocalDate = convertToLocalDateViaInstant(reseverdDate);
		System.out.println(reseverdLocalDate);
		List<Employee> emplist = employeeDao.findAll();
		List<Employee> availableEmplist = new ArrayList<>();
		// List<LeaveApplication> appList =
		// leaveApplicationDao.findNotAvailableOnRD(reseverdLocalDate);
		List<Integer> notAvailableIdList = leaveApplicationDao.findNotAvailableOnRD(reseverdLocalDate);
		List<Integer> allIdList = new ArrayList<>();
		// System.out.println(appList.toString());
		// System.out.println("appListSize=" + notAvailableIdList.size());
		System.out.println(notAvailableIdList);

		for (Employee emp : emplist) {
			allIdList.add(emp.getEid());
			// System.out.println(allIdList);
		}

		// if (notAvailableIdList != null) { //也就是說當天有人不能來上班

		allIdList.removeAll(notAvailableIdList);

		for (Integer availableId : allIdList) {
			// System.out.println(app.getAid());
			availableEmplist.add(employeeDao.findById(availableId).get());

		}
		// System.out.println("availableEmplist=" + availableEmplist.size());
		return availableEmplist;
		// }
		// System.out.println("查無當天請假員工資料");
		// return emplist;
	}

	public LocalDate convertToLocalDateViaInstant(java.util.Date dateToConvert) {
		// return
		// dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return dateToConvert == null ? null
				: LocalDate.ofInstant(Instant.ofEpochMilli(dateToConvert.getTime()), ZoneId.systemDefault());
	}

	public Maintenance findMtnById(Integer mid) {
		Optional<Maintenance> op = maintenanceDao.findById(mid);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public Book findBookById(Integer bid) {
		Optional<Book> op = bookDao.findById(bid);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}

	public byte[] findPhotoByProductId(Integer pid) {

//		Optional<Product> opProduct = productDao.findById(pid);
//		Product product = new Product();
//		String productModel = null;
//		if (opProduct.isPresent()) {
//			product = opProduct.get();
//			productModel = product.getProductModel();
//		}

		List<Photo> allPhoto = photoDao.findAll();
//		List<Photo> modelPhotoList = new ArrayList<>();

		// return allPhoto.get(10).getPhoto();
		Integer photoId = null;
		for (Photo photo : allPhoto) {
			if (photo.getProduct().getProductId().equals(pid)) {
//				modelPhotoList.add(photo);
				photoId = photo.getPhotoId();
				return allPhoto.get(photoId).getPhoto();
				// return allPhoto.get(photoId).getPhoto();
//				System.out.println("(photo.getProduct().getProductId():" + photo.getProduct().getProductId());
//				System.out.println("(pid:" + pid);
//				return photo.getPhoto();
			}
		}
		return null;
		// return modelPhotoList.get(0).getPhoto();
	}

}
