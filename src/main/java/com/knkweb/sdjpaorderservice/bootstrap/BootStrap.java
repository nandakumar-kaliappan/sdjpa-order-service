package com.knkweb.sdjpaorderservice.bootstrap;

import com.knkweb.sdjpaorderservice.domain.*;
import com.knkweb.sdjpaorderservice.repository.CustomerRepository;
import com.knkweb.sdjpaorderservice.repository.OrderHeaderRepository;
import com.knkweb.sdjpaorderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Set;

@Component
public class BootStrap implements CommandLineRunner {
    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        OrderHeader orderHeader = null;
        if(orderHeaderRepository.findById(7L).orElse(null) == null) {
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
            OrderHeader orderHeader1 = OrderHeader.builder()
                    .orderApproval(OrderApproval.builder().approvedBy("knk").build())
                    .billingAddress(Address.builder().address("tup").zipCode("8345").build())
                    .customer(customer)
                    .build();
            orderHeader1.addOrderLine(orderLine);
            orderHeader1.addOrderLine(orderLine1);

            orderHeader = orderHeaderRepository.saveAndFlush(orderHeader1);
        }

        orderHeader = orderHeaderRepository.findById(7L).orElse(null);
        orderHeader.getOrderLines().forEach(orderLine -> {
            System.out.println(orderLine.getProduct().getDescription());
            orderLine.getProduct().getCategories().forEach(category -> {
                System.out.println(category.getDescription()+" :-");
            });
        });

        Customer customer = new Customer();
        customer.setCustomerName("sdjf");
        Customer customer1 = customerRepository.saveAndFlush(customer);
        System.out.println(customer1.getVersion());

        customer1.setCustomerName("jhsdfv");
        Customer customer2 = customerRepository.saveAndFlush(customer1);
        System.out.println(customer2.getVersion());

        customer1.setCustomerName("hyyhdjf");
        Customer customer3 = customerRepository.saveAndFlush(customer1);
        System.out.println(customer3.getVersion());

        System.out.println("Run method ends");

    }

    private void bootStrapData() {

    }

}
