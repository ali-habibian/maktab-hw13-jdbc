package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;

import java.util.List;

public class GetAllCoursesCommand implements BaseCommand {
    CourseService courseService;

    public GetAllCoursesCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        List<Course> courses = courseService.loadAll();
        for (Course course : courses) {
            System.out.println(course);
        }
    }
}
