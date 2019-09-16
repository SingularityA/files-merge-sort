# files-merge-sort

The program merges few sorted files into one sorted one.
Values out of order are ignored.

To run the example you may open the project in IntelliJ IDEA
(or import it in another popular IDE), click 'Edit Configurations'
set CLI arguments in 'Program arguments' window, apply and 
run main() function in Application class through IDE.

Alternatively you can build and run the app with gradle like this:
```
gradlew run --args="-d -i output.txt input1.txt input2.txt input3.txt"
./gradlew run --args="-d -i output.txt input1.txt input2.txt input3.txt"
```
Or simply run batch file like that:
```
merge-files -d -i output.txt input1.txt input2.txt input3.txt
./merge-files -d -i output.txt input1.txt input2.txt input3.txt
```
You may also check out tests and run them through IDE or gradle.

The path to result file is '\<you project dir>\src\main\resources\output\output.txt'
('\<you project dir>\src\test\resources\output\output.txt' for tests).