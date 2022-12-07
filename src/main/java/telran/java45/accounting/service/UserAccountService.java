package telran.java45.accounting.service;

import telran.java45.accounting.dto.RolesResponseDto;
import telran.java45.accounting.dto.UserAccountResponceDto;
import telran.java45.accounting.dto.UserRegisterDto;
import telran.java45.accounting.dto.UserUpdateDto;

public interface UserAccountService {
	
	UserAccountResponceDto addUser(UserRegisterDto userRegistrDto) throws Exception;

	UserAccountResponceDto getUser(String login);

	UserAccountResponceDto removeUser(String login);

	UserAccountResponceDto editUser(String login, UserUpdateDto updateDto);

	RolesResponseDto changeRolesList(String login, String role, boolean isAddRole);
	
	void changePassword(String login, String newPassword);
}
