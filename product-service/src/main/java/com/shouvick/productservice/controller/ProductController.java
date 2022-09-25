package com.shouvick.productservice.controller;

import com.shouvick.productservice.dto.ProductDto;
import com.shouvick.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> all(){
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> save(@RequestBody Mono<ProductDto> productDtoMono){
       return productService.save(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono){
       return productService.update(id, productDtoMono)
               .map(ResponseEntity::ok)
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id){
        return productService.deleteProduct(id);
    }

}
