package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

        @Mock
        private UserRepository userRepository;
        @Mock
        private  ProjectService projectService;
        @Mock
        private  TaskService taskService;
        @Mock
        private KeycloakService keycloakService;
        @Mock
        private PasswordEncoder passwordEncoder;
        @InjectMocks
        private UserServiceImpl userService;

        @Spy
        private UserMapper userMapper = new UserMapper(new ModelMapper());


        User user;
        UserDTO userDTO;

        @BeforeEach
        void  setUp(){
                user = new User();
                user.setId(1L);
                user.setFirstName("John");
                user.setLastName("Doe");
                user.setUserName("user");
                user.setPassWord("Abc1");
                user.setEnabled(true);
                user.setRole(new Role("Manager"));

                userDTO = new UserDTO();
                userDTO.setId(1L);
                userDTO.setFirstName("John");
                userDTO.setLastName("Doe");
                userDTO.setUserName("user");
                userDTO.setPassWord("Abc1");
                userDTO.setEnabled(true);

                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setDescription("Manager");

                userDTO.setRole(roleDTO);
        }

        private List<User> getUsers(){
                User user2 = new User();
                user2.setId(2L);
                user2.setFirstName("Alxat");

                return List.of(user,user2);
        }

        private List<UserDTO> getUserDTOs(){
                UserDTO userDTO2 = new UserDTO();
                userDTO2.setId(2L);
                userDTO2.setFirstName("Alxat");

                return List.of(userDTO,userDTO2);
        }

        @Test
        void should_list_all_users(){
                when(userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUsers());
                List<UserDTO>expectedUserList = getUserDTOs();

                List<UserDTO>actualUserList= userService.listAllUsers();

                assertThat(actualUserList).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedUserList);
        }

        @Test
        void should_find_user_by_username(){
                when(userRepository.findByUserNameAndIsDeleted(anyString(),anyBoolean())).thenReturn(user);
                when(userMapper.convertToDTO(user)).thenReturn(userDTO);

                UserDTO actualUserDto = userService.findByUserName("Oreo");
                UserDTO expectedUserDto = userDTO;

                assertThat(actualUserDto).usingRecursiveComparison().isEqualTo(expectedUserDto);
        }
        @Test
        void should_throw_exception_when_user_not_found(){
                //we can either stub null or do nothing since it will return null also.
                //when(userRepository.findByUserNameAndIsDeleted(anyString(),anyBoolean())).thenReturn(null);

                //we call the method and capture the exception and its message
                Throwable throwable = catchThrowable(()->userService.findByUserName("someUsername"));
                //we use assertInstanceOf method to verify exception type
                assertInstanceOf(NoSuchElementException.class,throwable);
                //we can verify exception message with assertEquals
                assertEquals("User not found",throwable.getMessage());
        }

        @Test
        void should_save_user() {

                when(passwordEncoder.encode(anyString())).thenReturn("anypassword");
                when(userRepository.save(any())).thenReturn(user);
                UserDTO actualDTO = userService.save(userDTO);

                verify(keycloakService).userCreate(any());

                assertThat(actualDTO).usingRecursiveComparison().isEqualTo(userDTO);

        }
        }










