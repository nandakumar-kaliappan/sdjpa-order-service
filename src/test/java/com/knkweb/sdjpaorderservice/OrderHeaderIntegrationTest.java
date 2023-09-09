package com.knkweb.sdjpaorderservice;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.knkweb.sdjpaorderservice.domain.*;
import com.knkweb.sdjpaorderservice.repository.CustomerRepository;
import com.knkweb.sdjpaorderservice.repository.OrderApprovalRepository;
import com.knkweb.sdjpaorderservice.repository.OrderHeaderRepository;
import com.knkweb.sdjpaorderservice.repository.ProductRepository;
import org.hibernate.boot.model.source.spi.Orderable;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        orderHeader.setOrderApproval(OrderApproval.builder().approvedBy("Someone").build());
        OrderHeader orderHeader1 = orderHeaderRepository.save(orderHeader);

        assertNotNull(orderHeader1);
        assertNotNull(orderHeader1.getOrderLines());
        assertEquals(orderHeader1.getOrderLines().size(), 1);
        assertNotNull(orderHeader1.getOrderLines().iterator().next().getProduct());
        assertEquals(orderHeader1.getOrderLines().iterator().next().getProduct(),product);
        assertNotNull(orderHeader1.getOrderApproval());
        assertNotEquals(orderHeader1.getOrderApproval(),orderApproval);
    }

    @Test
    @Order(5)
    void testfindByDescription() {
        Product product = productRepository.findByDescription("Product 1");
        assertNotNull(product);
    }

    @Order(6)
    @Test
    void testOrderApprovalBiDirection() {
        OrderHeader orderHeader = OrderHeader.builder().customer(customer).build();

        orderHeader.setOrderApproval(OrderApproval.builder().approvedBy("Someone").build());
        OrderHeader orderHeader1 = orderHeaderRepository.save(orderHeader);

        assertNotNull(orderHeader1);
        assertNotNull(orderHeader1.getOrderApproval());
        assertNotEquals(orderHeader1.getOrderApproval(),orderApproval);

        OrderApproval orderApproval =
                orderApprovalRepository.findById(orderHeader1.getOrderApproval().getId()).orElse(null);

        assertEquals(orderApproval.getApprovedBy(),"Someone" );
    }

    @Order(7)
    @Test
    void testOrderApprovalBiDirectionAndDelete() {
        OrderHeader orderHeader = OrderHeader.builder().customer(customer).build();

        orderHeader.setOrderApproval(OrderApproval.builder().approvedBy("Someone").build());
        OrderHeader orderHeader1 = orderHeaderRepository.save(orderHeader);

        assertNotNull(orderHeader1);
        assertNotNull(orderHeader1.getOrderApproval());
        assertNotEquals(orderHeader1.getOrderApproval(),orderApproval);

        orderHeaderRepository.delete(orderHeader1);

        OrderApproval orderApproval =
                orderApprovalRepository.findById(orderHeader1.getOrderApproval().getId()).orElse(null);

        assertNull(orderApproval);
    }

    @Test
    @Commit
    void testLocking() {

        Category categoryToiletry = Category.builder().description("Toiletry").build();
        Category categoryFluid = Category.builder().description("Fluid").build();
        Category categorySolid = Category.builder().description("Solid").build();

        Product product =
                Product.builder().description("brush").categories(Set.of(categoryToiletry,categorySolid)).build();
        Product product1 = Product.builder().description("paste").categories(Set.of(categoryFluid
                ,categoryToiletry)).build();

        Product brushSaved = productRepository.saveAndFlush(product);
        Product pasteSaved = productRepository.saveAndFlush(product1);


        OrderLine orderLine = OrderLine.builder().quantityOrdered(50).product(brushSaved).build();
        OrderLine orderLine1 = OrderLine.builder().quantityOrdered(40).product(pasteSaved).build();

        Customer customer =
                customerRepository.saveAndFlush(Customer.builder().customerName("sarath").phone(
                        "75647547").build());
        OrderHeader orderHeader = OrderHeader.builder()
                .orderApproval(OrderApproval.builder().approvedBy("knk").build())
                .billingAddress(Address.builder().address("tup").zipCode("8345").build())
                .customer(customer)
                .build();
        orderHeader.addOrderLine(orderLine);
        orderHeader.addOrderLine(orderLine1);

        orderHeaderRepository.saveAndFlush(orderHeader);

    }
}
