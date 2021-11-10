package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class GetCourseByIdCommand implements BaseCommand {
    CourseService courseService;

    public GetCourseByIdCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter course id:");
        int courseId = Input.getIntInputValue("");
        Course course = courseService.loadById(courseId);
        System.out.println(course);
    }
}
