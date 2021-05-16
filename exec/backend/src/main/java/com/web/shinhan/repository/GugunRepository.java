package com.web.shinhan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.web.shinhan.entity.Gugun;
import com.web.shinhan.entity.Payment;
import com.web.shinhan.entity.User;
import com.web.shinhan.model.UserDto;

@Repository
public interface GugunRepository extends JpaRepository<Gugun, Integer>,
    PagingAndSortingRepository<Gugun, Integer>, QueryByExampleExecutor<Gugun> {

  @Query("select g from guguncode g where SUBSTRING(g.gugunCode,1,2) = :sido")
  List<Gugun> findAllBySidocode(String sido);

}