package net.engineeringdigest.journalApp.service;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getList(){
        return userRepository.findAll();
    }

    public User saveNewUserEntry(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            log.error("Error " +e);
            throw new RuntimeException(e);
        }
    }

    public User saveEntry(User user){
        userRepository.save(user);
        return user;
    }


    public void deleteById(ObjectId userId){
        userRepository.deleteById(userId);
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }
}
