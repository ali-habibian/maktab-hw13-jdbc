package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class AddCourseCommand implements BaseCommand {
    private CourseService courseService;

    public AddCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }


    @Override
    public void execute() {
        Printer.printMessage("Enter course name:");
        String courseName = Input.getStringInputValue("");
        Printer.printMessage("Enter course unit:");
        int courseUnit = Input.getIntInputValue("");
        courseService.saveOrUpdate(new Course(courseName, courseUnit));
    }
}
