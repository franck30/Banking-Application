package com.franck.example.services.impl;


import com.franck.example.dto.AddresseDto;
import com.franck.example.models.Addresse;
import com.franck.example.repository.AddresseRepository;
import com.franck.example.services.AddresseService;
import com.franck.example.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddresseServiceImpl implements AddresseService {

    private final AddresseRepository repository;
    private final ObjectsValidator<AddresseDto> validator;

    @Override
    public Integer save(AddresseDto dto) {
        validator.validate(dto);
        Addresse addresse = AddresseDto.toEntity(dto);
        return repository.save(addresse).getId();
    }

    @Override
    public List<AddresseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AddresseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddresseDto findById(Integer id) {
        return repository.findById(id)
                .map(AddresseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No address found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        //todo check delete
        repository.deleteById(id);

    }
}
