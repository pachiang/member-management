package com.group3.tofu.order.model.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group3.tofu.employee.model.Checks;
import com.group3.tofu.order.model.bean.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	//HQL語法參考的是JAVA的class name(Order)跟property name(f_customer_id),不是SQL SERVER的table(Orders)跟Column name
	@Query(value = "from Order where f_customer_id = ?1")
	List<Order> findByCustomerId(Integer customerId);
	
	@Query(value = "from Order where payment = ?1")
	List<Order> findByPayment(String payment);
	
	@Query(value = "from Order where ship_status = ?1")
	List<Order> findByShipment(String ship_status,Pageable pageable);
	
	@Query(value = "select o from Order o where o.ship_status = ?1")
	Page<Order> findByShipment2(String ship_status,Pageable pageable);
	
	@Query(value = "select count(*) as Sales from Orders group by f_employee_id", nativeQuery = true)
	List<Integer> getSalesOfEmp();
}
