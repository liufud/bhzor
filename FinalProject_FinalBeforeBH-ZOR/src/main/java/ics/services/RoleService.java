package ics.services;

import ics.model.Role;

public interface RoleService {
	void updateRole(Role role);
	Role findRoleByUserId(Long userId);
}
