package com.itbulls.learnit.onlinestore.persistence.entities;

import java.util.List;

public interface Role {
	
	Integer getId();
	void setId(Integer id);
	
	String getRoleName();
	void setRoleName(String roleName);
	
	List<User> getUsers();
	void setUsers(List<User> users);
	
	List<Privilege> getPrivileges();
	void setPrivileges(List<Privilege> privileges);

}