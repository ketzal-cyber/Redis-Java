package com.mx.apirest.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mx.apirest.entity.Browser;

import jakarta.transaction.Transactional;


public interface BrowserRepository extends JpaRepository<Browser, Long> {
	
	public abstract ArrayList<Browser> findByVersion(String version);
	
//	//
//	@Modifying(clearAutomatically = true, flushAutomatically = true)
//	@Transactional
//	@Query(value = "UPDATE navegador SET version = :version WHERE id = :id", nativeQuery = true)
//	public abstract int updateVersion(@Param("id") Long id, @Param("version") String version);
	
	//  metodo de log cuanbdo se llame al repository  desde el service se imprimira
//	default public  Optional<Browser> findBrowser(Long id){
//		System.out.println("LLamando al repository para obtener id");
//		return this.findById(id);
//	}

}
