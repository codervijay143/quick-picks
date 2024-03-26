package com.ecommerce.portal.apis;

import java.util.List;

import com.ecommerce.portal.dtos.UserDTO;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/admin")
public class AdminUserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException {

		System.out.println("/api/users/profile");
		List<UserDTO> allUsers=userService.findAllUsers();
		return new ResponseEntity<>(allUsers,HttpStatus.ACCEPTED);
	}


}
