package com.zakaria.jpa.controllers;

import static com.zakaria.jpa.generic.AppConstants.IS_DESC;
import static com.zakaria.jpa.generic.AppConstants.PAGE_NO;
import static com.zakaria.jpa.generic.AppConstants.PAGE_SIZE;
import static com.zakaria.jpa.generic.AppConstants.SORT_BY;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zakaria.jpa.dtos.UserRecord;
import com.zakaria.jpa.entities.User;
import com.zakaria.jpa.generic.GenericController;
import com.zakaria.jpa.generic.GenericQueryExecutor2;
import com.zakaria.jpa.generic.SpringContext;
import com.zakaria.jpa.repositories.UserRepository;
import com.zakaria.jpa.services.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController<User, Integer> {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@GetMapping("/uid{id}")
	public Object uid(@PathVariable("id") int id) {
//		userRepository.findAll

		var users = userRepository.findById(id, UserRecord.class);
//		var users = userRepository.findAllById(id, UserProjection.class);
		return users.get();
	}

	@GetMapping("/all2")
	public Object getUsers2() {
//		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles", User.class);
//	    TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
//		List<User> users = query.getResultList();
//		return users;

//		var criteria = entityManager.getCriteriaBuilder();
//		var query = criteria.createQuery(User.class);
//		var root = query.from(User.class);
//
//		query.select(root);
//	
//		var typedQuery = entityManager.createQuery(query);
//		return typedQuery.getResultList();
		
		return userRepository.findAll();
	}
	
//	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200")
//	@ApiImplicitParams({ @ApiImplicitParam(name = "params") })
	
	@GetMapping(value = "/allpp")
	public Object getAllPaginatedUsers(
			@RequestParam(name = PAGE_NO, defaultValue = "0") int pageNo,
			@RequestParam(name = PAGE_SIZE, defaultValue = "20") int pageSize,
			@RequestParam(name = SORT_BY, defaultValue = "") String sortBy,
			@RequestParam(name = IS_DESC, defaultValue = "false") boolean isDesc,
			@RequestParam(defaultValue = "{}") Map<String, Object> params
	) {
//		var data = userService.findPageableFilter(parameters, pageable);
//		return data;
		
//		return userRepository.findAll(Specification.where((root, query, criteriaBuilder) -> {
//			return criteriaBuilder.equal(root.get("isOk"), Boolean.valueOf(parameters.get("isOk").toString()));
//		}), pageable);
//		System.out.println();
//		
		System.out.println(params);
		
//		Pageable pageable = getPageable(pageNo, pageSize, sortBy, isDesc);
		
//		this.criteriaBuilder.equal(root.get(filterBy), filterWith);
		
//		String environment = System.getenv("ENVIRONMENT");
        String environment = SpringContext.getBean(Environment.class).getProperty("spring.profiles.active");

		System.out.println("environment: " + environment + "\n\n\n\n\n");
		
		var ss = GenericQueryExecutor2
				.builder(User.class, User.class)
				.enableFieldNotFoundException(false);
//				.buildEntityPredicates(params);
//				.findAll(pageNo, pageSize, sortBy, isDesc);
//				.findOne(params.get("id"));
//				.findAll(pageable);
		
//		ss.criteriaBuilder()::like
		
//		Join<LocalizedText, Translation> translationJoin = localizedTextJoin.join("translations");
//		predicates.add(criteriaBuilder.like(criteriaBuilder.upper(translationJoin.get("translatedText")), "%" +  filterWith.toUpperCase() + "%"));
  
//		Join<User, Role> roleJoin = ss.root().join("role");
//		ss.addPredicate(ss.criteriaBuilder().equal(roleJoin.get("id"), 2));
		
		if (params.containsKey("roles")) {
//			ss.addJoinToOnePredicateLike("roles", "name", params.get("role"));
			ss.addJoinToManyPredicate("roles", "name", params.get("roles"), JoinType.INNER);
		}
		
		ss.buildEntityPredicates(params);
		
//		return ss.findAll();
		return ss.findAll(pageNo, pageSize, sortBy, isDesc);
//		return ss;
		
//		return userRepository.findAll(pageable);
	}
}
