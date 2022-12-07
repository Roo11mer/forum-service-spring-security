package telran.java45.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java45.forum.dao.PostRepository;
import telran.java45.forum.dto.DateFromToDto;
import telran.java45.forum.dto.NewCommentDto;
import telran.java45.forum.dto.NewPostDto;
import telran.java45.forum.dto.PostDto;
import telran.java45.forum.dto.exception.PostNotFoundException;
import telran.java45.forum.model.Comment;
import telran.java45.forum.model.Post;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

	final PostRepository postRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addNewPost(NewPostDto newPost, String author) {
		Post post = modelMapper.map(newPost, Post.class);
		post.setAuthor(author);
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto getPost(String id) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
		return modelMapper.map(post,PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
		postRepository.delete(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(NewPostDto postUpdateDto, String id) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
		post.setContent(postUpdateDto.getContent());
		post.setTitle(postUpdateDto.getTitle());
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
		post.addLike();
		postRepository.save(post);
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
		Comment comment = new Comment(author,newCommentDto.getMessage());
		post.addComment(comment);
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return postRepository.findPostsByAuthorIgnoreCase(author)
				.map(p -> modelMapper.map(p, PostDto.class))
						.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		return postRepository.findPostsByTagsIgnoreCase(tags)
				.map(p -> modelMapper.map(p, PostDto.class))
						.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByDates(DateFromToDto dateFromToDto) {
		return postRepository.findPostsByDateCreatedBetween(dateFromToDto.getDateFrom(), dateFromToDto.getDateTo())
		.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

}
