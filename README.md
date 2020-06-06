# <div align="center"> REST API High School</div>

## <div align="center"> [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/AdrianRomanski/rest-school.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/AdrianRomanski/rest-school/context:java) [![codecov](https://codecov.io/gh/AdrianRomanski/rest-school/branch/master/graph/badge.svg)](https://codecov.io/gh/AdrianRomanski/rest-school) [![CircleCI](https://circleci.com/gh/AdrianRomanski/rest-school.svg?style=shield)](https://circleci.com/gh/AdrianRomanski/rest-school) ![Total alerts](https://img.shields.io/lgtm/alerts/g/AdrianRomanski/rest-school.svg?logo=lgtm&logoWidth=18) [![Generic badge](https://img.shields.io/badge/Status-Progress-<COLOR>.svg)](https://shields.io/) [![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Naereen/StrapDown.js/blob/master/LICENSE)
</div>
    
## <div align="left"> Running rest-school locally </div>

    git clone git@github.com:AdrianRomanski/rest-school.git
    cd rest-school
    mvn package
    java -jar target/rest-school-0.0.1-SNAPSHOT.jar

## <div align="left"> Working with rest-school in your IDE

### Prerequisites

The following items should be installed in your system:

- Java 8 or newer.
- git command line tool (https://help.github.com/articles/set-up-git)
- Your preferred IDE
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [Eclipse](https://www.eclipse.org/)
    - [Spring Tools Suite](https://spring.io/tools)
    - [VS Code](https://code.visualstudio.com/)

### Steps:

## 1) On the command line
git clone git@github.com:AdrianRomanski/rest-school.git

## 2) IDE
### IntelliJ IDEA
- In the main menu, choose File -> Open and select the pom.xml inside rest-school directory.
- Click on the Open as Project button.
- Run -> Run 'RestSchoolApplication'

### Eclipse or STS
- File -> Import -> Maven -> Existing Maven project
- Run the application main method by right clicking on it and choosing Run As -> Java Application.

## 3) Endpoints
- Visit http://localhost:8080/swagger-ui.html#/ in your browser for documentation
- Visit http://localhost:8080/h2 in your browser to inspect the content of the database
    - JDBC URL: jdbc:h2:mem:school
    - User Name: sa
- Visit http://localhost:8080/actuator/info in your browser for basic informations about application
- Visit http://localhost:8080/actuator/health in your browser to inspect status of application
- Visit http://localhost:8080/actuator/ in your browser for list of all actuator endpoints
