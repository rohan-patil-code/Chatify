package com.Chatify.Service;

import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Chatify.Model.RefreshToken;
import com.Chatify.Model.Users;
import com.Chatify.Repository.RefreshTokenRepo;
import com.Chatify.Repository.UserRepository;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepository userRepository;


   public boolean deleteRefreshToken(String token)
   {
    Optional<RefreshToken> refreshTokenop=refreshTokenRepo.findByToken(token);
    
        if(refreshTokenop.isPresent())
        {
            refreshTokenRepo.delete(refreshTokenop.get());


            return true;
        }
        return false;
   }

   public RefreshToken saveRefToken(String email,String token)
   {
         Users user = userRepository.findUserByEmail(email)

            .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken ref = new RefreshToken();
        ref.setUser(user);
        ref.setToken(token);
        ref.setCreatedAt(Instant.now());
        ref.setExpiresAt(Instant.now().plusSeconds(60*60*24*7));

        return refreshTokenRepo.save(ref);
   }

   public Optional<RefreshToken> findByToken(String token)
   {
    return refreshTokenRepo.findByToken(token);
   }

   public boolean isExpired(RefreshToken token)
   {
    return token.getExpiresAt().isBefore(Instant.now());
   }

   public void deleteIfExpired(RefreshToken token)
   {
    if(isExpired(token))
    {
        refreshTokenRepo.delete(token);
    }
   }

    
}
