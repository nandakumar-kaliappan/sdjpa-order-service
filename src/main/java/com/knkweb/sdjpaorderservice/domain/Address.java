package com.knkweb.sdjpaorderservice.domain;

import lombok.*;

import javax.persistence.Embeddable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Address {
    private String address;
    private String zipCode;
}
