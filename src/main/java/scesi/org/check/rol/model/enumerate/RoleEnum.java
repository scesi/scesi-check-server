package scesi.org.check.rol.model.enumerate;

public enum RoleEnum {
    ADMIN(1L, "admin"),
    MEMBER(2L, "member");

    private final Long id;
    private final String name;

    RoleEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RoleEnum fromName(String name) {
        for (RoleEnum role : values()) {
            if (role.name.equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + name);
    }
}