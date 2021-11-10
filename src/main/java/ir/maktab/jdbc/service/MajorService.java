package ir.maktab.jdbc.service;

import ir.maktab.jdbc.dao.MajorDao;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Major;

public class MajorService extends AbstractCrudService<Major, Integer> {
    public MajorService() {
        setBaseDao(new MajorDao());
    }

    @Override
    public MajorDao getBaseDao() {
        return (MajorDao) super.getBaseDao();
    }
}
