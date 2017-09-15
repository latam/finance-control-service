package global;

public enum ProfileConstants {
    Development("dev");
    
    private final String value;
    ProfileConstants(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
