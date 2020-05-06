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
* __addExamForClass__ - Adding Exam to every Student in the Class
* __addCorrectionExamForStudent__ - Adding Correction Exam to Student with matching id
* __addNewStudentToClass__ - Adding new Student to the Class
* __createNewTeacher__ - Converts DTO Object and save it to Database
#### PUT
* __moveExamToAnotherDay__ - Moving exam from one day to another
* __changeClassPresident__ - Changing class president
* __updateStudent__ - Converts DTO Object, update Teacher with matching id and save it to database

#### DELETE
* __deleteTeacherById__ - Delete Teacher with matching id
* __removeStudentFromClass__ - Removes Student from the Class

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
* __getSportTeamById__ -  Return Sport Team with matching id
* __getSportTeamByName__ - Return Sport Team with matching name
* __getSportTeamByPresident__ - Return List of Sport Teams with matching president
* __getTeamsForSport__ - Return Map where they key is Matching Sport and values Lists of Sport Teams grouped by President
* __getTeamsGroupedBySport__ - Map where the keys are Sports and values Lists of Sport Teams grouped by President
* __getSportTeamsByStudentsSize__ - Map where the keys are numbers of Students and values Lists of Sport Teams
#### POST
* __createNewSportTeam__ - Converts DTO Object and save it to Database
#### PUT
* __updateSportTeam__ - Converts DTO Object, update Sport Team with matching id and save it to database
#### DELETE
* __deleteStudentClassById__ - Delete Sport Team with matching id

## <div align="center"> Exam </div>
#### GET
* __getAllExams__ - Return all Exams
* __getExamById__ -  Return Exam with matching id
* __getExamByName__ -  Return Exam with matching name
* __getAllExamsForTeacher__ -  Return List of Exams for Teacher with matching firstName and lastName
* __getExamsForSubject__ -  Return Map where the Key is matching Subject and values List of Exams
* __getAllExamsBySubjectsAndTeachers__ -  Map where the Keys are Subjects and values Maps
where they Keys are Teachers and values List of exams
* __getAllExamsByStudentsAndSubjects__ -  Map where the Keys are Number of Students and
values Maps where they Keys are Subjects and values List of exams
#### POST
* __createNewExam__ - Converts DTO Object and save it to Database
#### PUT
* __updateExam__ - Converts DTO Object, update Exam with matching id and save it to database
#### DELETE
* __deleteExamById__ - Delete Exam with matching id

## <div align="center"> Exam Result </div>
#### GET
* __getAllExamResults__ - Return all Exam Results
* __getExamResultByID__ - Return Exam Result with matching id
* __getAllPassedExamResults__ - Return all Exam Result with Grade higher than F
* __getAllNotPassedExamResults__ - Return all Exam Result with Grade F
* __getAllPassedForSubject__ - Return all Passed Exams with matching Subject
* __getAllNotPassedForSubject__ - all Not Failed Exams with matching Subject
* __getResultsGroupedByGradeAndName__ - Return Exam Results groupedBy -> Grade ->  Name
* __getResultGroupedByDateAndGrade__ - Return Exam Results groupedBy -> Date -> Grade
#### POST
* __createExamResult__ - Save valid object to database
#### PUT
* __updateExamResult__ -  update Exam Result with matching id and save it to database
#### DELETE
* __deleteExamResultByID__ - Delete Exam Result with matching id

## <div align="center"> Subject </div>
#### GET
* __getAllSubjects__ - Return all Subjects
* __getSubjectByID__ - Return Subject with matching id
* __getSubjectByName__ - Return Subject with matching name
* __getSubjectsWithFullValue__ - List of Subjects with full point value
* __getSubjectsWithLowestValue__ - List of Subjects with lowest point value
#### POST
* __createNewSubject__ - Save valid object to database
#### PUT
* __updateSubject__ -  Update Subject with matching id and save it to database
#### DELETE
* __deleteSubjectByID__ - Delete Subject with matching id
