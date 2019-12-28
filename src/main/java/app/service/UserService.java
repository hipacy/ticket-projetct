package app.service;

import app.model.UserType;
import app.model.Users;
import app.model.mapper.UserMapper;
import app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("userService")
public class UserService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users saveNewUser(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsActive(1);

        return usersRepository.save(user);
    }

    public Users updateUser(Users userData, Users currentUser) {
        UserMapper.mapEditedUser(userData, currentUser);
        return usersRepository.save(currentUser);
    }

    public Users updateUser(Users userData, Integer currentUserId) {
        Users currentUser = usersRepository.findUsersByUserId(currentUserId);
        UserMapper.mapEditedUser(userData, currentUser);
        return usersRepository.save(currentUser);
    }

    public List<Users> findUserByRoles(UserType role) {
        return usersRepository.findUserByRoles(role);

    }


    public Set<Users> findAll() {
        return usersRepository.findAllUsers();

    }

    public Users findUsersByUserId(Integer id) {
        return usersRepository.findUsersByUserId(id);
    }


}