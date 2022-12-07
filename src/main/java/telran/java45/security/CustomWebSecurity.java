package telran.java45.security;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java45.accounting.dao.UserAccountRepository;
import telran.java45.accounting.model.UserAccount;
import telran.java45.forum.dao.PostRepository;
import telran.java45.forum.model.Post;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {

	final PostRepository postRepository;
	final UserAccountRepository userRepository;
	
	public boolean checkPostAuthor(String id, String user) {
		Post post = postRepository.findById(id).orElse(null);
		return post != null && user.equalsIgnoreCase(post.getAuthor());		
	}
	
	public boolean checkExpdate(String login) {
		UserAccount userAccount = userRepository.findById(login).orElse(null);
		return userAccount != null && LocalDateTime.now().isBefore(userAccount.getExpDate());
	}
	
}
