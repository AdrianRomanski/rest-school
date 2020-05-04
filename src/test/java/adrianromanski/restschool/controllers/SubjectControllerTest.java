package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.base_entity.SubjectController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.services.base_entity.subject.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static adrianromanski.restschool.domain.base_entity.enums.Subjects.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubjectControllerTest {

    public static final String SUBJECTS = "/subjects/";
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

    SubjectDTO createSubjectDTO(Subjects sub, Long value, Long id) {
        SubjectDTO subjectDTO = SubjectDTO.builder().name(sub).value(value).build();
        subjectDTO.setId(id);
        return subjectDTO;
    }

    SubjectDTO initBiology() {
        return createSubjectDTO(BIOLOGY, 10L, 1L);
    }

    SubjectDTO initMath() {
        return createSubjectDTO(MATHEMATICS, 10L, 2L);
    }

    SubjectDTO initPhysics() {
        return createSubjectDTO(PHYSICS, 5L, 3L);
    }

    List<SubjectDTO> createList() {
        return Arrays.asList(initBiology(), initMath(), initPhysics());
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllSubjects")
    @Test
    void getAllSubjects() throws Exception {
        List<SubjectDTO> subjectDTOS = createList();

        when(subjectService.getAllSubjects()).thenReturn(subjectDTOS);

        mockMvc.perform(get(SUBJECTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(3)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getSubjectByID")
    @Test
    void getSubjectByID() throws Exception {
        SubjectDTO biology = initBiology();

        when(subjectService.getSubjectByID(anyLong())).thenReturn(biology);

        mockMvc.perform(get(SUBJECTS + "id-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY.toString())));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getSubjectByName")
    @Test
    void getSubjectByName() throws Exception {
        SubjectDTO subjectDTO = initBiology();

        when(subjectService.getSubjectByName(anyString())).thenReturn(subjectDTO);

        mockMvc.perform(get(SUBJECTS + "name-Biology")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY.toString())));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getSubjectsWithFullValue")
    @Test
    void getSubjectsWithFullValue() throws Exception {
        List<SubjectDTO> subjectDTOS = Arrays.asList(initBiology(), initMath());

        when(subjectService.getSubjectsWithFullValue()).thenReturn(subjectDTOS);

        mockMvc.perform(get(SUBJECTS + "most-valuable")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getSubjectsWithLowestValue")
    @Test
    void getSubjectsWithLowestValue() throws Exception {
        List<SubjectDTO> subjectDTOS = Collections.singletonList(initPhysics());

        when(subjectService.getSubjectsWithLowestValue()).thenReturn(subjectDTOS);

        mockMvc.perform(get(SUBJECTS + "less-valuable")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subjects", hasSize(1)));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewSubject")
    @Test
    void createNewSubject() throws Exception {
        SubjectDTO subjectDTO = initBiology();

        when(subjectService.createNewSubject(any(SubjectDTO.class))).thenReturn(subjectDTO);

        mockMvc.perform(post(SUBJECTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY.toString())));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateSubject")
    @Test
    void updateSubject() throws Exception {
        SubjectDTO subjectDTO = initBiology();
        subjectDTO.setValue(1L); // Updating

        when(subjectService.updateSubject(anyLong(), any(SubjectDTO.class))).thenReturn(subjectDTO);

        mockMvc.perform(put(SUBJECTS + subjectDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(BIOLOGY.toString())))
                .andExpect(jsonPath("$.value", equalTo(1)));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteSubjectByID")
    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete(SUBJECTS + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subjectService).deleteSubjectByID(anyLong());
    }

    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Exception] = ResourceNotFoundException")
    @Test
    public void testNotFoundException() throws Exception {
        when(subjectService.getSubjectByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(SUBJECTS + "id-222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
