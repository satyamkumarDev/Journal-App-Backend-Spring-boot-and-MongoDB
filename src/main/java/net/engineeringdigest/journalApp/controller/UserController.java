package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getAllUser(){
        return userService.getList();
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.saveEntry(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId userId){
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
