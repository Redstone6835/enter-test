package space.jsserver.entertest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import space.jsserver.entertest.model.DBUtil;
import space.jsserver.entertest.model.Hobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "answer", value = "/answer")
public class GetAnswerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String serial = (String) session.getAttribute("password");

        String[] result = parse(sb.toString());
        for (int i = 0; i < result.length; i++) {
            try {
                updateResult(result[i], i, serial);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        JsonObject obj = JsonParser.parseString(sb.toString()).getAsJsonObject()
                .getAsJsonObject("survey_result").getAsJsonObject("data");

        String gameid = obj.get("gameid").getAsString();
        String age = obj.get("age").getAsString();
        Hobby hobby = Hobby.valueOf(obj.get("hobby").getAsString());
        try {
            updateSql("game_id", gameid, serial);
            updateSql("age", age, serial);
            updateSql("specialty", hobby.name(), serial);

            Connection conn = DBUtil.getConnection();
            String sql = "UPDATE user_application " +
                    "SET taken = ? " +
                    "WHERE serial = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setString(2, serial);
            ps.executeUpdate();

            session.setAttribute("msg", "答题成功，感谢您的参与！");
            session.setAttribute("login", false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateResult(String result, int index, String serial) throws Exception {
        Connection conn = DBUtil.getConnection();

        String sql = "UPDATE user_application " +
                "SET " + ("a" + (index + 1)) + " = ? " +
                "WHERE serial = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, result);
        ps.setString(2, serial);
        ps.executeUpdate();
    }

    public static void updateSql(String name, String value, String serial) throws Exception {
        Connection conn = DBUtil.getConnection();

        String sql = "UPDATE user_application " +
                "SET " + (name) + " = ? " +
                "WHERE serial = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, value);
        ps.setString(2, serial);
        ps.executeUpdate();
    }

    public static String[] parse(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("result");
        List<String> list = new ArrayList<>();
        while (obj != null && !obj.isJsonNull()) {
            list.add(obj.getAsJsonObject("data").get("result").getAsString());
            obj = obj.get("next").isJsonNull() ? null : obj.getAsJsonObject("next");
        }
        return list.toArray(new String[0]);
    }
}
