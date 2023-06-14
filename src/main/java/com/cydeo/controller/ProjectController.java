package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDTO> projectDTOList= projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Project List successfully retrieved",projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProjectByName(@PathVariable String projectCode){
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("project successfully retrieved",projectDTO,HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper>createProject(@RequestBody ProjectDTO projectDTO){
     projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project created",HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper>updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper>deleteUserByUsername(@PathVariable String projectCode){
        projectService.delete(projectCode);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("Project deleted",HttpStatus.NO_CONTENT));
    }
}
