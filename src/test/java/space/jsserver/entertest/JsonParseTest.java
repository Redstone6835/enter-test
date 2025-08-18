package space.jsserver.entertest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class JsonParseTest {
    @Test
    public void test() {
        String json = "{\"result\":{\"data\":{\"id\":1,\"result\":\"1\"},\"next\":{\"data\":{\"id\":2,\"result\":\"2\"},\"next\":{\"data\":{\"id\":3,\"result\":\"3\"},\"next\":{\"data\":{\"id\":4,\"result\":\"4\"},\"next\":{\"data\":{\"id\":5,\"result\":\"5\"},\"next\":{\"data\":{\"id\":6,\"result\":\"6\"},\"next\":{\"data\":{\"id\":7,\"result\":\"7\"},\"next\":{\"data\":{\"id\":8,\"result\":\"8\"},\"next\":{\"data\":{\"id\":9,\"result\":\"9\"},\"next\":null}}}}}}}}},\"survey_result\":{\"data\":{\"gameid\":\"打\",\"age\":\"12\",\"hobby\":\"SURVIVAL\"},\"timestamp\":1755536356796}}";        // 调用 parse 方法并打印结果
        System.out.println(Arrays.toString(GetAnswerServlet.parse(json)));
    }
}
