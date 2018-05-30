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

import com.apps.pu.hibah.entity.Barang;

@Repository
public class BarangDao {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<Barang> findBarangBykodeAndName(String kode, String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Barang> q = cb.createQuery(Barang.class);
		
		Root<Barang> rootbarang = q.from(Barang.class);
		q.select(rootbarang);
		
		Expression<String> kodeExp = rootbarang.get("kodeBarang");
		Expression<String> namaExp = rootbarang.get("namaBarang");
		
		q.where(
				cb.and(
						cb.like(cb.upper(kodeExp), "%"+kode.toUpperCase()+"%"),
						cb.like(cb.upper(namaExp), "%"+name.toUpperCase()+"%")
				)
			);
		
		
		try{
			List<Barang> result = em.createQuery(q).getResultList();
			return result;
		}catch (NoResultException e) {
			return null;
		}	
	}

	@Transactional
	public Barang delete(Barang barang) {		
		barang = em.merge(barang);
		em.remove(barang);
		em.flush();
		
		return barang;	
		
	}

	@Transactional
	public Barang save(Barang barang) {
		if(barang.getIdBarang() != null) {
			em.merge(barang);
		}else {
			em.persist(barang);
		}
		
		em.flush();
		
		return barang;
		
	}

}
