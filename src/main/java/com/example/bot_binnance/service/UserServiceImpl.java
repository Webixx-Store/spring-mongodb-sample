package com.example.bot_binnance.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.model.User;
import com.example.bot_binnance.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public Optional<User> findByEmailGetLogin(String email, String password) {
		 Optional<User> user = userRepository.findByEmailOrId(email ,email );
		 String passwordEncoder =this.passwordEncoder.encode(password);
	     String passwordInDatabase =  user.map(User::getPassword).get();
	     if(this.passwordEncoder.matches(passwordInDatabase, passwordEncoder)) {
	    	 return user;
	     }else {
	    	 return Optional.empty();
	     }
		
	}
	
	
	/**
     * Thêm mới hoặc cập nhật thông tin người dùng
     * @param user Đối tượng người dùng cần chèn hoặc cập nhật
     * @return Đối tượng người dùng đã được lưu
     */
    public User saveOrUpdateUser(User user) {
        if (user.getId() != null) {
            // Nếu có ID, tìm kiếm người dùng theo ID
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                // Cập nhật thông tin người dùng
                User updatedUser = existingUser.get();
                updateUserInfo(updatedUser, user);
                return userRepository.save(updatedUser);
            }
        }
        
        // Nếu không có ID hoặc ID không tồn tại, kiểm tra email
        Optional<User> existingUserByEmail = userRepository.findByEmailOrId(user.getEmail() ,user.getEmail() );
        if (existingUserByEmail.isPresent()) {
            // Cập nhật thông tin người dùng
            User updatedUser = existingUserByEmail.get();
            updateUserInfo(updatedUser, user);
            return userRepository.save(updatedUser);
        }
        
        // Nếu không tìm thấy người dùng, mã hóa mật khẩu và lưu người dùng mới
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTimeCreate(LocalDateTime.now());
        return userRepository.save(user);
    }

    private void updateUserInfo(User existingUser, User newUser) {
        existingUser.setUsername(newUser.getUsername());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Mã hóa mật khẩu nếu cần
        existingUser.setFirstName(newUser.getFirstName());
        existingUser.setLastName(newUser.getLastName());
        existingUser.setPhoneNumber(newUser.getPhoneNumber());
        existingUser.setAddress(newUser.getAddress());
        existingUser.setDateOfBirth(newUser.getDateOfBirth());
        existingUser.setTimeUpdate(LocalDateTime.now());
    }


	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmailOrId(email , email);
	}


}
