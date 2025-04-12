import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String user_type = request.getParameter("type");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FoodWasteDB", "root", "Sachin@2005");
            
            
            String sql = "INSERT INTO users (name, email, password, user_type) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, user_type);
            
            int rowsInserted = pst.executeUpdate();
            
            if (rowsInserted > 0) {
                
                response.sendRedirect("index.html?register=success");
            } else {
                response.sendRedirect("index.html?register=error");
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?register=error");
        }
    }
}