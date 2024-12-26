package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import java.util.List;

import com.itbulls.learnit.onlinestore.persistence.dao.ProductDao;
import com.itbulls.learnit.onlinestore.persistence.dto.ProductDto;

import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class JpaProductDao implements ProductDao {
	Logger LOGGER = LogManager.getLogger(JpaProductDao.class);
	EntityManagerFactory emf = null;
	EntityManager em = null;
	
	@Override
	public List<ProductDto> getProducts() {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			List<ProductDto> products = em.createQuery("SELECT p FROM product p", ProductDto.class).getResultList();
			LOGGER.info(" JpaProductDao get products SELECT p FROM product p");
			em.getTransaction().commit();
			return products;
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
	public ProductDto getProductById(int productId) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			ProductDto product = em.find(ProductDto.class, productId);
			LOGGER.info(" JpaProductDao getProductById " + productId);
			em.getTransaction().commit();
			return product;
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
	public List<ProductDto> getProductsLikeName(String searchQuery) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Query query = em.createNativeQuery("SELECT * FROM learn_it_db.product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',?1,'%')", ProductDto.class);
			query.setParameter(1, searchQuery);
			LOGGER.info(" JpaProductDao getProductsLikeName SELECT * FROM learn_it_db.product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%', " + searchQuery + " ,'%')" );

			List<ProductDto> resultList = query.getResultList();
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
	public List<ProductDto> getProductsByCategoryId(Integer id) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ProductDto> query = em.createQuery("SELECT p FROM product p WHERE p.categoryDto.id = :id", ProductDto.class);
			query.setParameter("id", id);
			LOGGER.info("SELECT p FROM product p WHERE p.categoryDto.id = " + id);
			List<ProductDto> resultList = query.getResultList();
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
	public List<ProductDto> getProductsByCategoryIdPaginationLimit(Integer categoryId, Integer page,
			Integer paginationLimit) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ProductDto> query = em.createQuery("SELECT p FROM product p WHERE p.categoryDto.id = :id", ProductDto.class);
			query.setParameter("id", categoryId);
			
			query.setFirstResult((page - 1) * paginationLimit); 
			query.setMaxResults(paginationLimit);
			LOGGER.info("SELECT p FROM product p WHERE p.categoryDto.id = " + categoryId + "page = " + page + ", limit = " + paginationLimit);
			List<ProductDto> resultList = query.getResultList();
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
	public Integer getProductCountForCategory(Integer categoryId) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM product p WHERE p.categoryDto.id = :id", Long.class);
			query.setParameter("id", categoryId);
			LOGGER.info("SELECT COUNT(p) FROM product p WHERE p.categoryDto.id = " + categoryId);
			Long count = query.getSingleResult();
			em.getTransaction().commit();
			return count.intValue();
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
	public Integer getProductCountForSearch(String searchQuery) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT COUNT(*) FROM product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',:searchQuery,'%'))", Integer.class);
			query.setParameter("searchQuery", searchQuery);
			LOGGER.info("SESELECT COUNT(*) FROM product WHERE UPPER(product_name) LIKE UPPER(CONCAT('%', " + searchQuery + " ,'%'))");
			Integer count = (Integer)query.getSingleResult();
			em.getTransaction().commit();
			return count;
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
	public List<ProductDto> getProductsLikeNameForPageWithLimit(String searchQuery, Integer page,
			Integer paginationLimit) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
					
			
			Query query = em.createNativeQuery("SELECT p.id, p.guid, p.product_name, p.description, p.price, p.category_id, p.img_name, c.id as cat_id, c.category_name, c.img_name as cat_img "
					+ "FROM learn_it_db.product p JOIN category c ON p.category_id = c.id "
					+ "WHERE UPPER(product_name) LIKE UPPER(CONCAT('%',:searchQuery,'%')) LIMIT :offset, :limit", 
					ProductDto.class);
			query.setParameter("searchQuery", searchQuery);
			query.setParameter("offset", (page - 1) * paginationLimit);
			query.setParameter("limit", paginationLimit);
			LOGGER.info("SELECT p.id, p.guid, p.product_name, p.description, p.price, p.category_id, p.img_name, c.id as cat_id, c.category_name, c.img_name as cat_img FROM learn_it_db.product p JOIN category c ON p.category_id = c.id WHERE UPPER(product_name) LIKE UPPER(CONCAT('%', " + searchQuery + ",'%')) page = " + page + " limit = " + paginationLimit + " );");
			List<ProductDto> resultList = query.getResultList();
			
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
	public ProductDto getProductByGuid(String guid) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ProductDto> query = em.createQuery("SELECT p FROM product p WHERE p.guid = :guid", ProductDto.class);
			query.setParameter("guid", guid);
			LOGGER.info("SELECT p FROM product p WHERE p.guid = " + guid);
			ProductDto product = query.getSingleResult();
			em.getTransaction().commit();
			return product;
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
