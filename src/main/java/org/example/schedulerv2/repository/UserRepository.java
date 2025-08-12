package org.example.schedulerv2.repository;

import org.example.schedulerv2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
