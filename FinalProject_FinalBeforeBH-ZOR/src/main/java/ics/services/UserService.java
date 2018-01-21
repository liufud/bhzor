package ics.services;

import java.util.List;

import ics.model.User;

public interface UserService {
	void addUser(User user);
	void editUser(User user);
	void deleteUser(Long userId);
	User findUser(Long userId);
	User findUserByName(String username);
	List<User> getAllUsers();
}
