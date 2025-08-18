package space.jsserver.entertest;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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

        if (name != null && !name.isEmpty() && password != null && !password.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("name", name);

            out.println("<html><head>");
            out.println("<meta http-equiv='refresh' content='3;url=" + request.getContextPath() + "/diaoyan.jsp'>");
            out.println("<title>登录成功</title></head><body>");
            out.println("<h1>登录成功，3 秒后自动跳转到主页...</h1>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h1>Login failed. Please enter valid credentials.</h1>");
            out.println("</body></html>");
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}