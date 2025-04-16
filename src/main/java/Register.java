import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("usertype");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (name, email, password, user_type) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, userType);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Registration success, redirect to login with success message
                response.sendRedirect("index.html?message=success");
            } else {
                // Registration failed
                response.sendRedirect("index.html?message=error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.html?message=error");
        }
    }
}
