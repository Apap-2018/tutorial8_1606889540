package com.apap.tutorial8.repository;

import com.apap.tutorial8.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDb extends JpaRepository<UserRoleModel, Long> {
    UserRoleModel findByUsername(String username);
}
