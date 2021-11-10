package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.HashSet;
import java.util.Set;

public class UpdateStudentCommand implements BaseCommand {
    StudentService studentService;

    public UpdateStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter student id:");
        int studentId = Input.getIntInputValue("");
        Printer.printMessage("Enter student new name:");
        String name = Input.getStringInputValue("");
        Printer.printMessage("Enter student new family name:");
        String familyName = Input.getStringInputValue("");
        Printer.printMessage("Enter student new major id:");
        int majorId = Input.getIntInputValue("");
        Major major = new Major(majorId);
        Set<Course> courseList = new HashSet<>();
        boolean continueAdding = true;
        while (continueAdding) {
            System.out.println("enter new course id or write (e) to exit adding courses:");
            String courseIdString = Input.getStringInputValue("");
            try {
                int courseId = Integer.parseInt(courseIdString);
                courseList.add(new Course(courseId));
            } catch (NumberFormatException e) {
                continueAdding = false;
            }
        }
        Student student = new Student.StudentBuilder()
                .id(studentId)
                .name(name)
                .familyName(familyName)
                .major(major)
                .courses(courseList).build();
        studentService.saveOrUpdate(student);
    }
}
