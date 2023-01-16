package com.franck.example.repository;

import com.franck.example.models.Addresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AddresseRepository extends JpaRepository<Addresse, Integer> {

}
