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

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> fetchAllUser() {
        return userRepository.findAll();
    }

    public User handleUpdateUser( User user) {
        User userUpdate = this.fetchUserById(user.getId());
        if (userUpdate != null) {
            BeanUtils.copyProperties(user, userUpdate);
            userRepository.save(userUpdate);
        }
        return userUpdate;
    }

}
