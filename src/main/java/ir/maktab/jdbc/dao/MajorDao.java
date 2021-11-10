package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Major;

import java.util.List;

public class MajorDao implements BaseDao<Major, Integer> {
    @Override
    public void save(Major entity) {

    }

    @Override
    public void update(Integer id, Major newEntity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Major loadById(Integer id) {
        return null;
    }

    @Override
    public List<Major> loadAll() {
        return null;
    }
}
