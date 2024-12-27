package com.itbulls.learnit.onlinestore.persistence.dto.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.persistence.dto.PrivilegeDto;
import com.itbulls.learnit.onlinestore.persistence.dto.RoleDto;
import com.itbulls.learnit.onlinestore.persistence.entities.Privilege;
import com.itbulls.learnit.onlinestore.persistence.entities.Role;
import com.itbulls.learnit.onlinestore.persistence.entities.impl.DefaultPrivilege;

@Service
public class PrivilegeDtoToPrivilegeConverter {
	
	@Autowired
	RoleDtoToRoleConverter roleCnv;
	
	public Privilege convertPrivilegeDtoToPrivilege(PrivilegeDto privilegeDto)
	{
		Privilege privilege = new DefaultPrivilege();
		privilege.setId(privilegeDto.getId());
		privilege.setName(privilegeDto.getName());
		
		
		return privilege;
	}
	
	public List<Privilege> convertPrivilegeDtosToPrivileges(List<PrivilegeDto> privilegeDtos)
	{
		List<Privilege> privileges = new ArrayList<>();
		for (PrivilegeDto privilegeDto : privilegeDtos)
		{
			privileges.add(convertPrivilegeDtoToPrivilege(privilegeDto));
		}
		return privileges;
	}
	
}
