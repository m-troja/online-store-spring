package com.itbulls.learnit.onlinestore.persistence.dto.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.persistence.dto.UserDto;
import com.itbulls.learnit.onlinestore.persistence.entities.User;
import com.itbulls.learnit.onlinestore.persistence.entities.impl.DefaultUser;

@Service
public class UserDtoToUserConverter {
	
	@Autowired
	private RoleDtoToRoleConverter roleCnv;
	

	public UserDto convertUserIdToUserDtoWithOnlyId(int customerId) {
		UserDto userDto = new UserDto();
		userDto.setId(customerId);
		return userDto;
	}
	
	public User convertUserDtoToUser(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		User user = new DefaultUser();
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setRoles(roleCnv.convertRoleDtosToRoles(userDto.getRoles()));
		user.setMoney(userDto.getMoney().doubleValue());
		user.setCreditCard(userDto.getCreditCard());
		user.setPartnerCode(userDto.getPartnerCode());
		user.setReferrerUser(convertUserDtoToUser(userDto.getReferrerUser()));
		user.setIsEnabled(userDto.isEnabled());
		
		System.out.println();
		System.out.println("convertUserToUserDto: user.getRoleName " + user.getRoleName());
		System.out.println();

		return user;
	}
	
	public UserDto convertUserToUserDto(User user) {
		UserDto userDto = null;
		if (user != null) {
			userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setPassword(user.getPassword());
			userDto.setRoles(roleCnv.convertRolesToRoleDtos(user.getRoles()));
			userDto.setMoney(BigDecimal.valueOf(user.getMoney()));
			userDto.setCreditCard(user.getCreditCard());
			userDto.setPartnerCode(user.getPartnerCode());
			userDto.setReferrerUser(convertUserToUserDto(user.getReferrerUser()));
			userDto.setIsEnabled(user.isEnabled());
			System.out.println();
			System.out.println("convertUserToUserDto: user.getRoles " + user.getRoles());
			System.out.println("convertUserToUserDto: roleCnv.convertRolesToRoleDtos(user.getRoles) " + roleCnv.convertRolesToRoleDtos(user.getRoles()));
			System.out.println("convertUserToUserDto: user.getRole().toString " + user.getRoles().toString());
			System.out.println("convertUserToUserDto: userDto.getRoles " + userDto.getRoles());
			System.out.println("convertUserToUserDto: user.getRole().toString " + userDto.getRoles().toString());
			System.out.println();

		}
		return userDto;
	}

	public List<User> convertUserDtosToUsers(List<UserDto> userDtos) {
		List<User> users = new ArrayList<>();
		
		for (UserDto userDto : userDtos) {
			users.add(convertUserDtoToUser(userDto));
		}
		return users;
	}

}
