package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.StudentDTO;
import adrianromanski.restschool.model.SubjectDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Biology";
    public static final Long VALUE = 10L;


    SubjectMapper subjectMapper = SubjectMapper.INSTANCE;


    @Test
    public void subjectToSubjectDTO() {
        //given

        Subject subject = new Subject();
        subject.setValue(VALUE);
        subject.setId(ID);
        subject.setName(NAME);

        //when
        SubjectDTO subjectDTO = subjectMapper.subjectToSubjectDTO(subject);

        assertEquals(ID, subjectDTO.getId());
        assertEquals(NAME, subjectDTO.getName());
        assertEquals(VALUE, subjectDTO.getValue());
    }

    @Test
    public void subjectDTOToSubject() {
        //given

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setValue(VALUE);
        subjectDTO.setId(ID);
        subjectDTO.setName(NAME);

        //when
        Subject subject = subjectMapper.subjectDTOToSubject(subjectDTO);

        assertEquals(ID, subject.getId());
        assertEquals(NAME, subject.getName());
        assertEquals(VALUE, subject.getValue());
    }

}

