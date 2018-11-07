package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDb userDb;

    @Override
    public UserRoleModel addUser(UserRoleModel user){
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public void updatePassword(UserRoleModel user, String password){
        user.setPassword(encrypt(password));
        userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public boolean comparePass(String oldPassword, String oldPasswordEncrypt){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean same = passwordEncoder.matches(oldPassword, oldPasswordEncrypt);
        return same;
    }

    @Override
    public boolean passValidator(String password){
        boolean passwordContainsDigit = false;
        boolean passwordContainsLetter = false;

        if(password.length()<8){
            return false;
        }

        else {
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    passwordContainsDigit = true;
                }
            }

            if (!passwordContainsDigit){
                return false;
            }

            passwordContainsLetter = password.matches(".*[a-zA-Z]+.*");

            return passwordContainsLetter;
        }

    }
}
