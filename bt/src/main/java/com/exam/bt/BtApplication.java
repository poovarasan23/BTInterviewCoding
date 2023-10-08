package com.exam.bt;

import com.exam.bt.entity.User;
import com.exam.bt.repository.UserRepository;
import com.exam.bt.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@PostConstruct
	public void initAdminUser() {
		String adminUser = "administrator";
		if (userRepository.findByUserId(adminUser) == null) {
			User user = new User();
			user.setUserName(adminUser);
			user.setPassword(adminUser);
			user.setRole(Role.ADMINISTRATOR);
			userRepository.save(user);
		}
	}

}
