package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CourseDao implements BaseDao<Course, Integer> {
    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();

    @Override
    public void save(Course entity) {
        //language=MySQL
        String query = "INSERT INTO maktab.course (name, unit) VALUES (?,?)";
        try (
                Connection connection = dataSourceConfig.createDataSource().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getUnit());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not insert data to db");
        }
    }

    @Override
    public void update(Integer id, Course newEntity) {
        //language=MySQL
        String query = "UPDATE maktab.course SET name=?, unit=? WHERE id=?";
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Course loadById(Integer id) {
        return null;
    }

    @Override
    public List<Course> loadAll() {
        return null;
    }

}
