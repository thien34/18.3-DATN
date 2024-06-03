package com.example.back_end.repository;

import com.example.back_end.entity.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long> {
}