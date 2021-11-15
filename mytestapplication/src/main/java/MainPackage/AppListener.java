package MainPackage;

import MainPackage.dbmanager.model.User;
import MainPackage.dbmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepository.deleteAll();

        User user1 = new User();
        user1.setUsername("Hamed_User");
        //===== password: HamedPassWord
        user1.setPassword("$2a$12$3cx3DFWPCyy2Dm1ZgF7/fugVmCk6zDcZpcitPm33cHKnQR3TdrbQm");

        User user2 = new User();
        user2.setUsername("khadamat_User");
        //===== password: KhadamatPassWord
        user2.setPassword("$2a$12$M8uLp5Autpekyz7cqo7oyO4/1wvAass2ZQImHTuBFiUhvPwisoyau");

        User user3 = new User();
        user3.setUsername("1");
        //===== password: 1
        user3.setPassword("$2a$12$41lu0ilq7DIXD8Eqib7xKO99gJIiNNDeHtS7jK32irc.02kjWXaba");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }
}
