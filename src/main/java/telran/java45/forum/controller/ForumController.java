package telran.java45.forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java45.forum.dto.DateFromToDto;
import telran.java45.forum.dto.NewCommentDto;
import telran.java45.forum.dto.NewPostDto;
import telran.java45.forum.dto.PostDto;
import telran.java45.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
public class ForumController {

	final ForumService forumService;


	@PostMapping("/forum/post/{author}")
	public PostDto addPost(@RequestBody NewPostDto newPost, @PathVariable String author) {
		return forumService.addNewPost(newPost, author);
	}

	@GetMapping("/forum/post/{id}")
	public PostDto getPost(@PathVariable String id) {
		return forumService.getPost(id);
	}

	@DeleteMapping("/forum/post/{id}")
	public PostDto removePost(@PathVariable String id) {
		return forumService.removePost(id);
	}

	@PutMapping("/forum/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto postUpdateDto) {
		return forumService.updatePost(postUpdateDto, id);
	}

	@PutMapping("/forum/post/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addLike(@PathVariable String id) {
		forumService.addLike(id);
	}

	@PutMapping("/forum/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String id, @PathVariable String author,
			@RequestBody NewCommentDto newCommentDto) {
		return forumService.addComment(id, author, newCommentDto);
	}

	@GetMapping("/forum/posts/author/{author}")
	public Iterable<PostDto> getPostsByAuthor(@PathVariable("author") String name) {
		return forumService.findPostsByAuthor(name);
	}
	
	@PostMapping("/forum/posts/tags")
	public Iterable<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	}
	
	@PostMapping("/forum/posts/period")
	public Iterable<PostDto> findPostsByDate(@RequestBody DateFromToDto dateFromToDto) {
		return forumService.findPostsByDates(dateFromToDto);
	}

}