package com.example.monday;

import com.example.monday.excetionhandler.RecordNotFoundException;
import com.example.monday.resource.StudentResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest //Uruchamiamy testowy kontekst springa, w ramach którego możemy wykonywać testy
@AutoConfigureMockMvc //Definiujemy, że chemy skorzystać ze skonfigurowanego obiektu mockMvc.
class MondayApplicationTests {

    @Autowired//wstrzykujemy bean - jeśli istnieje kontekst spring ai bean jest skonfigurowany poprawnie powinna się tu znaleźć instancja klasy
    private StudentResource studentResource;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThrows(RecordNotFoundException.class,
                () -> studentResource.getStudentById(UUID.randomUUID()));
    }

    @Test
    void givenNoStudents_whenGetById_thenRespondWithNotFound() throws Exception {
        var response = mockMvc.perform(get("/students/0b0dba77-e141-4dae-960d-344eabcbb858"))
                .andDo(print())
                .andReturn().getResponse();


        assertEquals(response.getStatus(), 404);
        assertTrue(response.getContentAsString().contains("not found"));
    }

}
