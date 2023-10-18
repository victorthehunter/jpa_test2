package com.zakaria.jpa.generic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class GenericService<T, ID> {
	
	@Autowired
	protected GenericRepository<T, ID> repository;
	
	public abstract Page<?> findPageableFilter(Map<String, Object> params, Pageable pageable);
	
	public List<T> findAll() {
		return this.repository.findAll();
	}
	
	public List<T> findAllByIds(List<ID> ids) {
		return this.repository.findAllById(ids);
	}
	
	public Page<T> findPageable(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public T save(T entity) {
		return this.repository.save(entity);
	}
	
	public List<T> saveAll(List<T> entities) {
		return this.repository.saveAll(entities);
	}

	public Optional<T> findById(ID id) {
		return this.repository.findById(id);
	}
	
	public long count() {
		return this.repository.count();
	}

	public boolean delete(T entity) {
		try {
			this.repository.delete(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteById(ID id) {
		try {
			this.repository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

