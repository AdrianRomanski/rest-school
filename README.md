# <div align="center"> REST API High School </div>

## <div align="center"> [![CircleCI](https://circleci.com/gh/AdrianRomanski/rest-school.svg?style=svg)](https://circleci.com/gh/AdrianRomanski/rest-school) [![codecov](https://codecov.io/gh/AdrianRomanski/rest-school/branch/master/graph/badge.svg)](https://codecov.io/gh/AdrianRomanski/rest-school) </div>
 
## <div align="center"> Students </div> 
#### GET
* __getAllStudents__ - Returns all Students sorted by age -> lastName -> firstName
* __getAllFemaleStudents__ - Returns all Female Students sorted by age -> lastName -> firstName
* __getAllMaleStudents__ - Returns all Male Students sorted by age -> lastName -> firstName
* __getStudentByFirstAndLastName__ - Returns Student with matching firstName and lastName
* __getStudentByID__ - Returns Student with matching id
#### POST
* __createNewStudent__ - Converts DTO Object and save it to database
#### PUT
* __updateStudent__ - Converts DTO Object, update Student with matching id and save it to database
#### DELETE
* __deleteStudentByID__ - Delete Student with matching id

## <div align="center"> Teachers </div> 
#### GET
* __getAllTeachers__ - Returns all Teachers sorted by Specialization -> yearsOfExperience
* __getTeacherByFirstNameAndLastName__ - Returns Teacher with matching firstName and lastName
* __getTeacherByID__ - Returns Teacher with matching id
* __getTeachersBySpecialization__ - Returns Map where the keys are Specializations and values - List of Teachers
* __getTeachersByYearsOfExperience__ - Returns Map where the keys are Years of Experience and values - List of Teachers
#### POST
* __createNewTeacher__ - Converts DTO Object and save it to Database
#### PUT
* __updateStudent__ - Converts DTO Object, update Teacher with matching id and save it to database
#### DELETE
* __deleteTeacherById__ - Delete Teacher with matching id


