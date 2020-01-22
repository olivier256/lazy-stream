package stream;

public class Operator {
	private final OperatorType operatorType;

	private final Object operator;

	protected Operator(final OperatorType operatorType, final Object operator) {
		this.operatorType = operatorType;
		this.operator = operator;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public Object getOperator() {
		return operator;
	}

}
