package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	// CREATE
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// UPDATE
	PostDto updatePost(PostDto postDto, Integer postId);

	// DELETE
	void deletePost(Integer postId);

	// GET ALL POSTS
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// GET POST BY ID
	PostDto getPostById(Integer postId);

	// GET ALL POSTS BY CATEGORY
	//List<PostDto> getPostByCategory(Integer categoryId);
	PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);


	// GET ALL POSTS BY USER
	//List<PostDto> getPostByUser(Integer userId);
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);


	// SEARCH POSTS
	List<PostDto> searchPosts(String keyword);
}
