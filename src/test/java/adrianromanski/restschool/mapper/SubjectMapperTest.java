package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.mapper.base_entity.SubjectMapper;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import org.junit.jupiter.api.Test;

import static adrianromanski.restschool.domain.base_entity.enums.Subjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectMapperTest {

    public static final Long ID = 1L;
    public static final Long VALUE = 10L;

    SubjectMapper subjectMapper = SubjectMapper.INSTANCE;

    @Test
    public void subjectToSubjectDTO() {
        //given

        Subject subject = new Subject();
        subject.setValue(VALUE);
        subject.setId(ID);
        subject.setName(BIOLOGY);

        //when
        SubjectDTO subjectDTO = subjectMapper.subjectToSubjectDTO(subject);

        assertEquals(ID, subjectDTO.getId());
        assertEquals(BIOLOGY, subjectDTO.getName());
        assertEquals(VALUE, subjectDTO.getValue());
    }

    @Test
    public void subjectDTOToSubject() {
        //given

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setValue(VALUE);
        subjectDTO.setId(ID);
        subjectDTO.setName(BIOLOGY);

        //when
        Subject subject = subjectMapper.subjectDTOToSubject(subjectDTO);

        assertEquals(ID, subject.getId());
        assertEquals(BIOLOGY, subject.getName());
        assertEquals(VALUE, subject.getValue());
    }

}

