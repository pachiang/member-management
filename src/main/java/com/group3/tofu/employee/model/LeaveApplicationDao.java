package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface LeaveApplicationDao
		extends JpaRepository<LeaveApplication, Integer>, JpaSpecificationExecutor<LeaveApplication> {

	// 找出顧客預約維修當天請假的員工
	// select * from LeaveApplication where manager_approved = 1 and begin_date <=
	// '2021-05-01' and end_date >= '2021-05-01'
	@Query(value = "select f_employee_id from LeaveApplication where manager_approved = 1 and begin_date <=:reservedDate and end_date >=:reservedDate", nativeQuery = true)
	List<Integer> findNotAvailableOnRD(@Param("reservedDate") LocalDate reseverdDate);
	// 找出顧客預約維修當天沒有請假的員工（不是這樣使用的）
	// select * from LeaveApplication where (manager_approved is null or
	// manager_approved <> 1) or ('2021-05-01' NOT BETWEEN begin_date AND end_date)
	// @Query(value = "select * from LeaveApplication where (manager_approved is
	// null or manager_approved <> 1) or (:reservedDate NOT BETWEEN begin_date AND
	// end_date) order by f_employee_id", nativeQuery = true)

	// 找出這個員工之前請了多少天特休
	// select sum(leave_days) from LeaveApplication where f_employee_id = 18 and
	// f_leave_id = 27
	// @Query(value = "select sum(leave_days) as totalUsedDays from LeaveApplication
	// where f_employee_id = :eid and f_leave_id = :lid", nativeQuery = true)
	// select * from LeaveApplication where f_employee_id = 18 and f_leave_id = 27
	// @Query(value = "select leave_days from LeaveApplication where
	// f_employee_id=:empid and f_leave_id=:leaveid", nativeQuery = true)

	// @Query(value = "select leave_days from LeaveApplication where [f_employee_id]
	// = :empid and f_leave_id=:leaveid", nativeQuery = true)
	// List<Integer> findUsedAnnualLeave(@Param("empid") Integer empid,
	// @Param("leaveid") Integer leaveid);
	@Query(value = "select sum(leave_days) as totalUsedDays from LeaveApplication where f_employee_id = :empid and f_leave_id = :leaveid", nativeQuery = true)
	Integer findUsedAnnualLeave(@Param("empid") Integer empid, @Param("leaveid") Integer leaveid);

	// 判斷此員工申請的假期期間是否已經請過假了
	// select count(*) from LeaveApplication where begin_date <= '2023-05-22' and
	// end_date >= '2023-05-22' and f_employee_id = 19
	@Query(value = "select count(*) from LeaveApplication where begin_date <= :applyBD and end_date >= :applyBD and f_employee_id = :empid", nativeQuery = true)
	Integer checkLeaveDateAlreadyTaken(@Param("applyBD") LocalDate applyBD, @Param("empid") Integer empid);

	// 找出待審核的假單
	// select * from LeaveApplication where manager_approved is null
	@Query(value = "select * from LeaveApplication where manager_approved is null", nativeQuery = true)
	List<LeaveApplication> findWaitingApprovalList();

	// 找出已經審核過的假單
	// select * from LeaveApplication where manager_approved is not null
	@Query(value = "select * from LeaveApplication where manager_approved is not null", nativeQuery = true)
	List<LeaveApplication> findApprovedOrRejectedList();

	// 員工找出自己送出過的假單
	// select * from LeaveApplication where f_employee_id = 19
	@Query(value = "select * from LeaveApplication where f_employee_id = :eid", nativeQuery = true)
	List<LeaveApplication> findMyApplicationList(@Param("eid") Integer eid);

}
