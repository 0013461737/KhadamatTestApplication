package MainPackage.mv.service;

import java.util.ArrayList;
import java.util.Optional;

import MainPackage.dbmanager.assets.UserAsset;
import MainPackage.dbmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserAsset userAsset;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MainPackage.dbmanager.model.User user;
        try {
            user = userRepository.findByUsername(username).get();
        } catch (Exception e) {
            user = null;
        }

        if (user != null) {

            return new User(user.getUsername(), user.getPassword(),
                    new ArrayList<>());

        } else {
            log.warn("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


    }
}