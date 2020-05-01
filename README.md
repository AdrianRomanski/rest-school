# <div align="center"> REST API High School </div>

## <div align="center"> [![CircleCI](https://circleci.com/gh/AdrianRomanski/rest-school.svg?style=svg)](https://circleci.com/gh/AdrianRomanski/rest-school) [![codecov](https://codecov.io/gh/AdrianRomanski/rest-school/branch/master/graph/badge.svg)](https://codecov.io/gh/AdrianRomanski/rest-school) </div>

## <div align="center"> Student </div>
#### GET
* __getAllStudents__ - Return all Students sorted by age -> lastName -> firstName
* __getAllFemaleStudents__ - Return all Female Students sorted by age -> lastName -> firstName
* __getAllMaleStudents__ - Return all Male Students sorted by age -> lastName -> firstName
* __getStudentByFirstAndLastName__ - Return Student with matching firstName and lastName
* __getStudentByID__ - Return Student with matching id
#### POST
* __createNewStudent__ - Converts DTO Object and save it to database
#### PUT
* __updateStudent__ - Converts DTO Object, update Student with matching id and save it to database
#### DELETE
* __deleteStudentByID__ - Delete Student with matching id

## <div align="center"> Teacher </div>
#### GET
* __getAllTeachers__ - Return all Teachers sorted by Specialization -> yearsOfExperience
* __getTeacherByFirstNameAndLastName__ - Return Teacher with matching firstName and lastName
* __getTeacherByID__ - Return Teacher with matching id
* __getTeachersBySpecialization__ - Return Map where the keys are Specializations and values - List of Teachers
* __getTeachersByYearsOfExperience__ - Return Map where the keys are Years of Experience and values - List of Teachers
#### POST
* __createNewTeacher__ - Converts DTO Object and save it to Database
#### PUT
* __updateStudent__ - Converts DTO Object, update Teacher with matching id and save it to database
#### DELETE
* __deleteTeacherById__ - Delete Teacher with matching id

## <div align="center"> Guardian </div>
#### GET
* __getAllGuardians__ - Return all Guardians
* __getGuardianByID__ - GuardianDTO with matching id
* __getGuardianByFirstAndLastName__ - Returns GuardianDTO with matching firstName and lastName
* __getGuardiansByAge__ - Map where the keys are ages of Guardians and values List of Guardians
* __getAllStudentsForGuardian__ - List of Students for Guardian with matching id
#### POST
* __createNewGuardian__ - Converts DTO Object and save it to Database
#### PUT
* __updateGuardian__ - Converts DTO Object, update Guardian with matching id and save it to database
#### DELETE
* __deleteGuardianByID__ - Delete Guardian with matching id

## <div align="center"> Student Class </div>
#### GET
* __getAllStudentClasses__ - Return all Student Classes
* __getStudentClassByID__ -  Student Class with matching id
* __getStudentClassByPresident__ - Return a  List of Student Classes with matching president
* __getStudentClassesGroupedBySpecialization__ - Return a Map where the keys are Specializations and values maps containing Classes   
* __getAllStudentClassForSpecialization__ - Return a list of Student Classes with matching specialization
* __getLargestStudentClass__ - Return a list of Student Classes with largest number of students
* __getSmallestStudentClass__ - Return a list of Student Classes with smallest number of students
* __getAllStudentsForClass__ - Return a Map where the keys are Genders and values List of Students of the Class
#### POST
* __createNewStudentClass__ - Converts DTO Object and save it to Database
#### PUT
* __updateStudentClass__ - Converts DTO Object, update Student Class with matching id and save it to database
#### DELETE
* __deleteStudentClassById__ - Delete Student Class with matching id

## <div align="center"> Sport Team </div>
#### GET
* __getAllSportTeam__ - Return all Sport Teams
* __getSportTeamById__ -  Sport Team with matching id
#### POST
* __createNewSportTeam__ - Converts DTO Object and save it to Database
#### PUT
* __updateSportTeam__ - Converts DTO Object, update Sport Team with matching id and save it to database
#### DELETE
* __deleteStudentClassById__ - Delete Sport Team with matching id
