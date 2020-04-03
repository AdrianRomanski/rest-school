package adrianromanski.restschool.controllers;

import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherListDTO;
import adrianromanski.restschool.services.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Controller for teachers")
@RestController
@RequestMapping("/teachers/")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("Returns a TeachListDTO Object that contains all Teachers")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public TeacherListDTO getAllTeachers() {
        return new TeacherListDTO(teacherService.getAllTeachers());
    }

    @ApiOperation("Returns a Teacher Object based on firstName and lastName")
    @GetMapping("{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return teacherService.getTeacherByFirstNameAndLastName(firstName,lastName);
    }

    @ApiOperation("Returns an Teacher Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByID(@PathVariable String ID) {
        return teacherService.getTeacherByID(Long.valueOf(ID));
    }

    @ApiOperation("Create and save new Teacher based on TeacherDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO createNewTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.createNewTeacher(teacherDTO);
    }

    @ApiOperation("Update an existing Teacher with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO updateTeacher(@PathVariable String ID, @RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(Long.valueOf(ID), teacherDTO);
    }

    @ApiOperation("Delete a Teacher based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeacherById(@PathVariable String ID) {
        teacherService.deleteTeacherById(Long.valueOf(ID));
    }
}
