package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaveDao extends JpaRepository<Leave, Integer>, JpaSpecificationExecutor<Leave> {

	// 查詢可以請的特休天數
	// select top(1) * from Leave where tenure_in_months <= 40 and leave_category =
	// '特別休假' ORDER BY leave_id DESC
	@Query(value = "select top(1) * from Leave where tenure_in_months <= :tenure and leave_category = '特別休假' ORDER BY leave_id DESC", nativeQuery = true)
	Leave findAnnualLeaveDaysAvailable(@Param("tenure") Integer tenure);
	
	// 查詢特休在何時之前請完（應該用不到，因為年資到了自然會重新取得不同的特休種類，重新拿到一個MaxDay）
	// select top(1) tenure_in_months from Leave where tenure_in_months > 48 and leave_category = '特別休假'
	@Query(value = "select top(1) tenure_in_months from Leave where tenure_in_months > :tenure and leave_category = '特別休假'", nativeQuery = true)
	Integer findWhenAnnualLeaveAvailable(@Param("tenure") Integer tenure);

	// 查詢可以請的特休以外的假別
	// select * from Leave where leave_category <> '特別休假' and tenure_in_months <= 3
	// or tenure_in_months is null
	@Query(value = "select * from Leave where leave_category <> '特別休假' and tenure_in_months <= :tenure or tenure_in_months is null", nativeQuery = true)
	List<Leave> findLeaveAvailable(@Param("tenure") Integer tenure);
}
