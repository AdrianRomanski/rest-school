package adrianromanski.restschool.mapper.event;

import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamResultMapper {

    ExamResultMapper INSTANCE  = Mappers.getMapper(ExamResultMapper.class);

    @Mapping(source = "exam" ,target = "examDTO")
    ExamResultDTO examResultToExamResultDTO(ExamResult examResult);

    @Mapping(source = "examDTO" ,target = "exam")
    ExamResult examResultDTOToExamResult(ExamResultDTO examResultDTO);
}
