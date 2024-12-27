package com.itbulls.learnit.onlinestore.core.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.core.facades.RoleFacade;
import com.itbulls.learnit.onlinestore.persistence.dao.RoleDao;
import com.itbulls.learnit.onlinestore.persistence.dao.impl.JpaRoleDao;
import com.itbulls.learnit.onlinestore.persistence.dto.RoleDto;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.RoleDtoToRoleConverter;
import com.itbulls.learnit.onlinestore.persistence.entities.Role;

@Service
public class DefaultRoleFacade implements RoleFacade {
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	RoleDtoToRoleConverter roleCnv;
	
	public Role getRoleByName(String roleName)
	{
		RoleDto roleDto = roleDao.getRoleByRoleName(roleName);
		System.out.println();
		System.out.println("DefaultRoleFacade returned role: " + roleDto.getId() + " " + roleDto.getRoleName());
		System.out.println();
		
		return roleCnv.convertRoleDtoToRole(roleDto);
	}
}
