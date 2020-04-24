# <div align="center"> REST API High School </div>

[![CircleCI](https://circleci.com/gh/AdrianRomanski/rest-school.svg?style=svg)](https://circleci.com/gh/AdrianRomanski/rest-school)
[![codecov](https://codecov.io/gh/AdrianRomanski/rest-school/branch/master/graph/badge.svg)](https://codecov.io/gh/AdrianRomanski/rest-school)

 
## <div align="center"> Students </div> 
#### <div align="left"> Get Mapping </div>
* __getAllStudents__ - Returns all Students sorted by lastName&firstName
* __getAllFemaleStudents__ - Returns all Female Students sorted by lastName&firstName
* __getAllFemaleStudents__ - Returns all Male Students sorted by lastName&firstName
* __getStudentByFirstAndLastName__ - Returns Student with matching firstName and lastName
* __getStudentByID__ - Returns Student with matching id
#### Post Mapping
* __createNewStudent__ - Converts DTO Object and Save it to Database 
* __updateStudent__ - Converts DTO Object, Update Student with Matching ID and save it to Database
#### DeleteMapping
* __deleteStudentByID__ - Delete Student with matching id

