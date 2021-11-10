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

public class StudentDao implements BaseDao<Student, Integer> {

    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;

    @Override
    public void save(Student entity) {
        //language=MySQL
        String query = "INSERT INTO maktab.student (name, family_name, m_id_fk) VALUES (?,?,?)";
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getFamilyName());
                ps.setInt(3, entity.getMajor().getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not insert student to db");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Integer id, Student newEntity) {
        //language=MySQL
        String query = "UPDATE maktab.student SET name=?, family_name=?, m_id_fk=? WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newEntity.getName());
            ps.setString(2, newEntity.getFamilyName());
            ps.setInt(3, newEntity.getMajor().getId());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update student to db");
        }
    }

    @Override
    public void delete(Integer id) {
        //language=MySQL
        String query = "DELETE FROM maktab.student WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not delete student from db");
        }
    }

    @Override
    public Student loadById(Integer id) {
        //language=MySQL
        String query = "SELECT * FROM maktab.student WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                Student student = null;
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String familyName = resultSet.getString("family_name");
                    int majorId = resultSet.getInt("m_id_fk");
                    student = Student.builder()
                            .id(studentId)
                            .name(name)
                            .familyName(familyName)
                            .major(new Major(majorId))
                            .build();
                }
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find student in db");
        }
    }

    @Override
    public List<Student> loadAll() {
        //language=MySQL
        String query = "SELECT * FROM maktab.student";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String familyName = resultSet.getString("family_name");
                int majorId = resultSet.getInt("m_id_fk");
                Student student = Student.builder()
                        .id(studentId)
                        .name(name)
                        .familyName(familyName)
                        .major(new Major(majorId))
                        .build();
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find students in db");
        }
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }
}
