package demo.springboot.pinyin;

import java.io.Serializable;

public class UserVO implements Serializable {
    private String name;
    private Integer age;

    public UserVO() {
    }

    public UserVO(String name) {
        this.name = name;
    }

    public UserVO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
