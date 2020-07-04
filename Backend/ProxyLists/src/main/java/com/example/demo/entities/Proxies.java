package com.example.demo.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="Proxies")
@DynamicUpdate
public class Proxies {
	
	
	@Id
	private String ipaddress;
	private int id;
	private int port;
	private String test_result;
	private String anonymity;
	private String firstFoundAddress;
	private String lastFoundAddress;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnonymity() {
		return anonymity;
	}
	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}
	public String getTest_result() {
		return test_result;
	}
	public void setTest_result(String test_result) {
		this.test_result = test_result;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getFirstFoundAddress() {
		return firstFoundAddress;
	}
	public void setFirstFoundAddress(String first_found_address) {
		this.firstFoundAddress = first_found_address;
	}
	public String getLastFoundAddress() {
		return lastFoundAddress;
	}
	public void setLastFoundAddress(String lastFoundAddress) {
		this.lastFoundAddress = lastFoundAddress;
	}
	
	

}
