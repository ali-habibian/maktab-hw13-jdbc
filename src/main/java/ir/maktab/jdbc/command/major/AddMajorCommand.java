package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

public class AddMajorCommand implements BaseCommand {
    MajorService majorService;

    public AddMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        Printer.printMessage("Enter major name:");
        String majorName = Input.getStringInputValue("");
        majorService.saveOrUpdate(new Major(majorName));
    }
}
