package adrianromanski.restschool.mapper.event;

import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.model.event.ExamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamMapper {

    ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);


    @Mappings({
            @Mapping(source = "subject", target = "subjectDTO"),
            @Mapping(source = "students", target = "studentsDTO"),
            @Mapping(source = "results", target = "resultsDTO"),
            @Mapping(source = "teacher", target = "teacherDTO")
    })
    ExamDTO examToExamDTO(Exam exam);


    @Mappings({
            @Mapping(source = "subjectDTO", target = "subject"),
            @Mapping(source = "studentsDTO", target = "students"),
            @Mapping(source = "resultsDTO", target = "results"),
            @Mapping(source = "teacherDTO", target = "teacher")
    })
    Exam examDTOToExam(ExamDTO examDTO);



}
