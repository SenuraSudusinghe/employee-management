package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Project;
import com.example.employeemanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updated) {
        Project existing = projectRepository.findById(id).orElseThrow();
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return projectRepository.save(existing);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
