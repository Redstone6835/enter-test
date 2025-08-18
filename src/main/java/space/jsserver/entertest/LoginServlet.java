package space.jsserver.entertest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import space.jsserver.entertest.model.DBUtil;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() {}

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            if (check(name, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);

                out.println("<html><head>");
                out.println("<meta http-equiv='refresh' content='1;url=" + request.getContextPath() + "/diaoyan.jsp'>");
                out.println("<title>登录成功</title></head><body>");
                out.println("<h1>登录成功，正在自动跳转到答题页...</h1>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h1>登录失败，请检查输入的信息是否正确！</h1>");
                out.println("</body></html>");
                request.setAttribute("errorMessage", "用户名或序列号错误！");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean check(String name, String password) throws SQLException {
        Connection conn = DBUtil.getConnection();

        String sql = "SELECT COUNT(*) FROM user_application WHERE username = ? AND serial = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;  // 有记录就返回true
            }
            return false;
        }
    }
}