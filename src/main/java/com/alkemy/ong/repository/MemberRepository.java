package com.alkemy.ong.repository;

import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <Member, Long>{

    Optional<Member> findById(Long id);

    List<MemberResponseDto> findAllProjectedBy();

}
