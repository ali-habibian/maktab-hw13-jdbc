package ir.maktab.jdbc.service;

import ir.maktab.jdbc.dao.StudentDao;
import ir.maktab.jdbc.entity.Student;

public class StudentService extends AbstractCrudService<Student, Integer> {
    public StudentService() {
        setBaseDao(new StudentDao());
    }

    @Override
    public StudentDao getBaseDao() {
        return (StudentDao) super.getBaseDao();
    }
}
