package com.shouvick.productservice;

import com.shouvick.productservice.dto.ProductDto;
import com.shouvick.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        ProductDto productDto1 = new ProductDto("A",1.0);
        ProductDto productDto2 = new ProductDto("B",2.0);
        ProductDto productDto3 = new ProductDto("C",3.0);
        ProductDto productDto4 = new ProductDto("D",4.0);

        Flux.just(productDto1,productDto2,productDto3,productDto4)
                .flatMap(p -> productService.save(Mono.just(p)))
                .subscribe(System.out::println);
    }
}
