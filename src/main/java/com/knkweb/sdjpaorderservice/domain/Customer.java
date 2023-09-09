package com.knkweb.sdjpaorderservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = "orders")
@Entity
public class Customer extends BaseEntity{
    private String customerName;
    @Embedded
    private Address address;

    private String phone;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderHeader> orders = new LinkedHashSet<>();

    @Version
    private Integer version;


}
