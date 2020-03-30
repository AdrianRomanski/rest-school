package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.ExamResult;
import adrianromanski.restschool.model.ExamResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamResultMapper {

    ExamResultMapper INSTANCE  = Mappers.getMapper(ExamResultMapper.class);

    @Mapping(source = "exam" ,target = "examDTO")
    ExamResultDTO examResultToExamResultDTO(ExamResult examResult);

    @Mapping(source = "examDTO" ,target = "exam")
    ExamResult examResultDTOToExamResult(ExamResult examResult);
}
