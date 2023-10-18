package com.zakaria.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zakaria.jpa.dtos.UserProjection;
import com.zakaria.jpa.entities.User;
import com.zakaria.jpa.generic.GenericRepository;

@Repository
public interface UserRepository extends GenericRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	Optional<User> findTopById(int id);
	
	@Query("SELECT NEW com.zakaria.jpa.dtos.UserProjection(u.email) FROM User u WHERE u.id = :id")
    UserProjection findById(@Param("id") int id);
	
	@EntityGraph(attributePaths = "roles")
	Optional<User> findUserTopById(int id);
	
	@Query(value = "select * from users", nativeQuery = true)
	List<User> findAllUsers();
	
	@Query("SELECT NEW com.zakaria.jpa.dtos.UserProjection(u.id, u.email) FROM User u")
	List<UserProjection> findAllUserDtos();
	
//	List<UserDto> findAllById(int id);
	
//	<T> List<T> findAllById(int id, Class<T> type);
	
//	@Query("SELECT NEW com.zakaria.jpa.dtos.UserProjection(u.id, u.email) FROM User u WHERE :filterConditions")
//    List<UserProjection> findByCustomSpecification(@Param("filterConditions") Specification<User> specification);
	
//	<T> List<T> findAll(Specification<T> spec, Class<T> type);
	
//	<T> List<T> findAllByCustomQuery(Specification<T> specification);
	
//	List<UserProjection> findAllByCustomQuery(Specification<UserProjection> specification);
}