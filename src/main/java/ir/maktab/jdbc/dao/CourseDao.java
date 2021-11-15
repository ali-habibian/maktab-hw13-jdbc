package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.exception.DataNotFoundException;
import ir.maktab.jdbc.exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            throw new ModificationDataException("Can not insert course to db");
        }
    }

    @Override
    public void update(Integer id, Course newEntity) {
        //language=MySQL
        String query = "UPDATE maktab.course SET name=?, unit=? WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newEntity.getName());
            ps.setInt(2, newEntity.getUnit());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update course");
        }
    }

    @Override
    public void delete(Integer id) {
        //language=MySQL
        String query = "DELETE FROM maktab.course WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not delete course from db");
        }
    }

    @Override
    public Course loadById(Integer id) {
        //language=MySQL
        String query = "SELECT * FROM maktab.course WHERE id=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                Course course = null;
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int unit = resultSet.getInt("unit");
                    course = new Course(courseId, name, unit);
                }
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find course in db");
        }
    }

    @Override
    public List<Course> loadAll() {
        //language=MySQL
        String query = "SELECT * FROM maktab.course";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int unit = resultSet.getInt("unit");
                Course course = new Course(courseId, name, unit);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find courses in db");
        }
    }

    public Set<Course> loadByStudentId(Integer studentId) {
        //language=MySQL
        Set<Course> courses = new HashSet<>();
        String query = "SELECT course_id_fk FROM maktab.student_course WHERE student_id_fk = ?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ResultSet courseIdResultSet = ps.executeQuery();
            while (courseIdResultSet.next()) {
                int courseId = courseIdResultSet.getInt("course_id_fk");
                Course course = loadById(courseId);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find courses in db");
        }
        return courses;
    }
}
