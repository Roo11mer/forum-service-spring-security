package telran.java45.accounting.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java45.accounting.dto.RolesResponseDto;
import telran.java45.accounting.dto.UserAccountResponceDto;
import telran.java45.accounting.dto.UserRegisterDto;
import telran.java45.accounting.dto.UserUpdateDto;
import telran.java45.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	UserAccountService accountService;
	
	@Autowired
	public UserAccountController(UserAccountService accountService) {
		this.accountService = accountService;
	}
	@PostMapping("/register")
	public UserAccountResponceDto register(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
		return accountService.addUser(userRegisterDto);
	}
	@PostMapping("/login")
	public UserAccountResponceDto login(Principal principal) {
		return accountService.getUser(principal.getName());
	}
	
	@DeleteMapping("/user/{login}")
	public UserAccountResponceDto removeUser(@PathVariable String login) {
		return accountService.removeUser(login);
	}
	
	@PutMapping("/user/{login}")
	public UserAccountResponceDto updateUser(@PathVariable String login,@RequestBody UserUpdateDto userUpdateDto) {
		return accountService.editUser(login, userUpdateDto);
	}
	
	@PutMapping("/user/{login}/role/{role}")
	public RolesResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RolesResponseDto removeRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, false);
	}
	
	@PutMapping("/password")
	public void changePassword(Principal principal, @RequestHeader("X-Password") String newPassword) {
		accountService.changePassword(principal.getName(), newPassword);
	}

}