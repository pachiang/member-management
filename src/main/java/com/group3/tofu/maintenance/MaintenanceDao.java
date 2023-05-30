package com.group3.tofu.maintenance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaintenanceDao extends JpaRepository<Maintenance, Integer> {

	// 查詢待主管分派的維修申請任務數量
	// select count(*) from Maintenance where status = 'waiting'
	@Query(value = "select count(*) from Maintenance where status='waiting'", nativeQuery = true)
	Integer countMtnBookWaiting();

	// 查詢員工被指派的維修申請任務數量
	// select count(*) from Maintenance where f_employee_id = 19 and
	// status='assigned'
	@Query(value = "select count(*) from Maintenance where f_employee_id=:eid and status='assigned'", nativeQuery = true)
	Integer countMtnAssigned(@Param("eid") Integer eid);

	// 查詢員工被指派的維修申請任務
	@Query(value = "select * from Maintenance where f_employee_id=:eid and status='assigned'", nativeQuery = true)
	List<Maintenance> mtnAssigned(@Param("eid") Integer eid);

}
