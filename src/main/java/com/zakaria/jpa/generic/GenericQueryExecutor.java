package com.zakaria.jpa.generic;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

/**
 * @author zakaria
 * 
 * E for Entity Class
 * S for Selector Class
 */

public class GenericQueryExecutor<E, S> {
	private static final String LIKEQ = "_LIKEQ";

	private boolean isCountPredicate;
    private Set<String> ignoreFields;
    private Map<String, Object> params;
	
    private final Root<E> countRoot;
    private final Root<E> entityRoot;
    private final Class<E> entityClass;
    private final Class<S> selectorClass;
    
    private List<Predicate> countQueryPredicates;
    private final List<Predicate> queryPredicates;
    
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;    
    
    private final CriteriaQuery<S> criteriaQuery;
    private final CriteriaQuery<Long> countCriteriaQuery;
    
    private GenericQueryExecutor(Class<E> entityClass, Class<S> selectorClass) {
    	this.entityClass = entityClass;
    	this.selectorClass = selectorClass;
    	this.queryPredicates = new ArrayList<>();
    	
    	this.entityManager = SpringContext.getBean(EntityManager.class);
    	this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
    	
    	this.criteriaQuery = this.criteriaBuilder.createQuery(selectorClass);
    	this.entityRoot = this.criteriaQuery.from(entityClass);
    	
    	this.countCriteriaQuery = this.criteriaBuilder.createQuery(Long.class);
    	this.countRoot = this.countCriteriaQuery.from(entityClass);
    }
    
	public static <E, T> GenericQueryExecutor<E, T> builder(Class<E> entityClass, Class<T> selectorClass) {
    	if (entityClass == null) {
    		throw new NullPointerException("entityClass can not be null");
    	} else if (selectorClass == null) {
    		throw new NullPointerException("selectorClass can not be null");
    	}
    	
        return new GenericQueryExecutor<>(entityClass, selectorClass);
    }
    
    public GenericQueryExecutor<E, S> buildEntityPredicates(Map<String, Object> params) {
    	this.params = params;
    	
    	removePaginationParams();
    	buildEntityQueryPredicates();
    	return this;
    }

    public GenericQueryExecutor<E, S> buildEntityPredicates(Map<String, Object> params, Set<String> ignoreFields) {
    	this.params = params;
    	this.ignoreFields = ignoreFields;
    	
    	removePaginationParams();
    	buildEntityQueryPredicates();
        return this;
    }       
    
    public GenericQueryExecutor<E, S> addPredicate(Predicate predicate) {
    	if (predicate != null) {
    		this.queryPredicates.add(predicate);
    	}
    	
    	return this;
    }
    
    public GenericQueryExecutor<E, S> addPredicates(List<Predicate> predicates) {
    	if (predicates != null && !predicates.isEmpty()) {
    		this.queryPredicates.addAll(predicates);
        }
    	
    	return this;
    }
    
    public GenericQueryExecutor<E, S> clearPredicates() {
    	if (!this.queryPredicates.isEmpty()) {
    		this.queryPredicates.clear();
    	}
    	
    	return this;
    }
    
    public List<S> findAll() {
    	buildSelectorQuery();
        return this.entityManager.createQuery(this.criteriaQuery).getResultList();
    }
    
    public Page<S> findAll(int pageNo, int pageSize, String sortBy, boolean isDesc) {
    	final Pageable pageable;

		if(sortBy != null && !sortBy.isEmpty()) {
			final Sort sort = Sort.by(sortBy);
			pageable = PageRequest.of(pageNo, pageSize, isDesc ? sort.descending() : sort.ascending());
		} else {
			pageable = PageRequest.of(pageNo, pageSize);
		}
		
		return findAll(pageable);
    }
    
