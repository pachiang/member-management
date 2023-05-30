package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckDao extends JpaRepository<Checks, Integer>, JpaSpecificationExecutor<Checks> {
	
	// 查詢該日是否已有打卡紀錄
	@Query(value = "select * from Checks where f_employee_id=:eid and CAST(checkin_time as DATE)=:checkintime", nativeQuery = true)
	Checks findTodaysCheck(@Param("eid") Integer eid, @Param("checkintime") LocalDate checkintime);
	
	// 修改下班打卡時間
	// update Checks set checkout_time = '2023-05-11 16:01:02' where f_employee_id=5 and CAST(checkin_time as DATE)='2023-05-11'
//	@Modifying
//	@Query(value = "update Checks set checkout_time=:checkouttime where f_employee_id=:eid and CAST(checkin_time as DATE)=:checkintime", nativeQuery = true)
//	void updateCheckOutTime(@Param("checkouttime") Date checkouttime, @Param("eid") Integer eid, @Param("checkintime") LocalDate checkintime);
	
	
}
