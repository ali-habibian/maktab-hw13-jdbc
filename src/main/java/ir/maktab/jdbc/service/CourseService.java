package ir.maktab.jdbc.service;

import ir.maktab.jdbc.dao.CourseDao;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;

public class CourseService extends AbstractCrudService<Course, Integer> {
    public CourseService() {
        setBaseDao(new CourseDao());
    }

    @Override
    public CourseDao getBaseDao() {
        return (CourseDao) super.getBaseDao();
    }
}
