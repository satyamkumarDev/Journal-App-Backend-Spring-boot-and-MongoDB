package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getList(){
        return userRepository.findAll();
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
