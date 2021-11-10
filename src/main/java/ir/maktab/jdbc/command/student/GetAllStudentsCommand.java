package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;

import java.util.List;

public class GetAllStudentsCommand implements BaseCommand {
    StudentService studentService;

    public GetAllStudentsCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        List<Student> students = studentService.loadAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
