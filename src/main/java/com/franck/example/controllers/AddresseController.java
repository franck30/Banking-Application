package com.franck.example.controllers;


import com.franck.example.dto.AddresseDto;
import com.franck.example.services.AddresseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddresseController {

    private final AddresseService addresseService;


    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody AddresseDto addresseDto) {

        return ResponseEntity.ok(addresseService.save(addresseDto));

    }


    @GetMapping("/")
    public ResponseEntity<List<AddresseDto>> findAll() {
        return ResponseEntity.ok(addresseService.findAll());
    }


    @GetMapping("/{addresse-id}")
    public ResponseEntity<AddresseDto> findById(@PathVariable("addresse-id") Integer addresseId) {
        return ResponseEntity.ok(addresseService.findById(addresseId));
    }

    @DeleteMapping("/{addresse-id}")
    public ResponseEntity<Void> delete(@PathVariable("addresse-id") Integer addresseId) {

        addresseService.delete(addresseId);
        return ResponseEntity.accepted().build();
    }
}
