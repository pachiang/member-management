package com.group3.tofu.order.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.group3.tofu.order.model.bean.Order;

@Repository
@Transactional
public class OrderQueryDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Order> findByOption(String sql, Integer start) {
		
		TypedQuery<Order> query = em.createQuery(sql,Order.class);
		query.setFirstResult(start);
		query.setMaxResults(10);
		return query.getResultList();
	}
	
	public int count(String sql) {
		
		TypedQuery<Order> query = em.createQuery(sql,Order.class);
		
		return query.getResultList().size();
	}
}
