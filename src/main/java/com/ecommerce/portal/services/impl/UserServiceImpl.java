package com.ecommerce.portal.services.impl;


import com.ecommerce.portal.dtos.AuthResponse;
import com.ecommerce.portal.dtos.RsaKeyConfigurationProperties;
import com.ecommerce.portal.dtos.UserDTO;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.repositories.UserRepository;
import com.ecommerce.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RsaKeyConfigurationProperties rsaKeyConfigurationProperties;

    @Override
    public User findUserById(Long userId) throws UserException {
        return  this.userRepository.findById(userId)
                .orElseThrow(()->new UserException("user not found with"+userId));
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        Optional<User> user=this.userRepository.findByEmail(email);

        if(!user.isPresent()) {
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+user.get().getEmail());
        return user.get();

    }

    @Override
    public List<UserDTO> findAllUsers() {
       List<User> usersList=this.userRepository.findAllByOrderByCreatedAtDesc();
       List<UserDTO> userDTOList=new ArrayList<>();

       for (User user:usersList){
          UserDTO userDTO= this.entityToDTO(user);
           userDTOList.add(userDTO);
       }
       return userDTOList;
    }

    private String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);

        Claims claims=Jwts.parserBuilder().setSigningKey(rsaKeyConfigurationProperties.publicKey()).build().parseClaimsJws(jwt).getBody();
        String email=String.valueOf(claims.get("email"));

        return email;

    }

    private UserDTO entityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setMobile(user.getMobile());
        userDTO.setAddress(user.getAddress());
        userDTO.setPaymentInformationList(user.getPaymentInformationList());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;

    }
}
