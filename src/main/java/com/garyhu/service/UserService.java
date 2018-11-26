package com.garyhu.service;

import com.garyhu.entity.User;
import com.garyhu.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: garyhu
 * @since: 2018/11/23 0023
 * @decription:
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User login(Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        User user = userRepository.findUserByName(username);
        if(user == null){
            return null;
        }else {
            String pass = user.getPassword();
            if(password != null && !"".equals(password)){
                if(password.equals(pass)){
                    return user;
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }
    }

    public User addUser(User user){
        User u = userRepository.save(user);

        return u;
    }

    public List<User> getUsersByRole(String role){
        final User user = new User();
        user.setRole(role);
        Example<User> ex = Example.of(user);
        List<User> users = userRepository.findAll(ex);

        return users;
    }

    public User getUserByName(String name){
        User user = userRepository.findUserByName(name);
        return user;
    }
}
