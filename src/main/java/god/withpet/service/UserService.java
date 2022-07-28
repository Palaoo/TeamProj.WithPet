package god.withpet.service;
import god.withpet.entity.User;
import god.withpet.repository.UserRepository;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> join(User user){
        userRepository.save(user);
//        return Optional.ofNullable(user);
        return Optional.ofNullable(user);
    };

    public boolean checkUser(String userid, String password){
        Optional<User> user = userRepository.findById(userid);
        try{
            if(user.get().getPassword().equals(password)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

}
