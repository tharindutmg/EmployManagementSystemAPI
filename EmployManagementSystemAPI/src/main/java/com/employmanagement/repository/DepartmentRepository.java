package com.employmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employmanagement.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
