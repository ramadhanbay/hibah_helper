package com.apps.pu.hibah.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.JoinType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.entity.Direktorat;
import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.ui.KeyValue;

@Repository
public class ReportDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<KeyValue> getSatkerByNamePaging(String direktoratName, String SatkerName, int limit, int offset){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KeyValue> q = cb.createQuery(KeyValue.class);
		Root<Direktorat> dirRoot = q.from(Direktorat.class);
		Join<Direktorat, Satker> dirJoin = dirRoot.join("satkers" , JoinType.INNER);
		
		q.multiselect(dirJoin.get("idSatker"),dirRoot.get("name"),dirJoin.get("name"));
		
		Expression<String> dirName = dirRoot.get("name");
		Expression<String> satkerName = dirJoin.get("name");
		
		q.where(
				cb.and(
						cb.like(cb.upper(dirName), direktoratName.toUpperCase()),
						cb.like(cb.upper(satkerName), SatkerName.toUpperCase())
						)
				);
		
		TypedQuery<KeyValue> tq = em.createQuery(q);
		tq.setFirstResult(offset);
		tq.setMaxResults(limit);
		
		try{
			List<KeyValue> result = tq.getResultList();
			return result;
		}catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public int countSatkerByNamePaging(String direktoratName, String SatkerName){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KeyValue> q = cb.createQuery(KeyValue.class);
		Root<Direktorat> dirRoot = q.from(Direktorat.class);
		Join<Direktorat, Satker> dirJoin = dirRoot.join("satkers" , JoinType.INNER);
		
		q.multiselect(dirJoin.get("idSatker"),dirRoot.get("name"),dirJoin.get("name"));
		
		Expression<String> dirName = dirRoot.get("name");
		Expression<String> satkerName = dirJoin.get("name");
		
		q.where(
				cb.and(
						cb.like(cb.upper(dirName), direktoratName.toUpperCase()),
						cb.like(cb.upper(satkerName), SatkerName.toUpperCase())
						)
				);
		
		
		try{
			List<KeyValue> result = em.createQuery(q).getResultList();
			return result.size();
		}catch (NoResultException e) {
			return 0;
		}
	}

}
