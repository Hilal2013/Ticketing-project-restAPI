package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects() {
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",
                projectService.listAllProjects(), HttpStatus.OK));


    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",
                projectService.getByProjectCode(code), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project) {//Im capturing that project from the API
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is  successfully created", HttpStatus.CREATED));

    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project) {
        projectService.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project is  successfully updated", HttpStatus.OK));

    }
@DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode) {
    projectService.delete(projectCode);
    return ResponseEntity.ok(new ResponseWrapper("Project is  successfully deleted", HttpStatus.OK));
    }
@GetMapping("/manager/project-status") //is gonna show the project assigned to manager
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
    return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",
            projectService.listAllProjectDetails(), HttpStatus.OK));
    }
    @PutMapping("/manager/complete/{projectCode}")//you are trying change field from open to complete
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully completed", HttpStatus.OK));
    }
}