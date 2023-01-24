# Frequency finder

### How to execute
To execute this project follow the below steps:
1. Clone this project
2. In the project directory root execute the following command to build the fat jar:
   - `./gradlew shadowJar`
3. To execute the program:
   - With input files
     - `java -jar ./build/libs/word-combination-frequency-finder-all.jar <text file> <text file> ...`
   - With STDIN
     - `java -jar ./build/libs/word-combination-frequency-finder-all.jar --stdin`
This will print the 3 word combination frequency of as many files as provided.

### How to run the tests
To execute unit tests just run the below command:
    - `./gradlew test`

### Ideas of improvement
- Make the quantity of consecutive letters parametrized 
- Use Alphabetical ordering for multiple word occurrences with same occurrence value

### Known bugs
- Account for letter variations, i.e. (Don't, Don`t) ?