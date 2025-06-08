package com.Chatify.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Chatify.Model.Users;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUserByEmail(String email);

    List<Users> findByUsernameContainingIgnoreCaseAndUserIdNot(String name, Long userid);
}
