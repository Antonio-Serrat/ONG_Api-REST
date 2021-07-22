	package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.request.MemberCreationDto;
import com.alkemy.ong.dto.response.MemberResponseDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Locale;


@Service
public class MemberServiceImpl implements IMemberService {

    private final MemberRepository memberRepository;
    private final ProjectionFactory projectionFactory;
    private final IFileStore fileStore;
    private final MessageSource messageSource;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, ProjectionFactory projectionFactory, IFileStore fileStore, MessageSource messageSource) {
        this.memberRepository = memberRepository;
        this.projectionFactory = projectionFactory;
        this.fileStore = fileStore;
        this.messageSource = messageSource;
    }

    @Override
    public MemberResponseDto createMember(MemberCreationDto memberCreationDto) {

        Member member = Member.builder()
                .description(memberCreationDto.getDescription())
                .facebookUrl(memberCreationDto.getFacebookUrl())
                .instagramUrl(memberCreationDto.getInstagramUrl())
                .linkedinUrl(memberCreationDto.getLinkedinUrl())
                .name(memberCreationDto.getName())
                .build();

        Member memberCreated = memberRepository.save(member);
        
        if(!memberCreationDto.getImage().isEmpty())
        	memberCreated.setImage(fileStore.save(memberCreated, memberCreationDto.getImage()));
        
        return projectionFactory.createProjection(MemberResponseDto.class, memberRepository.save(memberCreated));
    }

    public Page<Member> showAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }


    @Override
    public MemberResponseDto updateMemberById(Long id, MemberCreationDto dto) {

        Member member = getMemberById(id);

        if(!dto.getName().isBlank())
		    member.setName(dto.getName());

        if(!dto.getImage().isEmpty())
		    member.setImage(fileStore.save(member, dto.getImage()));

        if(!dto.getDescription().isBlank())
		    member.setDescription(dto.getDescription());

        if(!dto.getFacebookUrl().isBlank())
		    member.setFacebookUrl(dto.getFacebookUrl());

        if(!dto.getInstagramUrl().isBlank())
		    member.setInstagramUrl(dto.getInstagramUrl());

        if(!dto.getLinkedinUrl().isBlank())
		    member.setLinkedinUrl(dto.getLinkedinUrl());

		member.setEdited(new Date());
		return projectionFactory.createProjection(MemberResponseDto.class, memberRepository.save(member));
    }

    @Override
    public String deleteMember(Long id) {
        Member member = getMemberById(id);
        fileStore.deleteFilesFromS3Bucket(member);
        return messageSource.getMessage("member.delete.successful", null, Locale.getDefault());
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("member.error.not.found", null, Locale.getDefault())
                )
        );
    }
}
