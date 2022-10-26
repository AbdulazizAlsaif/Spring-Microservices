package com.microservices.authsvc.controllers;

import com.microservices.authsvc.config.ApiSuccessPayLoad;
import com.microservices.authsvc.dto.TokenResDTO;
import com.microservices.authsvc.dto.UserReqDTO;
import com.microservices.authsvc.dto.UserResDTO;
import com.microservices.authsvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UsersController {
	
	@Autowired
	private UserService userService;


	//To test the auth. will be removed when app is ready.
	@GetMapping("/test") ResponseEntity<ApiSuccessPayLoad> testEndPoint(Principal principal){

		ApiSuccessPayLoad payLoad = new ApiSuccessPayLoad(String.format("Hello %s From /test",principal.getName()));
		ResponseEntity<ApiSuccessPayLoad> res=new ResponseEntity<>(payLoad,HttpStatus.OK);
		return res;
	}


	@PostMapping("/create")
	public ResponseEntity<ApiSuccessPayLoad> createUser(@RequestBody @Valid UserReqDTO reqUser) {
		UserResDTO user= userService.createUser(reqUser.getUsername(), reqUser.getPassword());
		 ApiSuccessPayLoad payLoad = new ApiSuccessPayLoad("User Successfully created",user);
		 ResponseEntity<ApiSuccessPayLoad> res=new ResponseEntity<>(payLoad,HttpStatus.CREATED);
	     return res;
	  }


	@PostMapping("/login")
	public ResponseEntity<ApiSuccessPayLoad> login(@RequestBody  UserReqDTO reqUser ) {
		TokenResDTO token= userService.login(reqUser);
		ApiSuccessPayLoad payLoad = new ApiSuccessPayLoad(token);
		ResponseEntity<ApiSuccessPayLoad> res=new ResponseEntity<>(payLoad,HttpStatus.OK);
		return  res;
	}
}
