package tn.esprit.spring.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import tn.esprit.spring.entities.ERole;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.VerificationToken;
import tn.esprit.spring.payload.request.LoginRequest;
import tn.esprit.spring.payload.request.PasswordRequest;
import tn.esprit.spring.payload.request.ProfileRequest;
import tn.esprit.spring.payload.request.SignupRequest;
import tn.esprit.spring.payload.response.JwtResponse;
import tn.esprit.spring.payload.response.MessageResponse;
import tn.esprit.spring.repository.RoleRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.jwt.JwtUtils;
import tn.esprit.spring.security.services.UserDetailsImpl;
import tn.esprit.spring.service.OnRegistrationCompleteEvent;
import tn.esprit.spring.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;



	Cookie cookie=new Cookie("access_token","");
	LocalDateTime loginTime=null;
	LocalDateTime logoutTime=null;


/*	@Autowired
	TwilioOTPHandler twilioOTPHandler;*/
	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> logout(@PathVariable("id") long id) {
		User u=userService.findOne(id);
		if (u!=null)
		{
		return
				ResponseEntity
						.ok()
						.body(new MessageResponse("user deleted"));}
		else return ResponseEntity.badRequest().body(new MessageResponse("user doesn't exist"));
	}
	@GetMapping("/user/{id}")
	public User findUser(@PathVariable("id") long id) {
		User u=userService.findOne(id);
		return u;
	}
	@GetMapping("/users")
	public List<User> findUser() {
		return userService.findAll();
	}



	@PostMapping("/logout")
	public ResponseEntity<?> logout( HttpServletResponse response) {

		response.setContentType(null);
		return
		ResponseEntity
				.ok()
				.body(new MessageResponse("See you soon "));



	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User U = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
	
		
		if (!userDetails.getEtatAcc()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Your account is Disabled by Admin!"));
		}
		
		if (!U.isEnabled()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Please Confirm your Account, We've sent you a confirmation email"));
		}
		System.out.println(userDetails.getEtatAcc());
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		Map<String, String> tokens = new HashMap<>();

		JwtResponse access_token=new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles);

		tokens.put("access_token", access_token.getAccessToken());

		//cookie.setSecure(true);
		//cookie.setHttpOnly(true);
		response.addCookie(cookie);


		response.setHeader("access_token", access_token.getAccessToken());
		loginTime=LocalDateTime.now();

		return ResponseEntity.ok(access_token);
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request ) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),signUpRequest.getFirstName()
							 ,signUpRequest.getLastName(),signUpRequest.getAddress(),signUpRequest.getDateN(),signUpRequest.getTel()
							 ,signUpRequest.getSexe());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(employeeRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "company":
					Role infirmierRole = roleRepository.findByName(ERole.ROLE_COMPANY)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(infirmierRole);

					break;

			
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		String appUrl = request.getContextPath();
		User registered= user;
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
		request.getLocale(), appUrl));

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<?> UpdateUser(@Valid @RequestBody ProfileRequest profileRequest
			,Authentication authentication) {
		
		// update user's account
		UserDetailsImpl U1 = (UserDetailsImpl) authentication.getPrincipal();		
		User U = userRepository.findByUsername(U1.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + U1.getUsername()));
		U.setFirstName(profileRequest.getFirstName());
		U.setLastName(profileRequest.getLastName());
		U.setAddress(profileRequest.getAddress());
		U.setDateN(profileRequest.getDateN());
		U.setTel(profileRequest.getTel());
		U.setEmail(profileRequest.getEmail());
		
		userService.updateUser(U);

		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}
	
	//http://localhost:8089/SpringMVC/api/updatepassword
	@PutMapping("/updatepassword")
	public ResponseEntity<?> UpdatePassword(@Valid @RequestBody PasswordRequest PasswordRequest, Authentication authentication) {
		
		UserDetailsImpl U1 = (UserDetailsImpl) authentication.getPrincipal();		
		User U = userRepository.findByUsername(U1.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + U1.getUsername()));
		
			if(PasswordRequest.getPassword().equals(PasswordRequest.getPasswordConfirm()))
			{
				U.setPassword(encoder.encode(PasswordRequest.getPassword()));
				userService.updateUser(U);

				return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
			}
			else 		
				return ResponseEntity.ok(new MessageResponse("password and confirm password does not match!"));
		 	
		
	}
	
	@GetMapping("/regitrationConfirm/token/{token}")
	public ResponseEntity<?> confirmRegistration
	  (WebRequest request, Model model, @PathVariable(value = "token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
			return ResponseEntity.ok(new MessageResponse("InvaLID vERFICATION Token"));

	    }
	     
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return ResponseEntity.ok(new MessageResponse("Link Expired!"));

	    } 
	     
	    user.setEnabled(true); 
	    userService.updateUser(user);
		return ResponseEntity.ok(new MessageResponse("Account Confirmed with succes"));
	}







}
