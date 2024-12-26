package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.itbulls.learnit.onlinestore.persistence.dao.PurchaseDao;
import com.itbulls.learnit.onlinestore.persistence.dto.ProductDto;
import com.itbulls.learnit.onlinestore.persistence.dto.PurchaseDto;
import com.itbulls.learnit.onlinestore.persistence.dto.PurchaseStatusDto;
import com.itbulls.learnit.onlinestore.persistence.dto.UserDto;
import com.itbulls.learnit.onlinestore.persistence.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.QueryHint;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPurchaseDao implements PurchaseDao {

	EntityManagerFactory emf = null;
	EntityManager em = null;
	public final Logger LOGGER = LogManager.getLogger(JpaPurchaseDao.class);
	@Override
	public void savePurchase(PurchaseDto order) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			em.persist(order);
			
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
	public List<PurchaseDto> getPurchasesByUserId(int userId) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<PurchaseDto> query = em.createQuery("SELECT p FROM purchase p WHERE p.userDto.id = :id", PurchaseDto.class);
			query.setParameter("id", userId);
			
			List<PurchaseDto> resultList = query.getResultList();
			em.getTransaction().commit();
			
			return resultList;
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
	public List<PurchaseDto> getPurchases() {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			TypedQuery<PurchaseDto> query = em.createQuery("SELECT p FROM purchase p", PurchaseDto.class);
			
			List<PurchaseDto> resultList = query.getResultList();
			em.getTransaction().commit();
			
			return resultList;
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
	public List<PurchaseDto> getNotCompletedPurchases(Integer lastFulfilmentStageId) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em.createQuery("SELECT p.id, p.userDto, p.purchaseStatusDto FROM purchase p WHERE p.purchaseStatusDto.id != :statusId");
			query.setParameter("statusId", lastFulfilmentStageId);
			
			List<Object[]> resultList = query.getResultList();
			List<PurchaseDto> purchases = new ArrayList<>();
			for (Object[] resultTuple : resultList) {
				purchases.add(new PurchaseDto(
						(Integer)resultTuple[0], 
						(UserDto)resultTuple[1], 
						(PurchaseStatusDto)resultTuple[2]));
			}
			
			em.getTransaction().commit();
			
			return purchases;
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
	public PurchaseDto getPurchaseById(Integer purchaseId) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<PurchaseDto> criteriaQuery = criteriaBuilder.createQuery(PurchaseDto.class);
			Root<PurchaseDto> purchaseRoot = criteriaQuery.from(PurchaseDto.class);
			purchaseRoot.fetch("userDto");
			purchaseRoot.fetch("productDtos");
			Query query = em.createQuery(criteriaQuery.select(purchaseRoot).where(criteriaBuilder.equal(purchaseRoot.get("id"), purchaseId)));
			
			PurchaseDto purchase = (PurchaseDto)query.getSingleResult();
			em.getTransaction().commit();
			
			return purchase;
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
	public void updatePurchase(PurchaseDto newPurchase) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(newPurchase);
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
	
//	public List<ProductDto> productsUserPurchased(UserDto userDto)
//	{
//		emf = Persistence.createEntityManagerFactory("persistence-unit");
//		em = emf.createEntityManager();
//		em.getTransaction().begin();
//		
//		Query query = em.createQuery(
//				"SELECT distinct pp.product_id  "
//				+ "FROM purchase_product pp "
//				+ "JOIN purchase p "
//				+ "ON pp.purchase_id = p.id "
//				+ "WHERE p.fk_purchase_user = :user_id");
//		
//		query.setParameter("user_id", userDto.getId());
//		
//		List<Object[]> resultList = query.getResultList();
//		List<ProductDto> productsDto = new ArrayList<>();
//		for (Object[] resultTuple : resultList) {
//			productsDto.add(  em.find(ProductDto.class, resultTuple));
//			LOGGER.info("productsUserPurchased = " + resultTuple, "user = " + userDto.getId());
//		}
//		
//		em.getTransaction().commit();
//		
//		return productsDto;
//
//	}
	
	

}

