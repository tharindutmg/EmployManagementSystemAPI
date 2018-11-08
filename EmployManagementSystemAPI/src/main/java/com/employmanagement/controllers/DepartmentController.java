package com.employmanagement.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmanagement.exception.ResourceNotFoundException;
import com.employmanagement.model.Department;
import com.employmanagement.repository.DepartmentRepository;
import com.employmanagement.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class DepartmentController {
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	// Get All Department
	@GetMapping("/department")
	public List<Department> getAllDepartment() throws Exception {
		try {
	    return departmentRepository.findAll();
		 } catch (Exception e) {
				//e.printStackTrace();
				throw new Exception("Please select a record");
		 }
		
	}
		
	// Create a new Department
	@PostMapping("/department")
	public Department createDepartment(@Valid @RequestBody Department department) throws Exception {
		try {
	    return departmentRepository.save(department);
		 } catch (Exception e) {
				throw new Exception("Please select a record");
		 }
	}
	
	// Get a Single Department
	@GetMapping("/department/{id}")
	public Department getDepartmentById(@PathVariable(value = "id") Long departmentId) throws Exception {
		try {
	    return departmentRepository.findById(departmentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
		 } catch (Exception e) {
				throw new Exception("Please select a record");
		 }
	}
	
	// Update a Department
	@PutMapping("/department/{id}")
	public Department updateDepartment(@PathVariable(value = "id") Long departmentId,
	                                        @Valid @RequestBody Department departmentDetails) throws Exception {
		try {
		Department department = departmentRepository.findById(departmentId)
	            .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

		departmentDetails.setDepartmentId(department.getDepartmentId());

		Department updatedDepartment = departmentRepository.save(departmentDetails);
	    return updatedDepartment;
	 } catch (Exception e) {
			throw new Exception("Please select a record");
	 }
	}
	
	// Delete a Department
	@DeleteMapping("/department/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long departmentId) throws Exception {
		try {
			Department department = departmentRepository.findById(departmentId)
		            .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
	
			departmentRepository.delete(department);
	
		    return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new Exception("Please select a record");
	 }
	}




}
