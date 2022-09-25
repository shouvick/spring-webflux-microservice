package com.shouvick.userservice.controller;

import com.shouvick.userservice.dto.UserDto;
import com.shouvick.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public Flux<UserDto> all(){
        return userService.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable int id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> save(@RequestBody Mono<UserDto> userDtoMono){
        return userService.save(userDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> update(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono){
        return userService.update(id,userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable int id){
        return userService.deleteUserById(id);
    }


}
