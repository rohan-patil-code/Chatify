package com.Chatify.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Chatify.DTO.SearchUserDTO;
import com.Chatify.DTO.UserSummaryDTO;
import com.Chatify.Model.ChatParticipant;
import com.Chatify.Model.Users;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUserByEmail(String email);

    List<Users> findByUsernameContainingIgnoreCaseAndUserIdNot(String name, Long userid);

//    @Query("SELECT new com.Chatify.DTO.UserSummaryDTO(u.id, u.username) " +
//        "FROM Users u WHERE u.username LIKE %:query% AND u.id <> :currentUserId")
// List<UserSummaryDTO> searchUserSummaries(@Param("query") String query,
//                                          @Param("currentUserId") Long currentUserId);
// }
    
    List<Users> findByUsernameContainingIgnoreCase(String username);

    Optional<Users> findByUsername(String username);

}

