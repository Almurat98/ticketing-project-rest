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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController( UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper>getUsers(){
        List<UserDTO>userDTOList= userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("User List successfully retrieved",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable String userName){
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User"+userName+" successfully retrieved",user,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper>createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper>updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper>deleteUserByUsername(@PathVariable String userName){
        userService.deleteByUserName(userName);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("user deleted",HttpStatus.NO_CONTENT));
    }
}