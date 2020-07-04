package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.Proxies;
import com.example.demo.repositories.ProxyRepository;

@CrossOrigin
@RestController
@Controller
@RequestMapping("/api")
public class ProxiesController {

	@Autowired
	private ProxyRepository proxyRepository;

	@GetMapping("/getProxies")
	public List<Proxies> getAllProxies() {
		return proxyRepository.findAll();
	}
	@GetMapping("/getProxy/{id}")
	public Optional<Proxies> getProxy(@PathVariable Long id) {
		return proxyRepository.findById(id);
	}

	@PostMapping("/postProxy")
	public Proxies createProxy(@RequestBody Proxies proxy) {
		return proxyRepository.save(proxy);
	}

	@PutMapping("/putProxy")
	public Proxies updateProxy(@RequestBody Proxies proxy) {
		return proxyRepository.save(proxy);
	}

	@DeleteMapping("/deleteProxy")
	public void deleteProxy(@RequestBody Proxies proxy) {
		proxyRepository.delete(proxy);;
	}

	

	


}
