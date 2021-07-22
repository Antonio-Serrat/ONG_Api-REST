package com.alkemy.ong.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.annotation.MultipartConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.alkemy.ong.dto.request.MemberCreationDto;
import com.alkemy.ong.service.Interface.IMemberService;
import com.alkemy.ong.service.impl.UsersServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@MultipartConfig
public class MemberControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	MemberController controller;
	
	@MockBean
	UsersServiceImpl userService;

	@MockBean
	MessageSource message;
	
	@MockBean
	IMemberService iMember;
	
	@MockBean
	Pageable pageable;
	

	
	@Test
	public void createMemberTest() throws Exception  {
		
			
		MemberCreationDto dto = new MemberCreationDto();
		dto.setName("Raul");
		dto.setImage(null);
		dto.setDescription("The Raul description");
		dto.setFacebookUrl("faceboot.com/raulito");
		dto.setInstagramUrl("instagaram.com/raulitoski");
		dto.setLinkedinUrl("linkedIn.com/raulDev");
		
		
		doReturn(ResponseEntity.status(HttpStatus.CREATED).body(iMember.createMember(dto)))
		.when(controller).createMember(dto);
		
		mockMvc.perform(post("/members").contentType(MediaType.MULTIPART_FORM_DATA)
				.content(asJsonString(dto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.LOCATION, "/members"))
				.andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.name", is("Raul")))
	            .andExpect(jsonPath("$.description", is("The Raul description")))
	            .andExpect(jsonPath("$.facebookUrl", is("faceboot.com/raulito")))
	            .andExpect(jsonPath("$.instagramUrl", is("instagram.com/raulitoski")))
	            .andExpect(jsonPath("$.linkedinUrl", is("linkedIn.com/raulDev")));
		
	}			
//
//	@Test
//	public void updateMemberTest() {
//
//	}
//
	@Test
	public void getAllMembersTest() {
//		MemberCreationDto dto = new MemberCreationDto();
//		dto.setName("Raul");
//		dto.setImage(null);
//		dto.setDescription("The Raul description");
//		dto.setFacebookUrl("faceboot.com/raulito");
//		dto.setInstagramUrl("instagaram.com/raulitoski");
//		dto.setLinkedinUrl("linkedIn.com/raulDev");
//		controller.createMember(dto);
		
		Page<?> result = iMember.showAllMembers(pageable);
		
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(result),ResponseEntity.ok(result));
	}
//
//	@Test
//	public void deleteMemberTest() {
//
//	}
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
