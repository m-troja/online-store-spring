package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import com.itbulls.learnit.onlinestore.persistence.dao.PurchaseStatusDao;
import com.itbulls.learnit.onlinestore.persistence.dto.PurchaseStatusDto;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class JpaPurchaseStatusDao implements PurchaseStatusDao {
	EntityManagerFactory emf = null;
	EntityManager em = null;
	
	@Override
	public PurchaseStatusDto getPurchaseStatusById(Integer id) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			PurchaseStatusDto purchaseStatus = em.find(PurchaseStatusDto.class, id);
			
			em.getTransaction().commit();
			
			return purchaseStatus;
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
