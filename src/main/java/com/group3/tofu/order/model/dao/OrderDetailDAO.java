package com.group3.tofu.order.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.tofu.order.model.bean.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

}
