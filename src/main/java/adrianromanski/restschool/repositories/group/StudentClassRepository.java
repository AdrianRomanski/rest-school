package adrianromanski.restschool.repositories.group;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

    @Query("select sc.teacher from StudentClass sc")
    Optional<Teacher> getTeacher();
}
