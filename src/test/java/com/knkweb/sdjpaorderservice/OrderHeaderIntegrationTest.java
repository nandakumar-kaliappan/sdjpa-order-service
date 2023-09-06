package com.knkweb.sdjpaorderservice;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.knkweb.sdjpaorderservice.domain.*;
import com.knkweb.sdjpaorderservice.repository.CustomerRepository;
import com.knkweb.sdjpaorderservice.repository.OrderApprovalRepository;
import com.knkweb.sdjpaorderservice.repository.OrderHeaderRepository;
import com.knkweb.sdjpaorderservice.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHeaderIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    Product product;
    Customer customer;
    OrderApproval orderApproval;

    @BeforeEach
    void setUp() {
        product =
                productRepository.saveAndFlush(Product.builder().description("Test product").productStatus(ProductStatus.NEW).build());
        Customer customerT =
                Customer.builder().email("auihfd@sudh.com").phone("76548394875").address(Address.builder().address(
                        "RGP").zipCode("245").build()).customerName(
                "Test Customer").build();
        customer = customerRepository.saveAndFlush(customerT);

        orderApproval = orderApprovalRepository.saveAndFlush(OrderApproval.builder().approvedBy("me"
        ).build());
    }

    //    @Commit
    @Test
    @Order(1)
    void testOrderPersistance() {
        OrderHeader orderHeader =
                OrderHeader.builder().customer(customer).billingAddress(Address.builder().address("RGP").zipCode("641602").build()).build();
        OrderHeader orderHeaderSaved = orderHeaderRepository.save(orderHeader);
        assertNotNull(orderHeaderSaved);
        assertNotNull(orderHeaderSaved.getId());
        assertEquals(orderHeaderSaved.getCustomer().getCustomerName(), orderHeaderSaved.getCustomer().getCustomerName());
        assertEquals(orderHeader, orderHeaderSaved);

        OrderHeader orderHeaderFetched =
                orderHeaderRepository.findById(orderHeaderSaved.getId()).get();
        assertNotNull(orderHeaderFetched);
        assertEquals("RGP", orderHeaderFetched.getBillingAddress().getAddress());
        assertNotNull(orderHeaderSaved.getId());
        assertEquals(orderHeaderSaved.getCustomer().getCustomerName(), orderHeaderFetched.getCustomer().getCustomerName());

    }

    @Test
    @Order(2)
    void productTest() {
        Product product = Product.builder()
                .description("some Desc")
                .build();
        Product productSaved = productRepository.save(product);
        assertNotNull(productSaved);

        Product productFetched = productRepository.findById(productSaved.getId()).get();
        assertNotNull(productFetched);
        assertEquals(productSaved, productFetched);
    }

    @Test
    @Order(3)
    void testSaveOrderWithLine() {
        OrderHeader orderHeader =
                OrderHeader.builder().customer(customer).build();
        OrderLine orderLine = OrderLine.builder().quantityOrdered(5).build();
//        orderHeader.setOrderLines(Set.of(orderLine));
//        orderLine.setOrderHeader(orderHeader);
        orderHeader.addOrderLine(orderLine);
        OrderHeader orderHeader1 = orderHeaderRepository.save(orderHeader);

        assertNotNull(orderHeader1);
        assertNotNull(orderHeader1.getOrderLines());
        assertEquals(orderHeader1.getOrderLines().size(), 1);
    }

    @Test
    @Order(4)
    void testOrderLineWithProduct() {
        OrderHeader orderHeader = OrderHeader.builder().customer(customer).build();
        OrderLine orderLine = OrderLine.builder().quantityOrdered(5).build();
//        orderHeader.setOrderLines(Set.of(orderLine));
//        orderLine.setOrderHeader(orderHeader);
        orderHeader.addOrderLine(orderLine);
        orderLine.setProduct(product);
        orderHeader.setOrderApproval(orderApproval);
        OrderHeader orderHeader1 = orderHeaderRepository.save(orderHeader);

        assertNotNull(orderHeader1);
        assertNotNull(orderHeader1.getOrderLines());
        assertEquals(orderHeader1.getOrderLines().size(), 1);
        assertNotNull(orderHeader1.getOrderLines().iterator().next().getProduct());
        assertEquals(orderHeader1.getOrderLines().iterator().next().getProduct(),product);
        assertNotNull(orderHeader1.getOrderApproval());
        assertEquals(orderHeader1.getOrderApproval(),orderApproval);
    }

    @Test
    @Order(5)
    void testfindByDescription() {
        Product product = productRepository.findByDescription("Product 1");
        assertNotNull(product);
    }

}
