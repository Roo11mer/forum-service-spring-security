package telran.java45.accounting.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import telran.java45.accounting.dao.UserAccountRepository;
import telran.java45.accounting.dto.RolesResponseDto;
import telran.java45.accounting.dto.UserAccountResponceDto;
import telran.java45.accounting.dto.UserRegisterDto;
import telran.java45.accounting.dto.UserUpdateDto;
import telran.java45.accounting.dto.exception.UserExistsException;
import telran.java45.accounting.dto.exception.UserNotExistsException;
import telran.java45.accounting.dto.exception.UserNotFoundException;
import telran.java45.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService  , CommandLineRunner {
	
	final UserAccountRepository repository;
	final ModelMapper modelMapper;
	final PasswordEncoder passwordEncoder;
	
	@Value(value = "30")  
	@Getter
	@Setter
	private long period;
	
	@Override
	public UserAccountResponceDto addUser(UserRegisterDto userRegisterDto) throws Exception {
		if (repository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException(userRegisterDto.getLogin());
		}
		UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
		String password = passwordEncoder.encode("admin");
		userAccount.setPassword(password);
		repository.save(userAccount);
		return modelMapper.map(userAccount, UserAccountResponceDto.class);
	}

	@Override
	public UserAccountResponceDto getUser(String login) {
		UserAccount userAccount = repository.findById(login).orElseThrow();
		return modelMapper.map(userAccount, UserAccountResponceDto.class);
	}

	@Override
	public UserAccountResponceDto removeUser(String login) {
		if (!repository.existsById(login)) {
			throw new UserNotExistsException(login);
		}
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		repository.delete(userAccount);
		return modelMapper.map(userAccount, UserAccountResponceDto.class);
	}

	@Override
	public UserAccountResponceDto editUser(String login, UserUpdateDto updateDto) {
		if (!repository.existsById(login)) {
			throw new UserNotExistsException(login);
		}
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (updateDto.getFirstName() != null) {
			userAccount.setFirstName(updateDto.getFirstName());
		}
		if (updateDto.getLastName() != null) {
			userAccount.setLastName(updateDto.getLastName());
		}
		repository.save(userAccount);
		return modelMapper.map(userAccount, UserAccountResponceDto.class);
	}

	@Override
	public RolesResponseDto changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (isAddRole) {
			userAccount.addRole(role);
		} else {
			userAccount.removeRole(role);
		}
		repository.save(userAccount);
		return modelMapper.map(userAccount, RolesResponseDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		UserAccount userAccount = repository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		String hashPassword = passwordEncoder.encode(newPassword);
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now().plusDays(period));
		repository.save(userAccount);
	}

	@Override
	public void run(String... args) throws Exception {
		if(!repository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			UserAccount userAccount = new UserAccount("admin", password, "", "");
			userAccount.addRole("MODERATOR");
			userAccount.addRole("ADMINISTRATOR");
			repository.save(userAccount);
		}
		
	}

}
