package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.HashMap;
import java.util.Map;

public class CourseCommands implements BaseCommand {

    Map<Integer, BaseCommand> courseCommandsMap = new HashMap<>();

    public CourseCommands(CourseService courseService) {
        courseCommandsMap.put(1, new AddCourseCommand(courseService));
        courseCommandsMap.put(2, new UpdateCourseCommand(courseService));
        courseCommandsMap.put(3, new RemoveCourseCommand(courseService));
        courseCommandsMap.put(4, new GetCourseByIdCommand(courseService));
        courseCommandsMap.put(5, new GetAllCoursesCommand(courseService));
    }

    @Override
    public void execute() {
        int command = 0;
        while (command != 6) {
            Printer.printMessage("1. Add course");
            Printer.printMessage("2. Update course");
            Printer.printMessage("3. Remove course");
            Printer.printMessage("4. Get course by id");
            Printer.printMessage("5. Get all courses");
            Printer.printMessage("6. Back");
            command = Input.getIntInputValue("");
            if (command > 6) {
                System.out.println("invalid command");
            } else if (command < 6) {
                courseCommandsMap.get(command).execute();
            }
        }
    }
}
