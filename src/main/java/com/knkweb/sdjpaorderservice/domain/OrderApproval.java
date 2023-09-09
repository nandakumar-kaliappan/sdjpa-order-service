package com.knkweb.sdjpaorderservice.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "orderHeader")
@Entity
public class OrderApproval extends BaseEntity {

    private String approvedBy;

    @OneToOne
    private OrderHeader orderHeader;
}
