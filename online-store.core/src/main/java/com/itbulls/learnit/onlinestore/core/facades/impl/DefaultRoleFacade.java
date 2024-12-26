package com.itbulls.learnit.onlinestore.core.facades.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.persistence.dao.impl.JpaRoleDao;
import com.itbulls.learnit.onlinestore.persistence.dto.RoleDto;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.RoleDtoToRoleConverter;
import com.itbulls.learnit.onlinestore.persistence.entities.Role;

@Service
public class DefaultRoleFacade {
	
	@Autowired
	JpaRoleDao roleRepository;
	
	@Autowired
	RoleDtoToRoleConverter roleCnv;
	
	public Role getRoleByName(String roleName)
	{
		RoleDto roleDto = roleRepository.getRoleByRoleName(roleName);
		return roleCnv.convertRoleDtoToRole(roleDto);
	}
}
