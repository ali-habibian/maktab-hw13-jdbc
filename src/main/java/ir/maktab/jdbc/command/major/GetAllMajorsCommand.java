package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utilities.Input;
import ir.maktab.jdbc.utilities.Printer;

import java.util.List;

public class GetAllMajorsCommand implements BaseCommand {
    MajorService majorService;

    public GetAllMajorsCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        List<Major> majors = majorService.loadAll();
        for (Major major : majors) {
            System.out.println(major);
        }
    }
}
