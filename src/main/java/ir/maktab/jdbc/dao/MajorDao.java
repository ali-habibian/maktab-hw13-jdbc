package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDao implements BaseDao<Major, Integer> {
    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;

    @Override
    public void save(Major entity) {
        //language=MySQL
        String query = "INSERT INTO maktab.major (name) VALUES (?)";
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, entity.getName());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not insert major to db");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Integer id, Major newEntity) {
        //language=MySQL
        String query = "UPDATE maktab.major SET name=? WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newEntity.getName());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update major to db");
        }
    }

    @Override
    public void delete(Integer id) {
        //language=MySQL
        String query = "DELETE FROM maktab.major WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not delete major from db");
        }
    }

    @Override
    public Major loadById(Integer id) {
        //language=MySQL
        String query = "SELECT * FROM maktab.major WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                Major major = null;
                while (resultSet.next()) {
                    int majorId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    major = new Major(majorId, name);
                }
                return major;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find major in db");
        }
    }

    @Override
    public List<Major> loadAll() {
        //language=MySQL
        String query = "SELECT * FROM maktab.major";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            List<Major> majors = new ArrayList<>();
            while (resultSet.next()) {
                int majorId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Major major = new Major(majorId, name);
                majors.add(major);
            }
            return majors;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find majors in db");
        }
    }
}
