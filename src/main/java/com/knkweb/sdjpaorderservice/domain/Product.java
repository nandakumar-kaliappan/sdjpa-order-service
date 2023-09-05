package com.knkweb.sdjpaorderservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
public class Product extends BaseEntity {
    private String description;
    @ManyToMany
    @JoinTable(name = "product_category",
            inverseJoinColumns = @JoinColumn(name = "category_id"),
             joinColumns= @JoinColumn(name = "product_id"))
    private Set<Category> categories;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
}
