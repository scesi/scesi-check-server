package scesi.org.check.attendance.model.enumerate;

public enum TypeAttendanceEnum {
    PRESENT(1L),
    LATE_ARRIVAL(2L),
    ABSENCE(3L);
    private final Long id;

    TypeAttendanceEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
