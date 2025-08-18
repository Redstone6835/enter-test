package space.jsserver.entertest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import space.jsserver.entertest.model.DBUtil;
import space.jsserver.entertest.model.Hobby;
import space.jsserver.entertest.problem.Problem;
import space.jsserver.entertest.problem.ProblemBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

@WebServlet(name = "survey", value = "/survey")
public class GetSurveyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String type = new Gson().fromJson(sb.toString(), JsonObject.class).get("type").getAsString();
        String serial = (String) session.getAttribute("password");

        // 根据 type 生成问卷内容
        ProblemBuilder builder = new ProblemBuilder();
        ArrayList<Problem> generatedProblems = builder
                .setHobby(Hobby.valueOf(type))
                .randomGenerate();

        if ((Boolean) session.getAttribute("got") == false) {
            for (int j = 0; j < generatedProblems.size(); j++) {
                Problem problem = generatedProblems.get(j);
                try {
                    updateProblem(problem, j, serial);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            String jsonResponse = new Gson().toJson(generatedProblems);
            session.setAttribute("generatedProblems", generatedProblems);
            resp.getWriter().write(jsonResponse);

            session.setAttribute("got", true);
        } else {
            String jsonResponse = new Gson().toJson(session.getAttribute("generatedProblems"));
            resp.getWriter().write(jsonResponse);
        }
    }

    public static void updateProblem(Problem problem, int index, String serial) throws Exception {
        Connection conn = DBUtil.getConnection();

        String sql = "UPDATE user_application " +
                "SET " + ("q" + (index + 1)) + " = ? " +
                "WHERE serial = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, problem.getDescription());
        ps.setString(2, serial);
        ps.executeUpdate();
    }
}
