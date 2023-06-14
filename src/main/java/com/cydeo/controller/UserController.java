package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper>getUsers(){
        List<UserDTO>userDTOList= userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("User List successfully retrieved",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable String userName){
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User"+userName+" successfully retrieved",user,HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper>createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created",HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper>updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<ResponseWrapper>deleteUserByUsername(@PathVariable String userName){
        userService.deleteByUserName(userName);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("user deleted",HttpStatus.NO_CONTENT));
    }
}
