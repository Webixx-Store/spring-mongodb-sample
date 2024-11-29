package com.example.bot_binnance.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.Blog;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
    // Các truy vấn tùy chỉnh có thể được thêm vào đây nếu cần
}