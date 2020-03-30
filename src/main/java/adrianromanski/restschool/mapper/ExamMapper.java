package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
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
            @Mapping(source = "results", target = "resultsDTO")
    })
    ExamDTO examToExamDTO(Exam exam);


    @Mappings({
            @Mapping(source = "subjectDTO", target = "subject"),
            @Mapping(source = "studentsDTO", target = "students"),
            @Mapping(source = "resultsDTO", target = "results")
    })
    Exam examDTOToExam(ExamDTO examDTO);



}
