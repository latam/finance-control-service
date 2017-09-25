package global;

public enum OperationTypes {
	Income("income"),
	Expense("expense");
    
    private final String value;
    OperationTypes(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
