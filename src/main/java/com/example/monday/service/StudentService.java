package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Tu korzystamy już z Dependency Injection realizowanego przez konstruktor
// Pozwalamy, aby to Spring utworzył dla nas i wstrzykął StudentRepository do tej klasy
// podając to jako parametr konstruktora. Dopuszcza się też wstrzyknięcie Beana przez setter z adnotacją @Autowired
// Zamiast konstruktora używamy adnotacji z pakietu Lombok - dzięki temu jeśli dodamy tu inny Bean nie musimy już nic robić - konstruktor zaktualizuje nam lombok.
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    public void saveStudent(Student student) {
        studentRepository.saveStudent(student);
    }

    public Student getStudentById(UUID id) {
        return studentRepository.getStudentById(id);
    }
}
