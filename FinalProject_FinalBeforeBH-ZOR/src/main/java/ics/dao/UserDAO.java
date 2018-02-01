package ics.dao;

import java.util.List;

import ics.model.User;

public interface UserDAO {
	void addUser(User user);
	void editUser(User user);
	void deleteUser(Long userId);
	User findUser(Long userId);
	User findUserByName(String username);
	List<User> getAllUsers();
	List<User> getAllCustomers();
	List<User> getAllDistributors();
	List<User> getAllAdministrators();
}
