package com.alkemy.ong.util;

import com.alkemy.ong.model.User;
import com.alkemy.ong.service.impl.UsersServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RoleValidator {

    private final UsersServiceImpl usersService;


    public static String getToken(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getHeader("Authorization");
    }

    public boolean isAuthorized(String token) {
        if(isAdmin())
            return true;
        Long idFromToken = Long.valueOf(getIdFromToken(token));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails) principal).getUsername();
        User currentUser = (User) usersService.loadUserByUsername(currentUsername);
        return currentUser.getId().equals(idFromToken);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private boolean isAdmin(){
        return true;
    }


    public String getIdFromToken(String token) {
        return Jwts.parser().setSigningKey("${jwt.secret}").parseClaimsJws(token).getBody().getId();
    }


}
