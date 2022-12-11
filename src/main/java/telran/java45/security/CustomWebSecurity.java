package telran.java45.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java45.forum.dao.PostRepository;
import telran.java45.forum.model.Post;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {

	final PostRepository postRepository;
	
	public boolean checkPostAuthor(String id, String user) {
		Post post = postRepository.findById(id).orElse(null);
		return post != null && user.equalsIgnoreCase(post.getAuthor());		
	}
	
}
