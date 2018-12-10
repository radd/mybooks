package io.github.radd.mybooks.utils.user;

public enum UserRole {
    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATOR"),
    ADMIN("ROLE_ADMIN");

    private String roleName = "";

    UserRole(String name){
        this.roleName = name;
    }

    public boolean is(String name) {
        return roleName.equals(name);
    }

    public String getName() {
        return roleName;
    }
}