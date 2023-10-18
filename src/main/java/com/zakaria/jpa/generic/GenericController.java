package com.zakaria.jpa.generic;

import java.util.Map;
import java.util.Optional;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zakaria.jpa.dtos.PageData;
import com.zakaria.jpa.entities.User;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public abstract class GenericController<T, ID> {
	
	@Autowired
	protected GenericService<T, ID> genericService;
	
	@GetMapping("/all")
    public ResponseEntity<ResponseBuilder> findAll(){
		return ResponseBuilder.build(true, this.genericService.findAll());
    }

	@PageableAsQueryParam
    @GetMapping("/pageable")
    public ResponseEntity<ResponseBuilder> findPageable(@Parameter(hidden = true) Pageable pageable){
        return ResponseBuilder.build(true, this.genericService.findPageable(pageable));
    }
	
	@ApiResponse(content = @Content(schema = @Schema(implementation = PageData.class)), responseCode = "200")
	@PageableAsQueryParam
    @GetMapping("/pageable-filter")
    public ResponseEntity<Page<User>> findPageableFilter(
    		@RequestParam(required = false) Map<String, Object> params, 
    		@Parameter(hidden = true) Pageable pageable) {
		
		return ResponseEntity.ok(new PageImpl<User>(null));
		
//		return ResponseEntity.ok(this.genericService.findPageableFilter(params, pageable));
		
    }
	
//	@ApiResponse(content = @Content(schema = @Schema(implementation = PageData.class)), responseCode = "200")
//	@PageableAsQueryParam
//    @GetMapping("/pageable-filter")
//    public ResponseEntity<ResponseBuilder> findPageableFilter(
//    		@RequestParam(required = false) Map<String, Object> params, 
//    		@Parameter(hidden = true) Pageable pageable) {
//		
//	    return ResponseBuilder.build(true, this.genericService.findPageableFilter(params, pageable));
//    }
	
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseBuilder> findById(@PathVariable ID id){
    	Optional<T> entity = this.genericService.findById(id);
    	
    	if (entity.isEmpty()) {
    		return ResponseBuilder.build(false);
    	}
    	
    	return ResponseBuilder.build(true, entity.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBuilder> delete(@PathVariable ID id){
    	return ResponseBuilder.build(this.genericService.deleteById(id));
    }
    
	@PutMapping("/update")
	public ResponseEntity<ResponseBuilder> update(@RequestBody T updated) {
		return ResponseBuilder.build(true, this.genericService.save(updated));
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseBuilder> create(@RequestBody T created) {
		return ResponseBuilder.build(true, this.genericService.save(created));
	}
}

//public abstract class GenericController<T, ID> {
//	
//	@Autowired
//	protected GenericService<T, ID> genericService;
//	
//	@GetMapping("/all")
//    public ResponseEntity<List<T>> findAll(){
//        return ResponseEntity.ok(this.genericService.findAll());
//    }
//
//	@PageableAsQueryParam
//    @GetMapping("/pageable")
//    public ResponseEntity<Page<T>> findPageable(@Parameter(hidden = true) Pageable pageable){
//        return ResponseEntity.ok(this.genericService.findPageable(pageable));
//    }
//	
//	@PageableAsQueryParam
//    @GetMapping("/pageable-filter")
//    public ResponseEntity<Page<?>> findPageableFilter(
//    		@RequestParam(required = false) Map<String, Object> params, 
//    		@Parameter(hidden = true) Pageable pageable) {
//        return ResponseEntity.ok(this.genericService.findPageableFilter(params, pageable));
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<T> findById(@PathVariable ID id){
//    	Optional<T> entity = this.genericService.findById(id);
//    	
//    	if (entity.isEmpty()) {
//    		return ResponseEntity.badRequest().build();
//    	}
//    	
//        return ResponseEntity.ok(entity.get());
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Boolean> delete(@PathVariable ID id){
//    	boolean isDeleted = this.genericService.deleteById(id);
//    	
//    	if (isDeleted) {
//    		return ResponseEntity.badRequest().build();
//    	}
//    	
//        return ResponseEntity.ok(isDeleted);
//    }
//    
//	@PutMapping("/update")
//	public ResponseEntity<T> update(@RequestBody T updated) {
//		return ResponseEntity.ok(this.genericService.save(updated));
//	}
//
//	@PostMapping("/create")
//	public ResponseEntity<T> create(@RequestBody T created) {
//		return ResponseEntity.ok(this.genericService.save(created));
//	}
//}

