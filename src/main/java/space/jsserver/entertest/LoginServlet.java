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
        HttpSession session = request.getSession();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            if (check(name, password)) {
                if (isTaken(password)) {
                    session.setAttribute("finished", true);
                    session.setAttribute("msg", "您已经完成了答题，请耐心等待结果。如果需要重新填写，请联系管理员。");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    return;
                }

                session.setAttribute("name", name);
                session.setAttribute("password", password);
                session.setAttribute("got", false);
                session.setAttribute("login", true);
                session.setAttribute("finished", false);

                out.println("<html><head>");
                out.println("<meta http-equiv='refresh' content='1;url=" + request.getContextPath() + "/diaoyan.jsp'>");
                out.println("<title>登录成功</title></head><body>");
                out.println("<h1>登录成功，正在自动跳转到答题页...</h1>");
                out.println("</body></html>");
            } else {
                request.setAttribute("msg", "登录失败，请检查输入的信息是否正确！");
                request.setAttribute("errorMessage", "用户名或序列号错误！");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
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

    private boolean isTaken(String serial) throws SQLException {
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT taken FROM user_application WHERE serial = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serial);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("taken");
            }
            return false;
        }
    }
}