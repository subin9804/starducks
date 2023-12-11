package org.kosta.starducks.document.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.repository.CreateDocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateDocService {
    private final CreateDocRepository createDocRepository;
}
