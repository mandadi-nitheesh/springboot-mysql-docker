package com.nitheesh.certificate.Jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.nitheesh.certificate.CetificateManagementApplication;
import com.nitheesh.certificate.repository.CustomUserRepo;

@SpringBootTest(classes = CetificateManagementApplication.class)
@ContextConfiguration
class CustomUserServiceTest {

	@MockBean
	private CustomUserRepo userRepo;
	
	
	@Autowired
	private CustomUserService service;

	@Test
	void testSaveCustomUser() {
		
		CustomUser user=new CustomUser();
		user.setEmail("vazeem@123gmail.com");
		user.setId(12l);
		user.setPassword("vazeem");
		
		CustomUser user2=new CustomUser();
		user2.setEmail("nitheeshreddy290800@gmail.com");
		user2.setId(12l);
		user2.setPassword("nitheesh");
		
		when(userRepo.save(user)).thenReturn(user2);
		
	CustomUser createUser = service.createUser(user);
		assertEquals(user2, createUser);
		
	}
}
