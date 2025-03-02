package vn.hoidanit.jobhunter.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  User handleCreateUser(User user) {
        return userRepository.save(user);
    }
    public User handleGetUserByName(String name) {
       return this.userRepository.findByEmail(name);
    }

    public void handleDeleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public ResultPaginationDTO fetchAllUser(Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());

        mt.setPage(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());

        return rs;
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
