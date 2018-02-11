package com.apps.pu.hibah.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.entity.Roles;

@Repository
public class RoleDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Roles> getRolesLikeName(String roleName) {
		
		
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Roles> q = cb.createQuery(Roles.class);
		Root<Roles> roleRoot = q.from(Roles.class);
		q.select(roleRoot);
		
		Expression<String> path = roleRoot.get("roleName");
		
		q.where(cb.like(cb.upper(path), "%"+roleName.toUpperCase()+"%"));
		
		try{
			List<Roles> result = em.createQuery(q).getResultList();
			return result;
		}catch (NoResultException e) {
			return null;
		}		
	}

}
