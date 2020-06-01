package adrianromanski.restschool.mapper.person;

import adrianromanski.restschool.domain.person.Director;
import adrianromanski.restschool.model.person.DirectorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DirectorMapper {

    DirectorMapper INSTANCE  = Mappers.getMapper(DirectorMapper.class);

    DirectorDTO directorToDirectorDTO(Director director);

    Director directorDTOToDirector(DirectorDTO directorDTO);
}
