package com.demo.catalog.components;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.catalog.components.EventProcessor;
import com.demo.catalog.entities.Product;
import com.demo.catalog.events.ProductAddedEvent;
import com.demo.catalog.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class EventProcessorTest {

    @MockBean
    ProductRepository repo;

    ProductAddedEvent event;
    EventProcessor processor;

    String uuid;
    String name;
    double price;

    @Before
    public void init(){
        uuid = UUID.randomUUID().toString();
        name = "test-"+uuid;
        event = new ProductAddedEvent(uuid, name, price);
        processor = new EventProcessor(repo);
    }

    @Test
    public void testOn(){
        // Arrange
        List<Product> products = new ArrayList<>();
        when(repo.save(any(Product.class)))
                .thenAnswer(i -> {
                    Product prod = i.getArgumentAt(0, Product.class);
                    products.add(prod);
                    return prod;
                });

        // Act
        processor.on(event);

        // Assert
        verify(repo, times(1)).save(any(Product.class));
        verifyNoMoreInteractions(repo);
        Assert.assertEquals(products.get(0).getId(), uuid);
        Assert.assertEquals(products.get(0).getName(), name);

    }
}
