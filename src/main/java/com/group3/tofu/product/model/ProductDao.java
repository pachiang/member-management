package com.group3.tofu.product.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository <Product, Integer> {

}
