package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class RemoveCourseCommand implements BaseCommand {
    CourseService courseService;

    public RemoveCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter course id:");
        int courseId = Input.getIntInputValue("");
        courseService.deleteById(courseId);
    }
}
