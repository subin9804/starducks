package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.ConfRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfRepository extends JpaRepository<ConfRoom, Long> {

//    List<ConfRoom> getAllByRunningDay();

}