    public Page<S> findAll(Pageable pageable) {
    	if (pageable == null) {
    		throw new NullPointerException("pageable can not be null");
    	}
    	
        if (pageable.getSort() != null && pageable.getSort().isSorted()) {
			final List<Order> orders = pageable.getSort().get().map(order -> {
				final Path<E> path = this.entityRoot.get(order.getProperty());
				return order.isAscending() ? this.criteriaBuilder.asc(path) : this.criteriaBuilder.desc(path);
			}).collect(Collectors.toList());
			
            this.criteriaQuery.orderBy(orders);
        }
        
        buildSelectorQuery(); // build root selector query
    	
        final List<S> results = this.entityManager.createQuery(this.criteriaQuery)
        	.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
        	.setMaxResults(pageable.getPageSize())
        	.getResultList();
        
        this.isCountPredicate = true;
        buildSelectorQuery(); // build count selector query
    	
    	long totalCount = this.entityManager.createQuery(this.countCriteriaQuery).getSingleResult();
        return new PageImpl<>(results, pageable, totalCount);
    }
    
    public Optional<S> findOne(Object primaryKey) {
    	if (primaryKey == null) {
    		throw new NullPointerException("primaryKey can't be null");
    	}
    	
    	String primaryKeyName = null;
    	
    	for (Field field: this.entityClass.getDeclaredFields()) {
    		if (field.isAnnotationPresent(Id.class)) {
    			primaryKeyName = field.getName();
                break;
            }
    	}
    	
    	if (primaryKeyName == null) {
    		throw new NoSuchElementException("No primaryKey (@Id) found in " + this.entityClass.getSimpleName() + " entity");
    	}
    	
        try {
        	addQueryPredicate(this.criteriaBuilder.equal(this.entityRoot.get(primaryKeyName), primaryKey));
        	return buildSelectorSingResult();
        } catch (Exception e) {
        	if (e instanceof NoResultException) {
        		return Optional.empty();
        	}
        	
        	throw e;
		}
    }
    
    public Optional<S> findOneByCompositKey(Object compositPrimaryKey) {
    	if (compositPrimaryKey == null) {
    		throw new NullPointerException("compositPrimaryKey can't be null");
    	}
    	
    	 try {
    		 final Map<Path<?>, Object> fieldMap = new HashMap<>();
    		 
    		 if (this.entityClass.isAnnotationPresent(IdClass.class)) {
    			 for (Field field : compositPrimaryKey.getClass().getDeclaredFields()) {
      				field.setAccessible(true);
      				fieldMap.put(this.entityRoot.get(field.getName()), field.get(compositPrimaryKey));
      	 	     }
    		 } else {
    			 for (Field field: this.entityClass.getDeclaredFields()) {
        			 if (field.isAnnotationPresent(EmbeddedId.class)) {
        				 
        				 for (Field embeddedField: field.getType().getDeclaredFields()) {
        					 embeddedField.setAccessible(true);
        					 fieldMap.put(this.entityRoot.get(field.getName()).get(embeddedField.getName()), embeddedField.get(compositPrimaryKey));
        				 }
        				 
        				 break;
        			 }
        		 }
    		 }
    		 
    		 if (fieldMap.isEmpty()) {
    			 throw new NoSuchElementException("No primaryKey (@Id or @EmbeddedId) found in " + this.entityClass.getSimpleName() + " entity");
    		 }
    		 
    		 fieldMap.forEach((key, value) -> addQueryPredicate(this.criteriaBuilder.equal(key, value)));
    		 return buildSelectorSingResult();
    		
         } catch (Exception e) {
         	if (e instanceof NoResultException) {
         		return Optional.empty();
         	}
         	
         	throw new RuntimeException(e);
 		}
    }
    
    private void buildSelectorQuery() {
    	if (this.isCountPredicate) {
    		
    		buildEntityQueryPredicates(); // build count predicates 
        	this.countCriteriaQuery.select(criteriaBuilder.count(countRoot));
        	
        	if (this.countQueryPredicates != null && !this.countQueryPredicates.isEmpty()) {
        		this.countCriteriaQuery.where(this.criteriaBuilder.and(this.countQueryPredicates.toArray(new Predicate[0])));
        	}
        	
    		return;
    	}
    	
    	final List<Selection<?>> selections = Arrays.asList(this.selectorClass.getDeclaredFields()).stream()
                .map(field -> this.entityRoot.get(field.getName()).alias(field.getName()))
                .collect(Collectors.toList());
        
        if (selections.isEmpty()) {
            throw new NoSuchElementException("The selectClass has no properties");
        }
        
        this.criteriaQuery.multiselect(selections);
        if (!this.queryPredicates.isEmpty()) {
        	this.criteriaQuery.where(this.criteriaBuilder.and(this.queryPredicates.toArray(new Predicate[0])));
        }
    }
    
