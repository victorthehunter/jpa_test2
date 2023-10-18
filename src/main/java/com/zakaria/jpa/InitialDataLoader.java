package com.zakaria.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import com.zakaria.jpa.entities.Book;
import com.zakaria.jpa.entities.BookPK;
import com.zakaria.jpa.repositories.BookRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ApplicationContextEvent> {

//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	RoleRepository roleRepository;
//
//	@Autowired
//	UserService userService;
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		
//		BookPK bookPK = new BookPK(1L, 1L);
//		Book book = new Book(bookPK);
//		book.setName("book1");
//		bookRepository.save(book); 
//		
//		BookPK bookPK2 = new BookPK(2L, 2L);
//		Book book2 = new Book(bookPK2);
//		book2.setName("book2");
//		bookRepository.save(book2); 

		
//		var roles = IntStream.rangeClosed(1, 10).mapToObj(i -> {
//			Role role = new Role();
//			role.setName("ROLE_USER_" + i);
//			return role;
//		}).collect(Collectors.toList());
//
//		User user = new User();
//		user.setEmail("john@example.com");
//		user.setRoles(roles);
//
//		userRepository.save(user);
		
		
		
		
		

//		userRepository.findById(1).ifPresent(t -> {
//			userRepository.delete(t);
//		});
		
//		var roles = IntStream.rangeClosed(1, 10).mapToObj(i -> {
//			Role role = new Role();
//			role.setName("ROLE_USER_" + i);
//			return role;
//		}).collect(Collectors.toList());
//
////		Role role1 = new Role();
////		role1.setName("ROLE_USER");
////
////		Role role2 = new Role();
////		role2.setName("ROLE_USER2");
//
//		User user = new User();
//		user.setEmail("john@example.com");
//		user.setRoles(roles);
//
//		userRepository.save(user);

//		Parent parent = entityManager.find(Parent.class, parentId);
//		Child childToRemove = parent.getChildren().get(0);
//		parent.getChildren().remove(childToRemove);

//		userRepository.findById(1).ifPresent(user -> {
//			System.out.println(user.getRoles().size());
//			System.out.println(user.getRoles().get(0).getId() + "\n\n\n\n");
//			
//			user.getRoles().remove(user.getRoles().get(0));
//			userRepository.save(user);
//			
//		});

//        userRepository.deleteById(2);

//		User user = new User();
//        user.setEmail("john@example.com");
//        user = userRepository.save(user);
//
//        Role role1 = new Role();
//        role1.setName("ROLE_USER");
//        role1.setUser(user);
//		role1 = roleRepository.save(role1);
//		System.out.println("role1: " + role1);
//
//        Role role2 = new Role();
//        role2.setName("ROLE_ADMIN");
//        role2.setUser(user);
//		role2 = roleRepository.save(role2);
//		System.out.println("role2: " + role2);

//        user.setRoles(Arrays.asList(role1, role2));

//		User user = new User();
//        user.setEmail("john@example.com");
//
//        Role role1 = new Role();
//        role1.setName("ROLE_USER");
//        role1.setUser(user);
//
//        Role role2 = new Role();
//        role2.setName("ROLE_ADMIN");
//        role2.setUser(user);
//
//        user.setRoles(Arrays.asList(role1, role2));
//
//        userRepository.save(user);

//		var newUser = new User();
//		newUser.setEmail("e@g.com1");
//
//		newUser = userRepository.save(newUser);
//		System.out.println("newUser: " + newUser);
//
//		var role1 = new Role();
//		role1.setName("r1");
//		role1.setUser(newUser);
//
//		role1 = roleRepository.save(role1);
//		System.out.println("role1: " + role1);
//
//		var role2 = new Role();
//		role2.setName("r2");
//		role2.setUser(newUser);
//
//		role2 = roleRepository.save(role2);
//		System.out.println("role2: " + role2);

//		var newUser = new User();
//		newUser.setEmail("e@g.com1");
//		newUser.getRoles().addAll(List.of(role1, role2));

//		newUser = userRepository.save(newUser);
//		System.out.println("newUser: " + newUser);

//		var user = userRepository.findTopById(5);
//		var roles = user.get().getRoles();

//		System.out.println("user = " + user);

//		var user = userService.find2TopById(5);
//		System.out.println("user = " + user);

	}

}
