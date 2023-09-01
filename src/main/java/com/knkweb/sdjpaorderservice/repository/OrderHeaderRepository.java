package com.knkweb.sdjpaorderservice.repository;

import com.knkweb.sdjpaorderservice.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
