package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
//    @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(@RequestBody User postManUser) {
      User newUser =  userService.handleCreateUser(postManUser);
        return newUser;
    }

    @DeleteMapping("/user/{id}")
    public String deleteuser(@PathVariable("id") long id) {
        userService.handleDeleteUser(id);
        return "delete";
    }

    @GetMapping("/user/{user-id}")
    public User getUserById(@PathVariable("user-id") long id) {
        Optional<User> userOptional = userService.handleGetUserById(id);
        return userOptional.orElse(null);
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }
    @PutMapping("/user")
    public Optional<User> updateUser(@RequestBody User postManUser) {
         userService.handleUpdateUser(postManUser);
        return userService.handleGetUserById(postManUser.getId());
    }
}
