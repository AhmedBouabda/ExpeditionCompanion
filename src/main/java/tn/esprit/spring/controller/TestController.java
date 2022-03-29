package tn.esprit.spring.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.request.SignupRequest;
import tn.esprit.spring.payload.response.MessageResponse;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.services.UserDetailsImpl;
import tn.esprit.spring.service.UserService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TestController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/employee")
	//@PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
	public String medecinAccess() {
		return "Employee Board.";
	}
	
	@GetMapping("/admin")
	//@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/company")
	//@PreAuthorize("hasRole('COMPANY') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public String campanyAccess() {
		return "Campany Board.";
	}
	
	
	//http://localhost:8089/SpringMVC/api/profile
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public UserDetailsImpl currentUserName(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
       // return userDetails.getAddress();
		// return userDetails.getId(); //LONG
	    return userDetails;

    }
//http://localhost:8089/SpringMVC/api/afficherUsers
	@GetMapping("/afficherUsers")
	public List<User> getAllUser() {
		return userService.findAll();
	}
	//http://localhost:8089/SpringMVC/api/disableUser/montassar
	@PutMapping("/disableUser/{username}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> DisableUser(@PathVariable(value = "username") String username,@Valid @RequestBody SignupRequest signUpRequest) {
		
		if (!userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username not found!"));
		}
		
		User U = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		U.setEtatAcc(false);
		
		userService.updateUser(U);
		
		return ResponseEntity.ok(new MessageResponse("User Disabled successfully!"));
	}

}
