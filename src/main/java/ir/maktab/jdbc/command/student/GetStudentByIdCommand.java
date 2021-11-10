package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class GetStudentByIdCommand implements BaseCommand {
    StudentService studentService;

    public GetStudentByIdCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter student id:");
        int id = Input.getIntInputValue("");
        Student student = studentService.loadById(id);
        System.out.println(student);
    }
}
