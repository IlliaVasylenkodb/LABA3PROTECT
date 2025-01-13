package com.tpp.lab3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpp.lab3.models.Computer;
import com.tpp.lab3.repository.ComputerRepository;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    // Получить все компьютеры
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    // Найти компьютер по ID
    public Optional<Computer> findComputerById(Integer id) {
        return computerRepository.findById(id);
    }

    // Сохранить новый компьютер
    public void saveComputer(Computer computer) {
        computerRepository.save(computer);
    }

    // Обновить информацию о компьютере
    public void updateComputer(Computer updatedComputer) {
        Optional<Computer> existingComputerOpt = computerRepository.findById(updatedComputer.getComputerId());
        if (existingComputerOpt.isPresent()) {
            Computer existingComputer = existingComputerOpt.get();
            existingComputer.setName(updatedComputer.getName());
            computerRepository.save(existingComputer);
        }
    }

    // Удалить компьютер по ID
    public void deleteComputerById(Integer id) {
        computerRepository.deleteById(id);
    }
    
    
}
