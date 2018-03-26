package ics.services;

import java.util.List;

import ics.model.User;

public interface UserService {
	void addUser(User user);
	void editUser(User user);
	void deleteUser(Long userId);
	User findUser(Long userId);
	User findUserByName(String username);
	User findUserByEmail(String email);
	List<User> getAllUsers();
	List<User> getUsersByRole(String roleName);
	List<User> getAllCustomers();
	List<User> getAllDistributors();
	List<User> getAllAdministrators();
	public Long totalUserNum();
	List<User> list(Integer offset, Integer maxResults);
}
