package com.example.bot_binnance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bot_binnance.model.Menu;
@Repository
public interface MenuRepository extends MongoRepository<Menu, String>{
	
    @Query("{ '_id': ?0, 'comId': ?1 }")
    Menu findByIdAndComId(String id, String comId);

}
