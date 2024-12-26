package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.itbulls.learnit.onlinestore.persistence.dto.PrivilegeDto;

public class JpaPrivilegeDao {
	EntityManagerFactory emf = null;
	EntityManager em = null;

	public void save(PrivilegeDto privilege)
	{
		emf = Persistence.createEntityManagerFactory("persistence-unit");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(privilege);
		
		em.getTransaction().commit();
	}
	
	public PrivilegeDto getPrivilegeByName(String privilegeName)
	{
		
		emf = Persistence.createEntityManagerFactory("persistence-unit");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<PrivilegeDto> query = em.createQuery("SELECT p FROM privilege p WHERE p.name = :privilegeName", PrivilegeDto.class);
		query.setParameter("privilegeName", privilegeName);
		try {
			PrivilegeDto privilegeDto = query.getResultList().stream().findFirst().orElse(null);

			em.getTransaction().commit();
			return privilegeDto;
		} catch (NoResultException e) {
			return null;
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
