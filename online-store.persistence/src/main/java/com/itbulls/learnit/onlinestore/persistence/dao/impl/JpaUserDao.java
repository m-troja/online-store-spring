package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import java.util.List;

import com.itbulls.learnit.onlinestore.persistence.dao.UserDao;
import com.itbulls.learnit.onlinestore.persistence.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserDao implements UserDao {
	public final Logger LOGGER = LogManager.getLogger(JpaUserDao.class);
	
	EntityManagerFactory emf = null;
	EntityManager em = null;
	
	@Override
	public boolean saveUser(UserDto user) {
		try 
		{ 	 			
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			System.out.println(" ");
			System.out.println("JpaUserDao: userDto.setIsEnabled = " + user.isEnabled()  );
			System.out.println("JpaUserDao: user.getRoles().toString = " + user.getRoles().toString());
			System.out.println(" ");

			em.merge(user);
			LOGGER.info("User merged");

			em.getTransaction().commit();
			return true;
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public List<UserDto> getUsers() {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			List<UserDto> users = em.createQuery("SELECT u FROM user u", UserDto.class).getResultList();
			LOGGER.info("SELECT u FROM user u");
			em.getTransaction().commit();
			return users;
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public UserDto getUserByEmail(String userEmail) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<UserDto> query = em.createQuery("SELECT u FROM user u WHERE u.email = :email", UserDto.class);
			query.setParameter("email", userEmail);
			LOGGER.info("SELECT u FROM user u WHERE u.email = " + userEmail);
			try {
				UserDto user = query.getSingleResult();
				em.getTransaction().commit();
				return user;
			} catch (NoResultException e) {
				return null;
			}
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public UserDto getUserById(int id) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			UserDto user = em.find(UserDto.class, id);
			LOGGER.info("SELECT u FROM user u WHERE u.id = " + id);
			em.getTransaction().commit();
			return user;
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public UserDto getUserByPartnerCode(String partnerCode) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			System.out.println(partnerCode);
			TypedQuery<UserDto> query = em.createQuery("SELECT u FROM user u WHERE u.partnerCode = :partnerCode", UserDto.class);
			query.setParameter("partnerCode", partnerCode);
			
			LOGGER.info("SELECT u FROM user u WHERE u.partnerCode =  " + partnerCode);
			
			try {
				UserDto user = query.getSingleResult();
				em.getTransaction().commit();
				return user;
			} catch (NoResultException e) {
				return null;
			}
			
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public void updateUser(UserDto newUser) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			em.merge(newUser);
			LOGGER.info("Merge " + newUser.toString());
			em.getTransaction().commit();
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

	@Override
	public List<UserDto> getReferralsByUserId(int id) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<UserDto> query = em.createQuery("SELECT u FROM user u WHERE u.referrerUser.id = :id", UserDto.class);
			query.setParameter("id", id);
			LOGGER.info("SELECT u FROM user u WHERE u.referrerUser.id = " + id);
			List<UserDto> users = query.getResultList();
			em.getTransaction().commit();
			return users;
		}
		finally 
		{
			if (emf !=null )
			{
				emf.close();
			}
			
			if (em !=null )
			{
				em.close();
			}
		}
	}

}
