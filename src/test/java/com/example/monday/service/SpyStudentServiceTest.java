package com.example.monday.service;

import com.example.monday.data.Student;
import com.example.monday.data.StudentRepository;
import com.example.monday.data.StudentUnit;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**Rozszerzamy junit rozszerzeniem z mockito, aby móc w junitowych testach korzystać z funkcji biblioteki mockito
 * Log wprowadzam nam loggera używanego później do logowania w metodach konfiguracyjnych
 */
@Log
@ExtendWith(MockitoExtension.class)
class SpyStudentServiceTest {

    /**Spy tworzy nam naszą klasę i jednocześnie pozwala śledzić jej wywołania
    w przeciwieństwie do mocka nie konfigurujemy zachowania
     */
    @Spy
    private StudentRepository studentRepository;

    /**InjectMocks pozwala nam stworzyć klasę testowaną z wykorzystaniem obiektów, które zdefiniowaliśmy
    jako elementy mockito używając adnotacji Mock/Spy
     */
    @InjectMocks
    private StudentService studentService;

    /** ta metoda pozwala nam wprowadzić konfigurację przed wszystkimi testami w klasie
     */
    @BeforeAll
    static void setUpAll() {
        log.info("Before all tests this setup is called");
    }

    /** ta metoda pozwala nam wprowadzić konfigurację przed każdym testem w klasie
     */
    @BeforeEach
    void setUp() {
        log.info("Before each test this setup is called");
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.GDANSK, 3L);
        studentRepository.setStudents(List.of(student));
    }

    /** ta metoda pozwala nam wprowadzić konfigurację po wszystkich testach w klasie
     */
    @AfterEach
    void cleanUp() {
        log.info("After each test this cleanup is called");
    }

    /**ta metoda pozwala nam wprowadzić konfigurację po wszystkich testach w klasie
     */
    @AfterAll
    static void cleanUpAll() {
        log.info("After all tests this cleanup is called");
    }

    @Test
    void givenGdanskUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.GDANSK, null);

        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        /**weryfikujemy wartości otrzymanych danych, mamy do dyzpozycji wiele metod
        takich jak assertTrue, assertNotNull, assertThrows (dla wyjątków)
         */
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(15, savedStudent.index());
        /** weyfikujemy wywołanie metody z ilością wywołań
         */
        verify(studentRepository, times(1)).saveStudent(any());
    }


    @Test
    void givenWarszawaUnitWhenSaveStudentThenGetValidIndex() {
        //given
        var student = new Student(UUID.fromString("193c30a0-2c73-4229-989c-c257c05a9413"), "Karola", StudentUnit.WARSZAWA, null);

        //when
        var savedStudent = studentService.saveStudent(student);

        //then
        assertEquals(student.id(), savedStudent.id());
        assertEquals(student.name(), savedStudent.name());
        assertEquals(student.unit(), savedStudent.unit());
        assertEquals(30, savedStudent.index());
        verify(studentRepository, times(1)).saveStudent(any());
    }
}
