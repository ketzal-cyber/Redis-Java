package com.mx.apirest.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "navegador")
public class Browser  {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String version;
	
	public Browser() {
		super();
	}

	public Browser(Long id, String name, String version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Browser [id=" + id + ", name=" + name + ", version=" + version + "]";
	}
	
	
	
	

}
