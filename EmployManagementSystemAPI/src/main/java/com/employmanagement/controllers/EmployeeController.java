package com.employmanagement.controllers;

import java.util.ArrayList;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.employmanagement.dto.EmployeeDTO;
import com.employmanagement.exception.ResourceNotFoundException;
import com.employmanagement.model.Department;
import com.employmanagement.model.Employee;
import com.employmanagement.repository.DepartmentRepository;
import com.employmanagement.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	// Get All Employee
	@GetMapping("/employee")
	public List<Employee> getAllEmployees() throws Exception {
		List<EmployeeDTO> main=new ArrayList<EmployeeDTO>();
		try {
			for (Employee employee : employeeRepository.findAll()) {
				EmployeeDTO row = new EmployeeDTO();
				row.setEmployeeId(employee.getEmployeeId());
				row.setEmployeeFirstName(employee.getEmployeeLastName());
				row.setEmployeeLastName(employee.getEmployeeLastName());
				row.setDepartmentId(employee.getDepartment().getDepartmentId());
				row.setDepartmentName(employee.getDepartment().getDepartmentName());
				main.add(row);
	        }
		 } catch (Exception e) {
				//e.printStackTrace();
				throw new Exception("Please select a record");
		 }
	    return employeeRepository.findAll();
	}
	
	// Create a new Employee
	@PostMapping("/employee/{departmentId}")
	public Employee createEmployee(@Valid @PathVariable Long departmentId, @RequestBody Employee employee,UriComponentsBuilder builder) throws Exception{
		Department department=null;
		try {
			department=	departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department", "departmentId", departmentId));
			if(department==null) {
				throw new Exception("invalide department Id");
			}
			employee.setDepartment(department);
	    
		} catch (Exception e) {
			throw new Exception("Invalide Department Id");
		}
		return employeeRepository.save(employee);
	}
	
	// Get a Single Employee
	@GetMapping("/employee/{employeeId}")
	public Employee getEmployeeById(@PathVariable(value = "employeeId") Long empId) throws Exception{
		Employee employee=null;
		try {
			employee=employeeRepository.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", empId));
		}catch (Exception e) {
			throw new Exception("Invalide Employee Id");
		}
		return employee;
	}
	
	// Update a Employee
	@PutMapping("/employee/{employeeId}/{departmentId}")
	public Employee updateEmployee(@PathVariable Long employeeId,@PathVariable Long departmentId, @Valid @RequestBody Employee employeeDetail)  throws Exception{
		Employee employee =null;
		try {
			employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", employeeId));
		}catch (Exception e) {
			throw new Exception("Invalide Employee Id");
		}
		employeeDetail.setEmployeeId(employee.getEmployeeId());
		try {
			employeeDetail.setDepartment(departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("Department", "departmentId", departmentId)));
		}catch (Exception e) {
			throw new Exception("Invalide Department Id");
		}
		Employee updatedEmployee = employeeRepository.save(employeeDetail);
	    return updatedEmployee;
	}
	
	// Delete a Employee
	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId)	throws Exception{
		try {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("employeeRepository", "employeeId", employeeId));
		employeeRepository.delete(employee);
		}catch (Exception e) {
			throw new Exception("Invalide Employee Id");
		}
	    return ResponseEntity.ok().build();
	}





}
