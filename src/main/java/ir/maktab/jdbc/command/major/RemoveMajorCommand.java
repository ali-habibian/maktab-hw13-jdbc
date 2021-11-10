package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class RemoveMajorCommand implements BaseCommand {
    MajorService majorService;

    public RemoveMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter major id:");
        int majorId = Input.getIntInputValue("");
        majorService.deleteById(majorId);
    }
}
