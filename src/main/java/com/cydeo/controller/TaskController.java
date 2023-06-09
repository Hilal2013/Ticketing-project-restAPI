package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "TaskController",description = "Task API")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Get tasks")
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",
                taskService.listAllTasks(), HttpStatus.OK));
    }
    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get task by id")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",
                taskService.findById(taskId), HttpStatus.OK));
    }
    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Create task")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created",HttpStatus.CREATED));
    }
    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete task")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));
    }
    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update task")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")// give me all task assigned to me
    @RolesAllowed("Employee")
    @Operation(summary = "Get uncompleted tasks by assigned to manager")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){//employee is working on these tasks
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOList,HttpStatus.OK));

    }
    @PutMapping("/employee/update/")
    @RolesAllowed("Employee")
    @Operation(summary = "Update task by employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));


    }
    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    @Operation(summary = "Get completed task")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){//completed tasks
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOList,HttpStatus.OK));

    }
    }
