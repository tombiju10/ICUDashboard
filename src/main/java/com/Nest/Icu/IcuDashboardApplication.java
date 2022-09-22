package com.Nest.Icu;

import com.Nest.Icu.mapper.PatientMapper;
import com.Nest.Icu.mapper.PatientMapperImpl;
import com.Nest.Icu.model.User;
import com.Nest.Icu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class IcuDashboardApplication implements CommandLineRunner {
	
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder pe;

	@PostConstruct
	public void initUsers() {	
		List<User> listOfUsers= Stream.of(new User(101,"user",pe.encode("abcd")),
				new User(102,"user1",pe.encode("Welcome@123")),
				new User(103,"user2",pe.encode("Welcome@123")),
				new User(104,"user3",pe.encode("Welcome@123"))).collect(Collectors.toList());
		repo.saveAll(listOfUsers);
		
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(IcuDashboardApplication.class, args);
	}

	@Bean
	public PatientMapper getPatientMapper(){
		return new PatientMapperImpl();
	}

	@Override
	public void run(String... args) throws Exception {
	
	}
	

	
}
