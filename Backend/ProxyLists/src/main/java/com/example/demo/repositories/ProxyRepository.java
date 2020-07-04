package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Proxies;

@Repository
public interface ProxyRepository extends JpaRepository<Proxies, Long> {
	
	
	@Query("DELETE FROM Proxies p WHERE p.ipaddress = ?1")
	public void deleteProxiesByIpaddress(String ipaddress);
}
