package space.jsserver.entertest.problem;

import space.jsserver.entertest.model.Hobby;

import java.util.ArrayList;

public class ProblemBuilder {
    private String id;
    private String description;
    private boolean isImg;
    private String imgUrl;
    private Hobby hobby;
    public ProblemBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProblemBuilder setHobby(Hobby hobby) {
        this.hobby = hobby;
        return this;
    }

    public ArrayList<Problem> randomGenerate() {
        Problem problem = new Problem();
        problem.setId(id);

        // TODO: 题目生成逻辑
        return null;
    }
}
