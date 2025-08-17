package space.jsserver.entertest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import space.jsserver.entertest.model.Hobby;
import space.jsserver.entertest.problem.Problem;
import space.jsserver.entertest.problem.ProblemBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "survey", value = "/survey")
public class GetSurveyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String type = new Gson().fromJson(sb.toString(), JsonObject.class).get("type").getAsString();

        // resp.getWriter().write("{\"head\":{\"data\":{\"id\":1,\"description\":\"请输入你的姓名\",\"isImg\":false,\"imgUrl\":\"\"}}}");

        // 根据 type 生成问卷内容
        ProblemBuilder builder = new ProblemBuilder();
        ArrayList<Problem> generatedProblems = builder
                .setHobby(Hobby.valueOf(type))
                .randomGenerate();


        String jsonResponse = new Gson().toJson(generatedProblems);
        System.out.println("Generated Problems: " + jsonResponse);
        resp.getWriter().write(jsonResponse);
    }
}
