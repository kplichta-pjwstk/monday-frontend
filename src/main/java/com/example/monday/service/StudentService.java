package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
