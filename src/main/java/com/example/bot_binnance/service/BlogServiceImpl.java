package com.example.bot_binnance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.model.Blog;
import com.example.bot_binnance.repository.BlogRepository;
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    // Lấy tất cả các blog
    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    // Lấy blog theo ID
    @Override
    public Optional<Blog> getBlogById(String id) {
        return blogRepository.findById(id);
    }

    // Lưu blog mới
    @Override
    public Blog saveBlog(Blog blog) {
    	blog.setCreatedAt( LocalDateTime.now());
    	blog.setUpdateAt( LocalDateTime.now());
    	if(blog.getId() == null  || "".equals(blog.getId())) {
    		return blogRepository.save(blog);
    	}
    	Optional<Blog> blogCheck = blogRepository.findById(blog.getId());
    	if(blogCheck.isPresent()) {
    		 Blog existingBlog = blogCheck.get();
             existingBlog.setTitle(blog.getTitle());
             existingBlog.setContent(blog.getContent());
             existingBlog.setCategory(blog.getCategory());
             existingBlog.setUpdateAt( LocalDateTime.now());
             return blogRepository.save(existingBlog);
    	}
    	return null;
        
    }

    // Cập nhật blog
    @Override
    public Blog updateBlog(String id, Blog blogDetails) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isPresent()) {
            Blog existingBlog = blog.get();
            existingBlog.setTitle(blogDetails.getTitle());
            existingBlog.setContent(blogDetails.getContent());
            existingBlog.setCategory(blogDetails.getCategory());
            existingBlog.setUpdateAt( LocalDateTime.now());
            return blogRepository.save(existingBlog);
        }
        return null;
    }

    // Xóa blog theo ID
    @Override
    public void deleteBlog(String id) {
        blogRepository.deleteById(id);
    }
}
