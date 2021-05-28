package com.berdibekov.repository;


import com.berdibekov.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);

//    @Modifying(clearAutomatically = true)
////    @Transactional
//    @Query(value = "insert into users (admin, first_name, last_name, password, username, user_id) values ('no', '', '', '', '', 125) ",
//    nativeQuery = true)
//    void saveAnonym();
}