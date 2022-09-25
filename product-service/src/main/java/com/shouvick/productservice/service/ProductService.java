package com.shouvick.productservice.service;

import com.shouvick.productservice.dto.ProductDto;
import com.shouvick.productservice.repository.ProductRepository;
import com.shouvick.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> save(Mono<ProductDto> productDtoMono) {
       return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> update(String id,Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);

    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
