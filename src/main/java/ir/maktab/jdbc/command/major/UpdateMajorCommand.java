package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class UpdateMajorCommand implements BaseCommand {
    MajorService majorService;

    public UpdateMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter major id:");
        int majorId = Input.getIntInputValue("");
        Printer.printMessage("Enter major new name:");
        String majorName = Input.getStringInputValue("");
        majorService.saveOrUpdate(new Major(majorId, majorName));
    }
}
