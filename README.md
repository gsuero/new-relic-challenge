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

#### Sample:
Command:

`# java -jar ./build/libs/word-combination-frequency-finder-all.jar text-files/test.txt`

Output:
```console
100 of most common three word sequences in descending order of frequency
test.txt:
_______________________________________________________________
WORDS                                             | OCCURRENCES
_______________________________________________________________
suero can't read                                  |           2
garis manuel suero                                |           2
suero likes to                                    |           1
garis m suero                                     |           1
on odd days                                       |           1
m suero can't                                     |           1
manuel suero likes                                |           1
read most of                                      |           1
manuel suero can't                                |           1
sometimes garis manuel                            |           1
can't read sometimes                              |           1
but garis m                                       |           1
to read most                                      |           1
read sometimes garis                              |           1
most of the                                       |           1
of the time                                       |           1
the time but                                      |           1
can't read on                                     |           1
likes to read                                     |           1
time but garis                                    |           1
read on odd                                       |           1

###############################################################
```
### How to run the tests
To execute unit tests just run the below command:
    - `./gradlew test`

### Ideas of improvement 
- Make the quantity of consecutive letters parametrized 
- Use Alphabetical ordering for multiple word occurrences with same occurrence value
- Dockerize application
- Improve performance with parallelism ?

### Known bugs
- Account for letter variations, i.e. (Don't, Don`t) ?
- STDIN not working with `cat ...` example
