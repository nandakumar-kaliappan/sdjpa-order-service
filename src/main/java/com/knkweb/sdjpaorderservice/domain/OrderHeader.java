package com.knkweb.sdjpaorderservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AttributeOverrides({
        @AttributeOverride(
                name = "billingAddress.address",
                column = @Column(name = "billing_address")
        ),
        @AttributeOverride(
                name = "billingAddress.zipCode",
                column = @Column(name = "billing_zip_code")
        ),
        @AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        ),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        )
})
@Entity
public class OrderHeader extends BaseEntity {

    @Embedded
    private Address billingAddress;

    @Embedded
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderHeader",
            cascade = {CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<OrderLine> orderLines;

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "orderHeader")
    private OrderApproval orderApproval;

    public void setOrderApproval(OrderApproval orderApproval){
        this.orderApproval = orderApproval;
        orderApproval.setOrderHeader(this);
    }


    public void addOrderLine(OrderLine orderLine) {
        if(orderLines == null){
            orderLines = new HashSet<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }
}
