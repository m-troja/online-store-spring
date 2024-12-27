package com.itbulls.learnit.onlinestore.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.itbulls.learnit.onlinestore.persistence.dao.RoleDao;
import com.itbulls.learnit.onlinestore.persistence.dto.PurchaseStatusDto;
import com.itbulls.learnit.onlinestore.persistence.dto.RoleDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Repository
public class JpaRoleDao implements RoleDao {

	@Override
	public RoleDto getRoleById(int id) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {

			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			RoleDto role = em.find(RoleDto.class, id);
			em.getTransaction().commit();
			return role;
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}

	}

	@Override
	public RoleDto getRoleByRoleName(String roleName) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<RoleDto> query = em.createQuery("SELECT r FROM role r WHERE r.roleName = :roleName", RoleDto.class);
			query.setParameter("roleName", roleName);
			RoleDto roleDto = query.getResultList().stream().findFirst().orElse(null);
			em.getTransaction().commit();
			System.out.println(" ");
			System.out.println("JpaRoleDao getRoleByRoleName roleDto.toString: " + roleDto.toString());
			System.out.println(" ");
			return roleDto;
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void save(RoleDto role) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(role);
			em.getTransaction().commit();
			System.out.println("");
			System.out.println("JpaRoleDao save, Role saved: " + role.toString());
			System.out.println("");
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

}