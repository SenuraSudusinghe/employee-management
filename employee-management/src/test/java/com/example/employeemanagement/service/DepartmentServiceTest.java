package com.example.employeemanagement.service;


import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartments() {
        Department dep1 = new Department();
        dep1.setId(1L);
        dep1.setName("Engineering");

        Department dep2 = new Department();
        dep2.setId(2L);
        dep2.setName("HR");

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(dep1, dep2));

        List<Department> departments = departmentService.getAllDepartments();
        assertEquals(2, departments.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById() {
        Department dep = new Department();
        dep.setId(1L);
        dep.setName("Engineering");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(dep));

        Department found = departmentService.getDepartmentById(1L);
        assertEquals("Engineering", found.getName());
    }

    @Test
    void testCreateDepartment() {
        Department dep = new Department();
        dep.setName("Finance");

        Department savedDep = new Department();
        savedDep.setId(1L);
        savedDep.setName("Finance");

        when(departmentRepository.save(dep)).thenReturn(savedDep);

        Department created = departmentService.addDepartment(dep);
        assertNotNull(created.getId());
        assertEquals("Finance", created.getName());
    }

    @Test
    void testUpdateDepartment() {
        Department existing = new Department();
        existing.setId(1L);
        existing.setName("Engineering");

        Department updates = new Department();
        updates.setName("R&D");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(departmentRepository.save(existing)).thenReturn(existing);

        Department updated = departmentService.updateDepartment(1L, updates);

        assertEquals("R&D", updated.getName());
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentRepository).deleteById(1L);
        departmentService.deleteDepartment(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}
