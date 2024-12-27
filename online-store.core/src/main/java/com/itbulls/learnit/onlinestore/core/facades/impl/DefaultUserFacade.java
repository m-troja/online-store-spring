package com.itbulls.learnit.onlinestore.core.facades.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.onlinestore.core.facades.UserFacade;
import com.itbulls.learnit.onlinestore.core.services.AffiliateMarketingService;
import com.itbulls.learnit.onlinestore.core.services.impl.DefaultAffiliateMarketingService;
import com.itbulls.learnit.onlinestore.persistence.SetupDataLoader;
import com.itbulls.learnit.onlinestore.persistence.dao.RoleDao;
import com.itbulls.learnit.onlinestore.persistence.dao.UserDao;
import com.itbulls.learnit.onlinestore.persistence.dao.impl.JpaUserDao;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.RoleDtoToRoleConverter;
import com.itbulls.learnit.onlinestore.persistence.dto.converters.UserDtoToUserConverter;
import com.itbulls.learnit.onlinestore.persistence.entities.User;

@Service
public class DefaultUserFacade implements UserFacade {
	
	@Autowired
	private UserDao userDao = new JpaUserDao();
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	RoleDtoToRoleConverter roleCnv;
	
	@Autowired 
	private UserDtoToUserConverter userConverter = new UserDtoToUserConverter();
	
	@Autowired
	private AffiliateMarketingService marketingService = new DefaultAffiliateMarketingService();
	
	@Override
	public void registerUser(User user, String referrerCode) {
		user.setPartnerCode(marketingService.generateUniquePartnerCode());
		user.setReferrerUser(userConverter.convertUserDtoToUser(userDao.getUserByPartnerCode(referrerCode)));
		user.setRoles(roleCnv.convertRoleDtosToRoles(Arrays.asList(roleDao.getRoleByRoleName(SetupDataLoader.ROLE_CUSTOMER))));
		System.out.println();
		if (user.getRoles() != null)
		{
			System.out.println(" DefaultUserFacade registerUser setRoles: " + user.getRoles().toString());

		}
		else { System.out.println("DefaultUserFacade registerUser setRoles Roles are null"); }
		System.out.println(" ");

		userDao.saveUser(userConverter.convertUserToUserDto(user));
	}


	@Override
	public User getUserByEmail(String email) {
		return userConverter.convertUserDtoToUser(userDao.getUserByEmail(email));
	}

	@Override
	public List<User> getUsers() {
		return userConverter.convertUserDtosToUsers(userDao.getUsers());
	}

	@Override
	public User getUserById(Integer userId) {
		return userConverter.convertUserDtoToUser(userDao.getUserById(userId));
	}
                                                        
	@Override
	public List<User> getReferralsForUser(User loggedInUser) {
		return userConverter.convertUserDtosToUsers(userDao.getReferralsByUserId(loggedInUser.getId()));
	}


	@Override
	public void updateUser(User referrerUser) {
		// TODO Auto-generated method stub
		
	}
}
