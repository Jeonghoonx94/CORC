package com.web.shinhan.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.web.shinhan.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>,
		PagingAndSortingRepository<Payment, Integer>, QueryByExampleExecutor<Payment> {

	@Query("select p from payment p where userId = :userId")
	Page<Payment> findAllByUserId(int userId, Pageable pageable);

	@Query("select p from payment p where storeId = :storeId")
	Page<Payment> findAllByStoreId(int storeId, Pageable pageable);

	@Query("select p from payment p where department = :department")
	Page<Payment> findAllByDepartment(String department, Pageable pageable);

	@Query("select p from payment p where status = 1")
	List<Payment> findAllByStatus();

	@Query("select total from payment where status != 0")
	List<Integer> calcTotalExpense();

	Payment findByPaymentId(int userId);

	@Query("select total from payment where status = 1")
	List<Integer> findTotalByStatus();

	@Query("select total from payment where date between :startDate and :endDate")
	List<Integer> findAllByMonth(LocalDateTime startDate, LocalDateTime endDate);

	@Query("select total from payment where storeId = :storeId and status != 0")
	List<Integer> findTotalByStoreId(int storeId);

	@Query("select total from payment where storeId = :storeId and status == 1")
	List<Integer> findNotConfirmedByStoreId(int storeId);

}
