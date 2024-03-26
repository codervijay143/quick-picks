package com.ecommerce.portal.apis;

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
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		System.out.println("/api/users/profile");
		User user=userService.findUserProfileByJwt(jwt);
		UserDTO userDTO=this.entityToDTO(user);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.ACCEPTED);
	}

	private UserDTO entityToDTO(User user){

		UserDTO userDTO=new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRole(user.getRole());
		userDTO.setMobile(user.getMobile());
		userDTO.setAddress(user.getAddress());
		userDTO.setPaymentInformationList(user.getPaymentInformationList());
		userDTO.setCreatedAt(user.getCreatedAt());
		return userDTO;
	}

}
