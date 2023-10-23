package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentDataComponent;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
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


    public Student saveStudent(Student student) {
        var index = createIndex(student.getUnit());
        var toSave = new Student(student.getName(), student.getUnit(), index);
        studentRepository.save(toSave);
        return toSave;
    }

    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public void deleteByName(String name){
        studentRepository.deleteByName(name);
    }

    private Long createIndex(StudentUnit unit) {
        long maxIndex = studentRepository.getMaxIndex().orElse(0L);
        if(StudentUnit.GDANSK.equals(unit)) {
            return 5 + maxIndex;
        } else {
            return 10 * maxIndex;
        }
    }
}
