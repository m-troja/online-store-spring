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
		System.out.println();
		System.out.println("convertRoleDtoToRole roleDto.getPrivileges().toString: " + roleDto.getPrivileges().toString());
		System.out.println("convertRoleDtoToRole role.getPrivileges().toString: "    + role.getPrivileges().toString());
		System.out.println("convertRoleDtoToRole role.toString: " + role.toString());
		System.out.println();

		return role;
		
	}
	
	public List<Role> convertRoleDtosToRoles(List<RoleDto> roleDtos)
	{
		List<Role> roles = new ArrayList<>();
		for (RoleDto roleDto: roleDtos)
		{
			roles.add(convertRoleDtoToRole(roleDto));
			System.out.println();
			System.out.print("convertRoleDtosToRoles roleDto.toString: " + roleDto.toString());
		}
		System.out.println("convertRoleDtosToRoles roles.toString: " + roles.toString());
		System.out.println();

		return roles;
	}
	
	public List<RoleDto> convertRolesToRoleDtos(List<Role> roles) {
		List<RoleDto> roleDtos = new ArrayList<>();
		for (Role role : roles) {
			roleDtos.add(convertRoleToRoleDto(role));
		}
		return roleDtos;
	}
	
	private RoleDto convertRoleToRoleDto(Role role) {
		RoleDto rDto = new RoleDto();
		rDto.setId(role.getId());
		rDto.setRoleName(role.getRoleName());
		return rDto;
	}
	
	
}
