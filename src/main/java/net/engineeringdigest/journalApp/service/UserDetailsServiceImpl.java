package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    // Constructor injection (preferred)
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);

            if (user != null) {
                String[] roles = user.getRoles().toArray(new String[0]);
                if (roles.length == 0) {
                    roles = new String[]{"USER"}; // default role
                }

                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(roles)
                        .build();
            } else {
                throw new UsernameNotFoundException("User Not Found with username: " + username);
            }
        } catch (Exception e) {
            // Log and rethrow as UsernameNotFoundException
            System.out.println("Error loading user: " + e.getMessage());
            throw new UsernameNotFoundException("Error loading user: " + username, e);
        }
    }
}