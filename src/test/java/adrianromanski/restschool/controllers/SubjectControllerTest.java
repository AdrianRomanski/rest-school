package adrianromanski.restschool.controllers;

import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.services.subject.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubjectControllerTest {

    public static final String BIOLOGY = "Biology";
    public static final String MATH = "Math";
    public static final String PHYSICAL_EDUCATION = "Physical education";


    @Mock
    SubjectService subjectService;

    @InjectMocks
    SubjectController subjectController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    SubjectDTO initBiology() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(BIOLOGY);
        subjectDTO.setValue(10L);
        subjectDTO.setId(1L);
        return subjectDTO;
    }

    SubjectDTO initMath() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(MATH);
        subjectDTO.setValue(10L);
        subjectDTO.setId(2L);
        return subjectDTO;
    }

    SubjectDTO initPE() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(PHYSICAL_EDUCATION);
        subjectDTO.setValue(1L);
        subjectDTO.setId(3L);
        return subjectDTO;
    }

    @Test
    void getAllSubjects() throws Exception {
        List<SubjectDTO> subjectDTOS = Arrays.asList(initBiology(), initMath(), initPE());

        when(subjectService.getAllSubjects()).thenReturn(subjectDTOS);

        mockMvc.perform(get("/subjects/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(3)));
    }

    @Test
    void getSubjectByID() throws Exception {

        SubjectDTO biology = initBiology();

        when(subjectService.getSubjectByID(anyLong())).thenReturn(biology);

        mockMvc.perform(get("/subjects/id-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY)));
    }

    @Test
    void createNewSubject() throws Exception {
        SubjectDTO subjectDTO = initBiology();

        SubjectDTO returnDTO = new SubjectDTO();
        returnDTO.setName(subjectDTO.getName());
        returnDTO.setValue(subjectDTO.getValue());

        when(subjectService.createNewSubject(subjectDTO)).thenReturn(returnDTO);

        mockMvc.perform(post("/subjects/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY)));
    }

    @Test
    void updateSubject() throws Exception {
        SubjectDTO subjectDTO = initBiology();

        SubjectDTO returnDTO = new SubjectDTO();
        returnDTO.setName(subjectDTO.getName());
        returnDTO.setValue(1L);  // Update value
        returnDTO.setId(subjectDTO.getId());

        when(subjectService.updateSubject(anyLong(), any(SubjectDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put("/subjects/" + subjectDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY)))
                .andExpect(jsonPath("$.value", equalTo(1)));
    }

    @Test
    void getSubjectByName() throws Exception {
        SubjectDTO subjectDTO = initBiology();

        when(subjectService.getSubjectByName(anyString())).thenReturn(subjectDTO);

        mockMvc.perform(get("/subjects/name-Biology")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY)));
    }

    @Test
    void getSubjectsWithFullValue() throws Exception {

        List<SubjectDTO> subjectDTOS = Arrays.asList(initBiology(), initMath());

        when(subjectService.getSubjectsWithFullValue()).thenReturn(subjectDTOS);

        mockMvc.perform(get("/subjects/most-valuable")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(2)));
    }

    @Test
    void getSubjectsWithLowestValue() throws Exception {

        List<SubjectDTO> subjectDTOS = Collections.singletonList(initPE());

        when(subjectService.getSubjectsWithLowestValue()).thenReturn(subjectDTOS);

        mockMvc.perform(get("/subjects/less-valuable")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(1)));
    }

    @Test
    void getMostPopularSubject() throws Exception {

        SubjectDTO biology = initBiology();

        when(subjectService.getMostPopularSubject()).thenReturn(biology);

        mockMvc.perform(get("/subjects/most-popular")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY)));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/subjects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subjectService).deleteSubjectByID(anyLong());
    }


    @Test
    public void testNotFoundException() throws Exception {

        when(subjectService.getSubjectByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/students/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
