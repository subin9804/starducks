package org.kosta.starducks.fina.repository;

import org.kosta.starducks.fina.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findByStoreNameContaining(String searchKeyword, Pageable pageable);
}
