package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO> taskDTOList= taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Task List successfully retrieved",taskDTOList, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getUserById(@PathVariable Long id){
        TaskDTO taskDTO= taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Task successfully retrieved",taskDTO,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task created",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task updated",HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper>deleteUserById(@PathVariable Long id){
        taskService.delete(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("Task deleted",HttpStatus.NO_CONTENT));
    }

    @GetMapping("/employee/pending-task")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper>employeePendingTask(){
        List<TaskDTO> pendingTasks= taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Task List successfully retrieved",pendingTasks, HttpStatus.OK));
    }
    @PutMapping("/employee/update")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeUpdateTask(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task updated",HttpStatus.CREATED));
    }
    @GetMapping("/employee/archive")
    @RolesAllowed({"Employee"})
    public ResponseEntity<ResponseWrapper> employeeArchivedTask(){
        List<TaskDTO>archivedTasks= taskService.listAllTasksByStatus(Status.COMPLETE);

        return ResponseEntity.ok(new ResponseWrapper("Archived Task List successfully retrieved",archivedTasks, HttpStatus.OK));
    }

}
