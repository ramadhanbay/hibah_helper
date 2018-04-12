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

import com.apps.pu.hibah.entity.Direktorat;


@Repository
public class DirektoratDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Direktorat> getDirektoratLikeName(String direktoratName) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Direktorat> q = cb.createQuery(Direktorat.class);
		Root<Direktorat> direktoratRoot = q.from(Direktorat.class);
		q.select(direktoratRoot);
		
		Expression<String> path = direktoratRoot.get("name");
		
		q.where(cb.like(cb.upper(path), "%"+direktoratName.toUpperCase()+"%"));
		
		try{
			List<Direktorat> result = em.createQuery(q).getResultList();
			return result;
		}catch (NoResultException e) {
			return null;
		}		
		
	}

}
