package com.itbulls.learnit.onlinestore.persistence.dto.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.persistence.dto.RoleDto;
import com.itbulls.learnit.onlinestore.persistence.entities.Role;
import com.itbulls.learnit.onlinestore.persistence.entities.impl.DefaultRole;

@Service
public class RoleDtoToRoleConverter {
	
	@Autowired
	PrivilegeDtoToPrivilegeConverter privilegeCnv;

	public RoleDto convertRoleNameToRoleDtoWithOnlyRoleName(String roleName) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleName(roleName);
		return roleDto;
	}
	
	public Role convertRoleDtoToRole(RoleDto roleDto) 
	{
		Role role = new DefaultRole();
		role.setId(roleDto.getId());
		role.setRoleName(roleDto.getRoleName());
		role.setPrivileges(privilegeCnv.convertPrivilegeDtosToPrivileges(roleDto.getPrivileges()));
		return role;
		
	}
	
	public List<Role> convertRoleDtosToRoles(List<RoleDto> roleDtos)
	{
		List<Role> roles = new ArrayList<>();
		for (RoleDto roleDto: roleDtos)
		{
			roles.add(convertRoleDtoToRole(roleDto));
		}
		return roles;
	}

}
