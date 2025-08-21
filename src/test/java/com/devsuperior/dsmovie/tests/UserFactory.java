package com.devsuperior.dsmovie.tests;

import com.devsuperior.dsmovie.entities.RoleEntity;
import com.devsuperior.dsmovie.entities.UserEntity;

import java.time.LocalDate;

public class UserFactory {

	public static UserEntity createClientUser() {
		UserEntity user = new UserEntity(1L, "Maria", "maria@gmail.com", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new RoleEntity(1L, "ROLE_CLIENT"));
		return user;
	}

	public static UserEntity createAdminUser() {
		UserEntity user = new UserEntity(2L, "Alex", "alex@gmail.com", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new RoleEntity(2L, "ROLE_ADMIN"));
		return user;
	}

	public static UserEntity createCustomClientUser(Long id, String username) {
		UserEntity user = new UserEntity(id, "Maria", username, "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new RoleEntity(1L, "ROLE_CLIENT"));
		return user;
	}

	public static UserEntity createCustomAdminUser(Long id, String username) {
		UserEntity user = new UserEntity(id, "Alex", username, "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new RoleEntity(2L, "ROLE_ADMIN"));
		return user;
	}
}
