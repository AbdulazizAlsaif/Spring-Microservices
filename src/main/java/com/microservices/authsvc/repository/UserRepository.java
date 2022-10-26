package com.microservices.authsvc.repository;

import org.springframework.stereotype.Repository;

import com.microservices.authsvc.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	User findByName(String name);
}
