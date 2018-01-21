package ics.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ics.dao.UserDAO;
import ics.model.User;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	public void addUser(User user) {
		userDAO.addUser(user);
	}

	public void editUser(User user) {
		userDAO.editUser(user);
	}

	public void deleteUser(Long userId) {
		userDAO.deleteUser(userId);
	}

	public User findUser(Long userId) {
		return userDAO.findUser(userId);
	}

	public User findUserByName(String username) {
		return userDAO.findUserByName(username);
	}

	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}


}