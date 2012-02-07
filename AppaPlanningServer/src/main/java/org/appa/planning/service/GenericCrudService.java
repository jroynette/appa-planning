package org.appa.planning.service;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

public class GenericCrudService {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> T findByPK(Class<T> type, Object pk) {
		return this.entityManager.find(type, pk);
	}

	public <T> T find(Class<T> type, Object pk, Map<String, Object> args) {
		return this.entityManager.find(type, pk, args);
	}

	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2,
			Map<String, Object> arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void save(Object o) {
		this.entityManager.persist(o);
	}

	@Transactional
	public void save(Collection<Object> list) {
		for (Object o : list) {
			this.entityManager.persist(o);
		}
	}

	@Transactional
	public void delete(Object o) {
		this.entityManager.remove(o);
	}

	@Transactional
	public void delete(Collection<Object> list) {
		for (Object o : list) {
			this.entityManager.remove(o);
		}
	}
}
