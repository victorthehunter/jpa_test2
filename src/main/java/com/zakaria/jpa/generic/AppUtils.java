package com.zakaria.jpa.generic;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class AppUtils {
	
	private AppUtils() {}
	
	public static Pageable getPageable(int pageNo, int pageSize, String sortBy, boolean isDesc) {
		if(sortBy != null && !sortBy.isEmpty()) {
			final Sort sort = Sort.by(sortBy);
			return PageRequest.of(pageNo, pageSize, isDesc ? sort.descending() : sort.ascending());
		}
		
		return PageRequest.of(pageNo, pageSize);
	}

}
