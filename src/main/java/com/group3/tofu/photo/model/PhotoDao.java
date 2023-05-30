package com.group3.tofu.photo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoDao extends JpaRepository<Photo, Integer> {
	
}
