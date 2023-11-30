package org.kosta.starducks.generalAffairs.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    List<Product> findByProductName(String productName);

    Page<Product> findByProductNameContaining(String searchKeyword, Pageable pageable);




}
