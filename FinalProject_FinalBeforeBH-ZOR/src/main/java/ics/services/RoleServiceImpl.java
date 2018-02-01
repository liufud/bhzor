package ics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ics.dao.RoleDAO;
import ics.model.Role;
import ics.model.User;

public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private Role role;
	
	@Transactional
	public void updateRole(Role role) {
		roleDAO.updateRole(role);
	}
	@Transactional
	public Role findRoleByUserId(Long userId) {
		role = roleDAO.findRoleByUserId(userId);
		return role;
	}

}
