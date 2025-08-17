package space.jsserver.entertest;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "survey", value = "/survey")
public class GetSurveyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        System.out.println(json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write("{\"head\":{\"data\":{\"id\":1,\"description\":\"请输入你的姓名\",\"isImg\":false,\"imgUrl\":\"\"},\"next\":{\"data\":{\"id\":2,\"description\":\"请看下面图片并回答问题\",\"isImg\":true,\"imgUrl\":\"https://example.com/image1.jpg\"},\"next\":null}}}");
    }
}
