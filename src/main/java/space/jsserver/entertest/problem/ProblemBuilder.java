package space.jsserver.entertest.problem;

import space.jsserver.entertest.model.Hobby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProblemBuilder {
    private String id;
    private String description;
    private boolean isImg;
    private String imgUrl;
    private Hobby hobby;
    public ProblemBuilder setHobby(Hobby hobby) {
        this.hobby = hobby;
        return this;
    }

    private static List<Integer> randomNumberGenerate(int max, int n) {
        if (n > max) throw new IllegalArgumentException("n 不能大于 m");

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);
        return nums.subList(0, n);
    }

    public ArrayList<Problem> randomGenerate() {
        List<Integer> basicNumbers = randomNumberGenerate(Problems.BASICS.length, 2);
        List<Integer> redstoneNumbers = randomNumberGenerate(Problems.REDSTONES.length, 1);
        List<Integer> chattingNumbers = randomNumberGenerate(Problems.CHATTING.length, 4);

        ArrayList<Problem> problems = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if (i < 2) {
                int index = basicNumbers.get(i);
                Problem z = Problems.BASICS[index];
                z.setId(String.valueOf(i + 1));
                problems.add(z);
            } else if (i < 3) {
                int index = redstoneNumbers.getFirst();
                problems.add(new Problem(String.valueOf(i + 1), Problems.REDSTONES[index], false, ""));
            } else {
                int index = chattingNumbers.get(i - 3);
                problems.add(new Problem(String.valueOf(i + 1), Problems.CHATTING[index], false, ""));
            }
        }

        switch (hobby) {
            case BUILDING -> {
                problems.get(2).setDescription("你所擅长的建筑风格是什么？");
                problems.add(new Problem("8", "请描述您最喜欢的MC游戏玩法。", false, ""));
                problems.add(new Problem("9", "您加入我服的直接目的是为了玩游戏还是交友？", false, ""));
            }
            case NONE -> {
                problems.add(new Problem("8", "请描述您最喜欢的MC游戏玩法。", false, ""));
                problems.add(new Problem("9", "您加入我服的直接目的是为了玩游戏还是交友？", false, ""));
            }
            case SURVIVAL -> {
                problems.add(new Problem("8",
                        Problems.HIGH_LEVEL_REDSTONE[randomNumberGenerate(2, 2).getFirst()],
                        false, ""));
                problems.add(new Problem("9",
                        Problems.HIGH_LEVEL_SURVIVAL,
                        false, ""));
            }
            case MECHANICAL -> {
                problems.add(new Problem("8",
                        Problems.HIGH_LEVEL_REDSTONE[randomNumberGenerate(2, 2).getFirst()],
                        false, ""));
                problems.add(new Problem("9",
                        Problems.HIGH_LEVEL_REDSTONE[randomNumberGenerate(2, 2).get(1)],
                        false, ""));
            }
            case DIGITAL -> {
                problems.add(new Problem("8",
                        Problems.HIGH_LEVEL_REDSTONE[randomNumberGenerate(2, 2).getFirst()],
                        false, ""));
                problems.add(new Problem("9",
                        Problems.HIGH_LEVEL_DIGITAL,
                        false, ""));
            }
            case CODING -> {
                problems.add(new Problem("8",
                    Problems.HIGH_LEVEL_REDSTONE[randomNumberGenerate(2, 2).get(1)],
                    false, ""));
                problems.add(new Problem("9",
                        Problems.HIGH_LEVEL_CODING[randomNumberGenerate(2, 2).getFirst()],
                        false, ""));

            }
        }
        return problems;
    }
}
