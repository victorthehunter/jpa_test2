package com.zakaria.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zakaria.jpa.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}