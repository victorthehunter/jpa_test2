package com.zakaria.jpa.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zakaria.jpa.entities.User;
import com.zakaria.jpa.generic.GenericQueryExecutor;
import com.zakaria.jpa.generic.GenericService;
import com.zakaria.jpa.repositories.UserRepository;

import jakarta.persistence.EntityManager;

@Service
public class UserService extends GenericService<User, Integer> {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	UserRepository userRepository;
	
	public Optional<User> findTopById(int id) {
		var criteria = entityManager.getCriteriaBuilder();
		var query = criteria.createQuery(User.class);
		var root = query.from(User.class);
		
		query.where(criteria.equal(root.get("id"), id));
		query.select(root);
		
		var typedQuery = entityManager.createQuery(query);
		typedQuery.setMaxResults(1);
		return typedQuery.getResultList().stream().findFirst();
	}
	
	public User find2TopById(int id) {
//		var maps = new HashMap<>() {{
//			put("id", id)
//		}};
		
		return entityManager.find(User.class, id);
		
//		var criteria = entityManager.getCriteriaBuilder();
//		var query = criteria.createQuery(User.class);
//		var root = query.from(User.class);
//		
//		query.where(criteria.equal(root.get("id"), id));
//		query.select(root);
//		
//		var typedQuery = entityManager.createQuery(query);
//		typedQuery.setMaxResults(1);
//		return typedQuery.getResultList().stream().findFirst();
	}

	@Override
	public Page<User> findPageableFilter(Map<String, Object> params, Pageable pageable) {
		return GenericQueryExecutor
				.builder(User.class, User.class)
				.buildEntityPredicates(params)
				.findAll(pageable);
	}
	
//	@Override
//	public Page<User> findAllPageable(Map<String, Object> params, Pageable pageable) {
//		return GenericQueryExecutor
//				.builder(User.class, UserProjection.class)
//				.buildEntityPredicates(params)
//				.findAll(pageable)
//				.map(data -> {
//					User user = new User();
//					user.setId(data.getId());
//					user.setEmail(data.getEmail());
//					return user;
//				});
//	}

}
