package space.jsserver.entertest.model;

public enum Hobby {
    DIGITAL("数电"),
    SURVIVAL("生电"),
    BUILDING("建筑"),
    MECHANICAL("械电"),
    CODING("编程"),
    NONE("无");
    public final String name;
    Hobby(String name) {
        this.name = name;
    }
}
