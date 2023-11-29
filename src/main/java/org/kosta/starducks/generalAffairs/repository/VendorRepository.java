package org.kosta.starducks.generalAffairs.repository;

import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    @Query("select v.vendorName from Vendor v")
    List<String> findAllVendorNames();
    //kj
}
