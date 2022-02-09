package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//CRUD함수를 JpaRepository가 들고있음
//@Repository라는 어노테이션없이 IoC가 된다. 왜냐하면, JpaRepository를 상속받았으니까
public interface UserRepository extends JpaRepository<User, Integer>{

}
