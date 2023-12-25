package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {


}
