package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;
import mx.edu.j2se.hernandezv.CarRentalSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
