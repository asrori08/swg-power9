package com.swg.power9.controller;

import com.swg.power9.dto.ProductDto;
import com.swg.power9.entity.Product;
import com.swg.power9.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    ProductRepository productRepository;

    KafkaTemplate<String, Product> kafkaTemplate;

    @PostMapping("/product/add")
    public String insertProduct(@RequestBody ProductDto productDto){
        System.out.println(productDto.toString());

        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        productRepository.save(product);

        kafkaTemplate.send("product", product);

        return "OK";
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Product> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
