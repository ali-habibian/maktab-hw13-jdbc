package ir.maktab.jdbc;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.command.course.CourseCommands;
import ir.maktab.jdbc.command.major.MajorCommands;
import ir.maktab.jdbc.command.student.StudentCommands;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MajorService majorService = new MajorService();
        CourseService courseService = new CourseService();
        StudentService studentService = new StudentService();

        Map<Integer, BaseCommand> mainMenuCommandsMap = new HashMap<>();
        mainMenuCommandsMap.put(1, new StudentCommands(studentService));
        mainMenuCommandsMap.put(2, new CourseCommands(courseService));
        mainMenuCommandsMap.put(3, new MajorCommands(majorService));

        int command = 0;
        while (command != 4) {
            Printer.printMessage("1. Students menu");
            Printer.printMessage("2. Courses menu");
            Printer.printMessage("3. Majors menu");
            Printer.printMessage("4. Exit");
            command = Input.getIntInputValue("");
            if (command > 4) {
                System.out.println("invalid command");
            } else if (command < 4) {
                mainMenuCommandsMap.get(command).execute();
            } else {
                System.out.println("Exited");
            }
        }
    }
}
