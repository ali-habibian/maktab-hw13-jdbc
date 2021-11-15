package ir.maktab.jdbc.dao;

import ir.maktab.jdbc.config.DataSourceConfig;
import ir.maktab.jdbc.dao.core.BaseDao;
import ir.maktab.jdbc.entity.Course;
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
import java.util.Set;

public class StudentDao implements BaseDao<Student, Integer> {

    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;

    @Override
    public void save(Student entity) {
        //language=MySQL
        String query = "INSERT INTO maktab.student (name, family_name, m_id_fk) VALUES (?,?,?)";
        //language=MySQL
        String getStudentIdQuery = "SELECT LAST_INSERT_ID(id) FROM maktab.student";
        int studentId = 0;
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement(query);
                    PreparedStatement studentIdPs = connection.prepareStatement(getStudentIdQuery)) {
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getFamilyName());
                ps.setInt(3, entity.getMajor().getId());
                ps.executeUpdate();
                ResultSet resultSet = studentIdPs.executeQuery();
                while (resultSet.next())
                    studentId = resultSet.getInt(1);
                resultSet.close();
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
        addStudentAndCourseIdToMiddleTable(studentId, entity.getCourses());
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
        deleteStudentAllCourseIdsFromMiddleTable(id);
        addStudentAndCourseIdToMiddleTable(id, newEntity.getCourses());
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
                    CourseDao courseDao = new CourseDao();
                    MajorDao majorDao = new MajorDao();
                    int studentId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String familyName = resultSet.getString("family_name");
                    int majorId = resultSet.getInt("m_id_fk");
                    Major major = majorDao.loadById(majorId);
                    Set<Course> courses = courseDao.loadByStudentId(studentId);
                    student = Student.builder()
                            .id(studentId)
                            .name(name)
                            .familyName(familyName)
                            .major(major)
                            .courses(courses)
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
                Student student = loadById(studentId);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find students in db");
        }
    }

    private void addStudentAndCourseIdToMiddleTable(int studentId, Set<Course> courses) {
        //language=MySQL
        String query = "INSERT INTO maktab.student_course (student_id_fk, course_id_fk) VALUES (?,?)";
        for (Course course : courses) {
            try (Connection connection = dataSourceConfig.createDataSource().getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, studentId);
                ps.setInt(2, course.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModificationDataException("Can not add courses for student in to db");
            }
        }
    }

    private void deleteStudentAllCourseIdsFromMiddleTable(int studentId) {
        //language=MySQL
        String query = "DELETE FROM maktab.student_course WHERE student_id_fk=?";
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find student in db");
        }
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }
}
