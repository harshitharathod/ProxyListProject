package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ProxyLists;


@Repository
public interface ProxyListRepository extends JpaRepository<ProxyLists, Long> {
	

}
