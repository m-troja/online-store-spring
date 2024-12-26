package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.itbulls.learnit.onlinestore.persistence.dao.CategoryDao;
import com.itbulls.learnit.onlinestore.persistence.dto.CategoryDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class JpaCategoryDao implements CategoryDao 
{
	public final Logger LOGGER = LogManager.getLogger(JpaCategoryDao.class);
	EntityManagerFactory emf = null;
	EntityManager em = null;
	
	@Override
	public CategoryDto getCategoryByCategoryId(int id) {
		try 
		{ 	 
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
		
			em.getTransaction().begin();
			
			CategoryDto category = em.find(CategoryDto.class, id);
			LOGGER.info("JpaCategoryDao get category by id " + id);
			em.getTransaction().commit();
			return category;
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
	public List<CategoryDto> getCategories() 
	{
		try 
		{ 
			var emf = Persistence.createEntityManagerFactory("persistence-unit");
			var em = emf.createEntityManager();
			em.getTransaction().begin();
			List<CategoryDto> categories = em.createQuery("SELECT c FROM category c", CategoryDto.class).getResultList();
			LOGGER.info("Get categories SELECT c FROM category c");
			em.getTransaction().commit();
			return categories;
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
