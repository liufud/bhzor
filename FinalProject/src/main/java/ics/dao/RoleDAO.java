package ics.dao;

import ics.model.Role;

public interface RoleDAO {
	void updateRole(Role role);
	Role findRoleByUserId(Long userId);
}
