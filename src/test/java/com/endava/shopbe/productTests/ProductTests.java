package com.endava.shopbe.productTests;

import com.endava.shopbe.entity.Category;
import com.endava.shopbe.entity.Product;
import com.endava.shopbe.entity.Supplier;
import com.endava.shopbe.repository.ProductRepo;
import com.endava.shopbe.service.ProduceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductTests {
    @Mock
    private ProductRepo productRepo;

    private ProduceService produceService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        produceService = new ProduceService(productRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    public Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("Iphone 13")
                .price(1300.0)
                .cantity(120)
                .category(new Category(1L, "Electrocasnice"))
                .supplier(new Supplier(1L, "Apple"))
                .build();
    }

    @Test
    void testSaveProduce() {
        Product product = createProduct();
        produceService.save(product);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepo).save(productArgumentCaptor.capture());

        Product capturedProduce = productArgumentCaptor.getValue();
        Assertions.assertThat(capturedProduce).isEqualTo(product);
    }

    @Test
    void getAllProductsTest() {
        produceService.findAll();
        verify(productRepo).findAll();
    }

    @Test
    void FindByIdProductTest() throws CloneNotSupportedException {
        Product product = createProduct();

        produceService.save(product);

        doReturn(Optional.of(product)).when(productRepo).findById(1L);

        Product product2 = produceService.findById(1L);

        Product productClone=(Product)product2.clone();

//        productClone.setCantity(20);

        Assertions.assertThat(product.getId()).isEqualTo(productClone.getId());
        Assertions.assertThat(product.getName()).isEqualTo(productClone.getName());
        Assertions.assertThat(product.getCantity()).isEqualTo(productClone.getCantity());
        Assertions.assertThat(product.getCategory()).isEqualTo(productClone.getCategory());
        Assertions.assertThat(product.getPrice()).isEqualTo(productClone.getPrice());
        Assertions.assertThat(product.getSupplier()).isEqualTo(productClone.getSupplier());
    }
}
