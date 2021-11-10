package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.HashMap;
import java.util.Map;

public class MajorCommands implements BaseCommand {
    Map<Integer, BaseCommand> majorCommandsMap = new HashMap<>();

    public MajorCommands(MajorService majorService) {
        majorCommandsMap.put(1, new AddMajorCommand(majorService));
        majorCommandsMap.put(2, new UpdateMajorCommand(majorService));
        majorCommandsMap.put(3, new RemoveMajorCommand(majorService));
        majorCommandsMap.put(4, new GetMajorByIdCommand(majorService));
        majorCommandsMap.put(5, new GetAllMajorsCommand(majorService));
    }

    @Override
    public void execute() {
        int command = 0;
        while (command != 6) {
            Printer.printMessage("1. Add major");
            Printer.printMessage("2. Update major");
            Printer.printMessage("3. Remove major");
            Printer.printMessage("4. Get major by id");
            Printer.printMessage("5. Get all majors");
            Printer.printMessage("6. Back");
            command = Input.getIntInputValue("");
            if (command > 6) {
                System.out.println("invalid command");
            } else if (command < 6) {
                majorCommandsMap.get(command).execute();
            }
        }
    }
}
