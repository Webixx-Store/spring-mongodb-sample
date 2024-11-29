package com.example.bot_binnance.service;
import java.util.List;
import java.util.Optional;

import com.example.bot_binnance.model.Blog;


public interface BlogService {
	List<Blog> getAllBlogs();

    Optional<Blog> getBlogById(String id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(String id, Blog blogDetails);

    void deleteBlog(String id);

}
