package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class UpdateCourseCommand implements BaseCommand {
    CourseService courseService;

    public UpdateCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter course id:");
        int courseId = Input.getIntInputValue("");
        Printer.printMessage("Enter new name for course:");
        String courseName = Input.getStringInputValue("");
        Printer.printMessage("Enter new unit for course:");
        int courseUnit = Input.getIntInputValue("");
        Course course = new Course(courseId, courseName, courseUnit);
        courseService.saveOrUpdate(course);
    }
}
