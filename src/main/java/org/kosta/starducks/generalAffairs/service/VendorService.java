package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<String> getAllVendorNames(){
        List<String> vendorNames = vendorRepository.findAllVendorNames();
    return vendorNames;}

}
