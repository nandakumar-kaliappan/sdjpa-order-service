package com.knkweb.sdjpaorderservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "products")
@Entity
public class Category extends BaseEntity{
    private String description;

    @ManyToMany
    @JoinTable(name = "product_category",
    joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

}
