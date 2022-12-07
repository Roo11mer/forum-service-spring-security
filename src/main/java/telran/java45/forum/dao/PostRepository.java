package telran.java45.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java45.forum.model.Post;

public interface PostRepository extends CrudRepository<Post, String> {

	Stream<Post> findPostsByAuthorIgnoreCase(String author);
	
	Stream<Post> findPostsByTagsIgnoreCase(List<String> tags);
	
	Stream<Post> findPostsByDateCreatedBetween(LocalDate from, LocalDate to);

		
}


