package com.grocery.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
