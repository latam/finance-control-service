package global;

public enum AccountTypes {
	Income("income"),
	Expense("expense");
    
    private final String value;
    AccountTypes(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
