package com.microservices.authsvc.services;


import com.microservices.authsvc.dto.TokenResDTO;
import com.microservices.authsvc.dto.UserReqDTO;
import com.microservices.authsvc.dto.UserResDTO;
import com.microservices.authsvc.models.User;
import com.microservices.authsvc.repository.UserRepository;
import com.microservices.authsvc.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;


@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	public UserResDTO createUser(String name, String password) {
		
	User user=userRepository.findByName(name);
	User newUser= new User(name,passwordEncoder.encode(password));
	if(user!=null)
		throw new ResponseStatusException(HttpStatus.CONFLICT, "User already registered in the system");
	var savedUser= UserResDTO.UserToUserResDTO(userRepository.save(newUser));
	return savedUser;
	}
	
	


	public TokenResDTO login(UserReqDTO user) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		UserDetails userDetails = loadUserByUsername(user.getUsername());
		String token=jwtTokenUtil.generateToken(userDetails);
		Date tokenExpiryDate = jwtTokenUtil.getExpirationDateFromToken(token);
		String[] grantedAuthorities= {};


		return new TokenResDTO(user.getUsername(),jwtTokenUtil.generateToken(userDetails),tokenExpiryDate,grantedAuthorities);
	}


	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails userDetails= new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
		return userDetails;
	}
}
	
	


