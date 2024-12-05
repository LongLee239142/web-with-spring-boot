package vn.hoidanit.jobhunter.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  User handleCreateUser(User user) {
       return userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> handleGetUserById(long id) {
        return Optional.of(userRepository.findById(id).get());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void handleUpdateUser( User user) {
        User userUpdate = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + user.getId()));
        BeanUtils.copyProperties(user, userUpdate);

        userRepository.save(userUpdate);
    }

}
