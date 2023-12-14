package org.kosta.starducks.fina.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.entity.StoreOperationalYn;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final EmpRepository empRepository;

    /**
     * 지점 목록 조회
     */
    public Page<Store> getAllStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public Page<Store> storeSearchList(String searchKeyword, Pageable pageable) {
        return storeRepository.findByStoreNameContaining(searchKeyword, pageable);
    }

    /**
     * 지점 단일 조회
     */
    public Store findById(Long storeNo) {
        return storeRepository.findById(storeNo).orElse(null);
    }

    /**
     * 지점 추가하기
     *
     * @return
     */
    public void createStore(Store store) {
        storeRepository.save(store);
    }

    public List<String> getStoreManagersNames() {
        List<Employee> storeManagers = empRepository.findByPosition(Position.ROLE_STOREMANAGER);
        return storeManagers.stream()
                .map(Employee::getEmpName)
                .collect(Collectors.toList());
    }

    /**
     * 지점 수정
     *
     * @param
     * @return
     */

    public void editStore(Store store) {
        storeRepository.save(store);
    }

    public Store getStoreById(Long storeNo) {
        return storeRepository.findById(storeNo).orElse(null);
    }

    public List<StoreOperationalYn> getAllStoreOperationalYn() {
        return Arrays.asList(StoreOperationalYn.values());
    }

    public List<String> getAllStoreManagers() {
        List<Employee> storeManagerList = empRepository.findByPosition(Position.ROLE_STOREMANAGER);
        return storeManagerList.stream()
                .map(Employee::getEmpName)
                .collect(Collectors.toList());
    }

    public void updateStore(Store store) {
        Store target = storeRepository.findById(store.getStoreNo()).orElse(null);
        if (target != null) {
            storeRepository.save(store);
        }
    }

    /**
     * 지점 삭제
     *
     * @param storeNo
     * @param rttr
     */
    public void deleteStore(Long storeNo, RedirectAttributes rttr) {
        Store storeTarget = storeRepository.findById(storeNo).orElse(null);
        if (storeTarget != null) {
            storeRepository.delete(storeTarget);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
    }

}
