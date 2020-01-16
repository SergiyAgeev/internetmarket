package mate.academy.internetshop.model;

import mate.academy.internetshop.lib.IdGenerator;

public class User {
    private Long id;
    private String name;
    private String secondName;
    private int age;
    private String login;
    private String password;
    private String token;

    public User() {
        this.id = IdGenerator.generateNewUserId();
    }

    public User(String name, String secondName, String login, String password, int age) {
        this();
        this.name = name;
        this.secondName = secondName;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public String getSecondName() {
        return secondName;
    }

    public User setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", name='" + name + '\''
                + ", secondName='" + secondName + '\''
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + ", age=" + age + '}';
    }
}
