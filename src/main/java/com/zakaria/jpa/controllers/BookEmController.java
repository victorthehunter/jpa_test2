package com.zakaria.jpa.controllers;

import static com.zakaria.jpa.generic.AppConstants.IS_DESC;
import static com.zakaria.jpa.generic.AppConstants.PAGE_NO;
import static com.zakaria.jpa.generic.AppConstants.PAGE_SIZE;
import static com.zakaria.jpa.generic.AppConstants.SORT_BY;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zakaria.jpa.dtos.UserRecord;
import com.zakaria.jpa.entities.BookEm;
import com.zakaria.jpa.entities.BookPkEm;
import com.zakaria.jpa.entities.User;
import com.zakaria.jpa.generic.GenericQueryExecutor;
import com.zakaria.jpa.generic.GenericQueryExecutor2;
import com.zakaria.jpa.repositories.BookEmRepository;
import com.zakaria.jpa.repositories.UserRepository;
import com.zakaria.jpa.services.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.Explode;
import jakarta.persistence.EntityManager;

@RestController
@RequestMapping("/bookEm")
public class BookEmController {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookEmRepository bookEmRepository;

	@GetMapping("/uid{userId}/{companyId}")
	public Object uid(@PathVariable("userId") long userId, @PathVariable("companyId") long companyId) {
//		userRepository.findAll

//		var users = userRepository.findById(id, UserRecord.class);
////		var users = userRepository.findAllById(id, UserProjection.class);
//		return users.get();
		
		BookPkEm bookPkEm = new BookPkEm(userId, companyId);
		
		 return GenericQueryExecutor2
			.builder(BookEm.class, BookEm.class)
			.findOneByCompositKey(bookPkEm);
//		 .findOneByCompositKey(bookPkEm);
	}

	@GetMapping("/all2")
	public Object getUsers2() {
//		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles", User.class);
//	    TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
//		List<User> users = query.getResultList();
//		return users;

		var criteria = entityManager.getCriteriaBuilder();
		var query = criteria.createQuery(User.class);
		var root = query.from(User.class);

		query.select(root);
	
		var typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}
	
//	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200")
//	@ApiImplicitParams({ @ApiImplicitParam(name = "params") })
	
    @Parameters({
    	@Parameter(name = "params", explode =  Explode.TRUE) 
    })
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
		
		var ss = GenericQueryExecutor
				.builder(User.class, UserRecord.class)
				.buildEntityPredicates(params)
				.findAll(pageNo, pageSize, sortBy, isDesc);
//				.findOne(params.get("id"));
//				.findAll(pageable);
		
//		ss.addPredicate(ss.criteriaBuilder().equal(ss.root().get("id"), 2));
		
//		return ss.findAll();
		return ss;
		
//		return userRepository.findAll(pageable);
	}
	
	public static Pageable getPageable(int pageNo, int pageSize, String sortBy, boolean isDesc) {
		Pageable pageable;

		if(sortBy != null && sortBy.trim().length() > 0) {
			if (isDesc) {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			} else {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
			}
		} else {
			pageable = PageRequest.of(pageNo, pageSize);
		}

		return pageable;
	}
}
