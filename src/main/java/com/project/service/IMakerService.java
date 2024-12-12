package com.project.service;

import com.project.models.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerService {

    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void save(Maker maker);
    void deleteById(Long id);
}
