package com.alkemy.ong.repository;

import com.alkemy.ong.Enum.ERole;
import com.alkemy.ong.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(ERole roleName);

}
