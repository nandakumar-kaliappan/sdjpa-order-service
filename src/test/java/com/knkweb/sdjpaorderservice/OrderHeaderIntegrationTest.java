package com.knkweb.sdjpaorderservice;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.knkweb.sdjpaorderservice.domain.Address;
import com.knkweb.sdjpaorderservice.domain.OrderHeader;
import com.knkweb.sdjpaorderservice.domain.Product;
import com.knkweb.sdjpaorderservice.repository.OrderHeaderRepository;
import com.knkweb.sdjpaorderservice.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHeaderIntegrationTest {
    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

//    @Commit
    @Test
    @Order(1)
    void testOrderPersistance() {
        OrderHeader orderHeader =
                OrderHeader.builder().customerName("john Thompson").billingAddress(Address.builder().address("RGP").zipCode("641602").build()).build();
        OrderHeader orderHeaderSaved = orderHeaderRepository.save(orderHeader);
        assertNotNull(orderHeaderSaved);
        assertNotNull(orderHeaderSaved.getId());
        assertEquals(orderHeaderSaved.getCustomerName(), orderHeaderSaved.getCustomerName());
        assertEquals(orderHeader, orderHeaderSaved);

        OrderHeader orderHeaderFetched =
                orderHeaderRepository.findById(orderHeaderSaved.getId()).get();
        assertNotNull(orderHeaderFetched);
        assertEquals("RGP", orderHeaderFetched.getBillingAddress().getAddress());
        assertNotNull(orderHeaderSaved.getId());
        assertEquals(orderHeaderSaved.getCustomerName(), orderHeaderFetched.getCustomerName());

    }

    @Test
    @Order(1)
    void productTest(){
        Product product = Product.builder()
                .description("some Desc")
                .build();
        Product productSaved = productRepository.save(product);
        assertNotNull(productSaved);

        Product productFetched = productRepository.findById(productSaved.getId()).get();
        assertNotNull(productFetched);
        assertEquals(productSaved, productFetched);
    }

}
