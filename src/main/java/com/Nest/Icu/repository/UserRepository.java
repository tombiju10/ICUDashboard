package com.Nest.Icu.repository;

import com.Nest.Icu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

	User findByUserName(String userName);
}
