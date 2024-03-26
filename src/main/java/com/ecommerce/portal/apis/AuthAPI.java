package com.ecommerce.portal.apis;


import com.ecommerce.portal.config.services.CustomUserDetailsService;
import com.ecommerce.portal.dtos.AuthRequest;
import com.ecommerce.portal.dtos.AuthResponse;
import com.ecommerce.portal.dtos.UserDTO;
import com.ecommerce.portal.dtos.UserRole;
import com.ecommerce.portal.entities.Cart;
import com.ecommerce.portal.entities.User;
import com.ecommerce.portal.exceptions.UserException;
import com.ecommerce.portal.repositories.UserRepository;
import com.ecommerce.portal.services.CartService;
import com.ecommerce.portal.services.UserService;
import com.ecommerce.portal.services.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthAPI {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetails;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getEmail();
        String password = authRequest.getPassword();

        System.out.println(username +" ----- "+password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = authenticationService.generateToken(authentication);
        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token) throws Exception {
        log.info("Entered validate token method at "+ LocalDateTime.now());
        return authenticationService.validateToken(token);
    }

    @GetMapping("{userId}")
    public UserDTO findUserById(@PathVariable Long userId) throws UserException {
        User user=this.userService.findUserById(userId);
        return this.entityToDTO(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDTO userDTO)throws UserException {

        String email=userDTO.getEmail();
        String password=  userDTO.getPassword();
        String firstName=userDTO.getFirstName();
        String lastName=userDTO.getLastName();
        UserRole role=userDTO.getRole();
        Optional<User> isEmailExist=this.userRepository.findByEmail(email);
        if(isEmailExist.isPresent()){
            throw new UserException("username already exists plz try with another "+email);
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setRole(role);

        User savedUser=this.userRepository.save(createdUser);
        Cart cart=this.cartService.createCart(savedUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=authenticationService.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(token,true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private UserDTO entityToDTO(User user){
        UserDTO userDTO = new UserDTO();
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

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("sign in userDetails - "+userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
