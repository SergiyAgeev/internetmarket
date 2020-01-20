package mate.academy.internetshop.model;

import mate.academy.internetshop.lib.IdGenerator;

public class Role {
    private final Long id;
    private RoleName roleName;

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public Role() {
        this.id = IdGenerator.generateNewRoleId();
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public Role setRoleName(RoleName roleName) {
        this.roleName = roleName;
        return this;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
