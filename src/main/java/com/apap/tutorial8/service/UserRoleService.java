package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
    UserRoleModel addUser(UserRoleModel user);
    void updatePassword(UserRoleModel user, String password);
    boolean comparePass(String password1, String password2);
    boolean passValidator(String password);
    public String encrypt(String password);
}