    private Optional<S> buildSelectorSingResult() {
    	buildSelectorQuery();
     	return Optional.of(this.entityManager.createQuery(this.criteriaQuery).setMaxResults(1).getSingleResult());
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void buildEntityQueryPredicates() {
    	if (isParamsEmpty()) {
    		return;
    	}

		final Root<E> root =  this.isCountPredicate ? this.countRoot : this.entityRoot;
    	
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String filterBy = entry.getKey();
			final String filterWith = entry.getValue().toString();

			if (filterBy != null && filterBy.contains(LIKEQ)) {
				filterBy = filterBy.replaceAll(LIKEQ + "$", "");
			}

			if (filterBy == null || filterBy.isEmpty() || filterWith == null || filterWith.isEmpty()) {
				continue;
			}

			if (ignoreFields != null && ignoreFields.contains(filterBy)) {
				continue;
			}

            final Path<E> path = root.get(filterBy);
			final Class<?> type = this.isCountPredicate ? this.countRoot.get(filterBy).getJavaType() : this.entityRoot.get(filterBy).getJavaType();

			if (Number.class.isAssignableFrom(type) || type.isEnum() || type.equals(Boolean.class) || type.equals(UUID.class)) {
				final Object filterWithValue;
				
				if (type.isEnum()) {
					filterWithValue = UUID.fromString(filterWith);
				} else if (type.equals(Boolean.class)) {
					filterWithValue = Boolean.valueOf(filterWith);
				} else if (type.equals(UUID.class)) {
					filterWithValue = Enum.valueOf((Class<Enum>) type, filterWith);
				} else {
					filterWithValue = filterWith;
				}
				
				addQueryPredicate(this.criteriaBuilder.equal(path, filterWithValue));
				
			} else if (type.equals(String.class)) {
				if (entry.getKey().contains(LIKEQ)) {
					addQueryPredicate(this.criteriaBuilder.like(this.criteriaBuilder.upper(root.get(filterBy)), "%" + filterWith.toUpperCase() + "%"));
				} else {
					addQueryPredicate(this.criteriaBuilder.equal(root.get(filterBy), filterWith));
				}
			} else if (type.equals(LocalDateTime.class)) {
				final LocalDate localDate = LocalDate.parse(filterWith);
				addQueryPredicate(this.criteriaBuilder.between(this.entityRoot.get(filterBy), LocalDateTime.of(localDate, LocalTime.MIN), LocalDateTime.of(localDate, LocalTime.MAX)));
			}
		}
    }
    
    public Root<E> root() {
    	return this.entityRoot;
    }
    
    public CriteriaBuilder criteriaBuilder() {
    	return this.criteriaBuilder;
    }

    public static String getLIKEQ() {
		return LIKEQ;
	}
    
    private boolean isParamsEmpty() {
    	return this.params == null || this.params.isEmpty();
    }
    
    private void removePaginationParams() {
    	if (!isParamsEmpty()) {
    		this.params.remove(AppConstants.PAGE_NO);
    		this.params.remove(AppConstants.PAGE_SIZE);
    		this.params.remove(AppConstants.SORT_BY);
    		this.params.remove(AppConstants.IS_DESC);
    	}
    }
    
    private void addQueryPredicate(Predicate predicate) {
    	if (!this.isCountPredicate) {
    		this.queryPredicates.add(predicate);
    		return;
    	}
    	
    	if (this.countQueryPredicates == null) {
			this.countQueryPredicates = new ArrayList<>();
		}
		
		this.countQueryPredicates.add(predicate);
    }
}

