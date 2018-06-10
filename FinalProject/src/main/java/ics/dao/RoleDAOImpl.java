package ics.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ics.model.Role;
import ics.model.User;
import ics.services.UserService;

public class RoleDAOImpl implements RoleDAO{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserService userService;
	
	@Transactional
	public void updateRole(Role role) {
//		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
//		Role role = (Role) criteria.add(Restrictions.eq("user", user)).uniqueResult();
//		role.setRoleName(roleName);
//		sessionFactory.getCurrentSession().update(role);
		
		sessionFactory.getCurrentSession().update(role);
	}
	
	@Transactional
	public Role findRoleByUserId(Long userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
		User user = userService.findUser(userId);
		Role role = (Role) criteria.add(Restrictions.eq("user", user)).uniqueResult();
		return role;		
	}

}
