import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;databaseName=StudentManagement_DB;user=sa;password=123456789;encrypt=true;trustServerCertificate=true";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(CONNECTION_URL)) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nChoose an operation:");
                System.out.println("1. Create student");
                System.out.println("2. Retrieve all students");
                System.out.println("3. Update student");
                System.out.println("4. Delete student");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createStudent(con, scanner);
                        break;
                    case 2:
                        getAllStudents(con);
                        break;
                    case 3:
                        updateStudent(con, scanner);
                        break;
                    case 4:
                        deleteStudent(con, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createStudent(Connection con, Scanner scanner) throws SQLException {
        System.out.println("Enter student details:");
        System.out.print("Id: ");
        String Id = scanner.nextLine();
        System.out.print("StudentId: ");
        String studentId = scanner.nextLine();
        System.out.print("FirstName: ");
        String firstName = scanner.nextLine();
        System.out.print("LastName: ");
        String lastName = scanner.nextLine();
        System.out.print("Birthday: ");
        String birthday = scanner.nextLine();
        System.out.print("Class: ");
        String className = scanner.nextLine();

        String SQL = "INSERT INTO Students (Id, StudentId, FirstName, LastName, Birthday, Class) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, Id);
            pstmt.setString(2, studentId);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setDate(5, Date.valueOf(birthday));
            pstmt.setString(6, className);
            pstmt.executeUpdate();
            System.out.println("Student created successfully.");
        }
    }

    private static void getAllStudents(Connection con) throws SQLException {
        String SQL = "SELECT * FROM Students";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
            System.out.println("All students:");
            while (rs.next()) {
                System.out.println(rs.getString("Id") + " " + rs.getString("StudentId") + " " + rs.getString("FirstName") + " " + rs.getString("LastName") + " " + rs.getString("Birthday") + " " + rs.getString("Class"));
            }
        }
    }

    private static void updateStudent(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to update: ");
        String studentId = scanner.nextLine();
    
        System.out.println("Enter new student details:");
        System.out.print("FirstName: ");
        String firstName = scanner.nextLine();
        System.out.print("LastName: ");
        String lastName = scanner.nextLine();
        System.out.print("Birthday: ");
        String birthday = scanner.nextLine();
        System.out.print("Class: ");
        String className = scanner.nextLine();
        System.out.print("Grade: ");
        String grade = scanner.nextLine();
    
        String SQL = "UPDATE Students SET FirstName =?, LastName =?, Birthday =?, Class =?, Grade =? WHERE StudentId =?";
        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, Date.valueOf(birthday));
            pstmt.setString(4, className);
            pstmt.setString(5, grade);
            pstmt.setString(6, studentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        }
    }

    private static void deleteStudent(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        String studentId = scanner.nextLine();

        String SQL = "DELETE FROM Students WHERE StudentId =?";
        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        }
    }
}