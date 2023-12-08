package org.kosta.starducks.document.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.repository.CreateDocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateDocService {
    private final CreateDocRepository createDocRepository;
}
