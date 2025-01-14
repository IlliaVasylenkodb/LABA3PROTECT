package com.tpp.lab3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpp.lab3.models.Factory;
import com.tpp.lab3.repository.FactoryRepository;

@Service
public class FactoryService {

    @Autowired
    private FactoryRepository factoryRepository;

    // Отримання всіх фабрик
    public List<Factory> getAllFactories() {
        return factoryRepository.findAll();
    }

    // Пошук фабрики за її ID
    public Optional<Factory> findFactoryById(Integer id) {
        return factoryRepository.findById(id);
    }

    // Збереження нової фабрики
    public void saveFactory(Factory factory) {
        factoryRepository.save(factory);
    }

    // Оновлення фабрики
    public void updateFactory(Factory updatedFactory) {
        Optional<Factory> existingFactoryOpt = factoryRepository.findById(updatedFactory.getFactoryId());

        if (existingFactoryOpt.isPresent()) {
            Factory existingFactory = existingFactoryOpt.get();
            existingFactory.setName(updatedFactory.getName());
            existingFactory.setCountryId(updatedFactory.getCountryId());
            factoryRepository.save(existingFactory);
        }
    }

    // Видалення фабрики за ID
    public void deleteFactoryById(Integer id) {
        factoryRepository.deleteById(id);
    }
}
