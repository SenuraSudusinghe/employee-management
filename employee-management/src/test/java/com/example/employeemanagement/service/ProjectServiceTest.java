package com.example.employeemanagement.service;


import com.example.employeemanagement.entity.Project;
import com.example.employeemanagement.repository.ProjectRepository;
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

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProjects() {
        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Project A");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Project B");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project found = projectService.getProjectById(1L);
        assertEquals("Project A", found.getName());
    }

    @Test
    void testCreateProject() {
        Project project = new Project();
        project.setName("New Project");

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setName("New Project");

        when(projectRepository.save(project)).thenReturn(savedProject);

        Project created = projectService.addProject(project);
        assertNotNull(created.getId());
        assertEquals("New Project", created.getName());
    }

    @Test
    void testUpdateProject() {
        Project existing = new Project();
        existing.setId(1L);
        existing.setName("Old Project");

        Project updates = new Project();
        updates.setName("Updated Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(projectRepository.save(existing)).thenReturn(existing);

        Project updated = projectService.updateProject(1L, updates);

        assertEquals("Updated Project", updated.getName());
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(1L);
        projectService.deleteProject(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }
}

