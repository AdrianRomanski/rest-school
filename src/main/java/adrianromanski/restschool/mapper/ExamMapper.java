package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Exam;
import adrianromanski.restschool.model.ExamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamMapper {

    ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);


    @Mappings({
            @Mapping(source = "subject", target = "subjectDTO"),
            @Mapping(source = "students", target = "studentsDTO")
    })
    ExamDTO examToExamDTO(Exam exam);


    @Mappings({
            @Mapping(source = "subjectDTO", target = "subject"),
            @Mapping(source = "studentsDTO", target = "students")
    })
    Exam examDTOToExam(ExamDTO examDTO);



}
