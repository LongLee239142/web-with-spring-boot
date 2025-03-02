package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        String hashedPassword = passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashedPassword);
        User ericUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ericUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if (id <= 0) {
            throw new IdInvalidException("ID không hợp lệ. ID phải là một số nguyên dương.");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // fetch user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User fetchUser = this.userService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    // fetch all users
    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sCurrent = currentOptional.orElse("");
        String sPageSize = pageSizeOptional.orElse("");

        int current = Integer.parseInt(sCurrent);
        int pageSize = Integer.parseInt(sPageSize);

        Pageable pageable = PageRequest.of(current - 1, pageSize);

        // return ResponseEntity.ok(this.userService.fetchAllUser());
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(pageable));
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User ericUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(ericUser);
    }

}
