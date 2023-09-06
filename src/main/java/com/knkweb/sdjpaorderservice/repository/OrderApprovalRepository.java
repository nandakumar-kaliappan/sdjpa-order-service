package com.knkweb.sdjpaorderservice.repository;

import com.knkweb.sdjpaorderservice.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
