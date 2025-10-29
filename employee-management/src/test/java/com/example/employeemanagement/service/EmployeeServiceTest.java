package com.example.employeemanagement.service;


import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("John");
        emp1.setEmail("john@example.com");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Jane");
        emp2.setEmail("jane@example.com");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("John");
        emp.setEmail("john@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

        Employee found = employeeService.getEmployeeById(1L);
        assertEquals("John", found.getName());
    }

    @Test
    void testCreateEmployee() {
        Employee emp = new Employee();
        emp.setName("Alice");
        emp.setEmail("alice@example.com");

        Employee savedEmp = new Employee();
        savedEmp.setId(1L);
        savedEmp.setName("Alice");
        savedEmp.setEmail("alice@example.com");

        when(employeeRepository.save(emp)).thenReturn(savedEmp);

        Employee created = employeeService.addEmployee(emp);
        assertNotNull(created.getId());
        assertEquals("Alice", created.getName());
    }

    @Test
    void testUpdateEmployee() {
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setName("John");
        existing.setEmail("john@example.com");

        Employee updates = new Employee();
        updates.setName("John Updated");
        updates.setEmail("johnupdated@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(existing);

        Employee updated = employeeService.updateEmployee(1L, updates);

        assertEquals("John Updated", updated.getName());
        assertEquals("johnupdated@example.com", updated.getEmail());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);
        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
