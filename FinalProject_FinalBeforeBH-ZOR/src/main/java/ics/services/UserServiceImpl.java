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

	public List<User> getAllCustomers() {
		return userDAO.getAllCustomers();
	}

	public List<User> getAllDistributors() {
		return userDAO.getAllDistributors();
	}

	public List<User> getAllAdministrators() {
		return userDAO.getAllAdministrators();
	}

	public List<User> getUsersByRole(String roleName) {
		return userDAO.getUsersByRole(roleName);
	}

	public Long totalUserNum() {
		return userDAO.totalUserNum();
	}

	public List<User> list(Integer offset, Integer maxResults) {
		return userDAO.list(offset, maxResults);
	}

	public User findUserByEmail(String email) {
		return userDAO.findUserByEmail(email);
	}


}
