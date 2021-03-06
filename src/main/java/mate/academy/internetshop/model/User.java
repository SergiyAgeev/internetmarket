package mate.academy.internetshop.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String secondName;
    private int age;
    private String login;
    private String password;
    private byte[] salt;
    private String token;
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String secondName, String login, String password, int age) {
        this.name = name;
        this.secondName = secondName;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public User(long userId, User user) {
        this.id = userId;
        name = user.name;
        secondName = user.secondName;
        login = user.login;
        password = user.password;
        token = user.token;
        roles = user.roles;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return age == user.age
                && id.equals(user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(secondName, user.secondName)
                && login.equals(user.login)
                && password.equals(user.password)
                && Objects.equals(token, user.token)
                && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, age, login, password, token, roles);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", name='" + name + '\''
                + ", secondName='" + secondName + '\''
                + ", login='" + login + '\''
                + ", age=" + age + '}';
    }
}
