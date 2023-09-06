package com.knkweb.sdjpaorderservice.domain;


import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class OrderApproval extends BaseEntity {

    private String approvedBy;
}
