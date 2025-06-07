package com.Chatify.Security;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtill jwtUtill;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtill jwtUtill, UserDetailsService userDetailsService) {
        this.jwtUtill = jwtUtill;
        this.userDetailsService = userDetailsService;

    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
          
        final String authHeader = request.getHeader("Authorization");

        String email=null;
        String token=null;

        if(authHeader!=null && authHeader.startsWith("Bearer")) {

            token=authHeader.substring(7);
            
            if(jwtUtill.validateAccessToken(token))
            {
                email=jwtUtill.getUsernameFromToken(token);
            }
        }

        if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

          if(jwtUtill.validateAccessToken(token))
          {
            UsernamePasswordAuthenticationToken authToken=
                     new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                     SecurityContextHolder.getContext().setAuthentication(authToken);
          }      
        }
            filterChain.doFilter(request, response);
    }

}
