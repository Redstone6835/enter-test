package space.jsserver.entertest.controller;

import com.google.gson.Gson;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import space.jsserver.entertest.model.Hobby;
import space.jsserver.entertest.problem.Problem;
import space.jsserver.entertest.problem.ProblemBuilder;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "generateProblem", value = "/generateProblem")
public class GenerateProblemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Problem problem = new Problem();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        class ProblemRequest {
            public String id;
            public String type;
        }

        JsonReader reader = Json.createReader(req.getReader());
        String id = reader.readObject().getString("id");
        Hobby hobby = Hobby.valueOf(reader.readObject().getString("type"));

        ProblemBuilder builder = new ProblemBuilder();
        ArrayList<Problem> problems = builder.setId(id)
                .setHobby(hobby)
                .randomGenerate();

        resp.getWriter().write(new Gson().toJson(problems));
        req.setAttribute("problems", problems);
    }
}
