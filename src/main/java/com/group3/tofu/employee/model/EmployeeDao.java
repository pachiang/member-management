package com.group3.tofu.employee.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeDao extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
	
	// 根據帳號查詢員工訊息
	@Query("From Employee where account=:account")
	Employee findByAccount(@Param("account") String account);
	
	// 查詢所有部門
	// select department from Employee group by department;
	@Query(value = "select department from Employee group by department", nativeQuery = true)
	List<String> findAllDepartment();
	
	// 查詢所有職位
	// select position from Employee group by position;
	@Query(value = "select position from Employee group by position", nativeQuery = true)
	List<String> findAllPosition();
	
	
}
