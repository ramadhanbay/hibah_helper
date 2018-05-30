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

import com.apps.pu.hibah.entity.Satker;

@Repository
public class SatkerDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<Satker> getSatuanKerjaByDirektorat(Integer idDirektorat, String satker){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Satker> q = cb.createQuery(Satker.class);
		
		Root<Satker> satkerRoot = q.from(Satker.class);
		q.select(satkerRoot);
		
		Expression<String> name = satkerRoot.get("name");
		Expression<Integer> dir = satkerRoot.get("direktorat");
		
		q.where(
				cb.and(
						cb.like(cb.upper(name), "%"+satker.toUpperCase()+"%"),
						cb.or(cb.equal(dir, idDirektorat), cb.equal(cb.nullLiteral(Integer.class), idDirektorat))
				)
			);
		
		
		try{
			List<Satker> result = em.createQuery(q).getResultList();
			return result;
		}catch (NoResultException e) {
			return null;
		}	
	}
	
	@Transactional
	public Satker save(Satker satker) {
		
		if(satker.getIdSatker() != null) {
			em.merge(satker);
		}else {
			em.persist(satker);
		}
		
		em.flush();
		
		return satker;
		
	}
	
	@Transactional
	public Satker delete(Satker satker) {
		
		satker = em.merge(satker);
		em.remove(satker);
		em.flush();
		
		return satker;		
	}

}
