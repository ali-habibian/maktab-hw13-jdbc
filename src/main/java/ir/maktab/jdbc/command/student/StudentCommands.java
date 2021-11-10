package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.HashMap;
import java.util.Map;

public class StudentCommands implements BaseCommand {
    Map<Integer, BaseCommand> studentCommandsMap = new HashMap<>();

    public StudentCommands(StudentService studentService) {
        studentCommandsMap.put(1, new AddStudentCommand(studentService));
        studentCommandsMap.put(2, new UpdateStudentCommand(studentService));
        studentCommandsMap.put(3, new RemoveStudentCommand(studentService));
        studentCommandsMap.put(4, new GetStudentByIdCommand(studentService));
        studentCommandsMap.put(5, new GetAllStudentsCommand(studentService));
    }

    @Override
    public void execute() {
        int command = 0;
        while (command != 6) {
            Printer.printMessage("1. Add student");
            Printer.printMessage("2. Update student");
            Printer.printMessage("3. Remove student");
            Printer.printMessage("4. Get student by id");
            Printer.printMessage("5. Get all students");
            Printer.printMessage("6. Back");
            command = Input.getIntInputValue("");
            if (command > 6) {
                System.out.println("invalid command");
            } else if (command < 6) {
                studentCommandsMap.get(command).execute();
            }
        }
    }
}
