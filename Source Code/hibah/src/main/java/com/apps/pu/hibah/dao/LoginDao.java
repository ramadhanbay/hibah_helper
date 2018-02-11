package com.apps.pu.hibah.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.entity.Roles;
import com.apps.pu.hibah.entity.Users;

@Repository
public class LoginDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Users getUser(String uname, String pwd) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Users> q = cb.createQuery(Users.class);
		Root<Users> userRoot = q.from(Users.class);
		q.select(userRoot);
		q.where(cb.and(
					cb.equal(userRoot.get("username"), uname),
					cb.equal(userRoot.get("password"), pwd)
				));
		
		try{
			Users result = em.createQuery(q).getSingleResult();
			return result;
		}catch (NoResultException e) {
			return null;
		}		
		
	}

	public List<Roles> getRolesByUsername(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
}
