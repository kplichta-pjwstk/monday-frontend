package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/** Uwaga! ten test nie zakończy się sukcesem. Jest to przykład co dzieje się gdy testy są od siebie zależne
 */
@ExtendWith(MockitoExtension.class)
class DependentStudentServiceTest {

    @Spy
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private List<Student> students = new ArrayList<>();

    /** Przed każdym testem dodajemy studenta do listy (nie jest to poprawne już na tym etapie, ze względu na to, że studentów będzie w liście tylu ile testów
    - będzie dodany jeden przed każdym testem, jednak na potrzeby wyjaśnienia zależności testów nie ma to dla nas na tym etapie znaczenia ile studentów jest w liście
    liczy się wartość największego indexu stuenta z listy a ta w tym wypadku stale wynosi 3L
     */
    @BeforeEach
    void setup() {
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.GDANSK, 3L);
        students.add(student);
        studentRepository.setStudents(students);
    }


    /** w tym teście modyfikujemy wartość listy i dodajemy studenta o wyższym indexie - ten test działa jak powinien i zakończy się sukcesem za każdym razem
     */
    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.GDANSK, 5L);
        students.add(student);

        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(15, savedStudent.index());
        verify(studentRepository, times(1)).saveStudent(any());
    }


    /**te test zachowa się niedeterministycznie - czasem zobaczymy sukces a czasem nie. Będzie to uzależnione od tego, który z testów z tej klasy
    wykona się jako pierwszy. Nie mamy wpływu na kolejność wykonywania testów, więc nie jesteśmy w stanie tego określić.
    Patrząc na ten test oraz metodę beforeEach nie znajdziemy źródła błędu, dlatego  należy dbać o to aby testy były niezależne.
    W naszym wypadku testów mamy tylko 2 ale jeśli kontext będzie szerszy a testów będzie więcej błąd jest bardzo trudny do odnalezienia
     */
    @Test
    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.WARSZAWA, null);

        //when
        studentService.saveStudent(student);

        //then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).saveStudent(argumentCaptor.capture());
        var savedStudent = argumentCaptor.getValue();
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(30, savedStudent.index());
    }
}
