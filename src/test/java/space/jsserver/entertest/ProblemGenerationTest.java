package space.jsserver.entertest;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import space.jsserver.entertest.model.Hobby;
import space.jsserver.entertest.problem.Problem;
import space.jsserver.entertest.problem.ProblemBuilder;

import java.util.ArrayList;

public class ProblemGenerationTest {
    public static String type = "SURVIVAL";
    @Test
    public void testProblemGeneration() {
        ProblemBuilder builder = new ProblemBuilder();
        ArrayList<Problem> generatedProblems = builder
                .setHobby(Hobby.valueOf(type))
                .randomGenerate();
        String jsonResponse = new Gson().toJson(generatedProblems);
        System.out.println("Generated Problems: " + jsonResponse);
        System.out.println("Problem generation test executed.");
    }
}
