package eapli.base.productionordermanagement.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
public class State implements ValueObject,  Comparable<State> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String state;

    public State(String state) {
        this.state=state;
    }

    private enum StateValues {
        SUSPENDED("Suspended"),
        PENDING("Pending"),
        EXECUTING("Executing"),
        EXECUTION_HALTED("Execution halted temporarily"),
        FINISHED("Finished");

        private String value;

        StateValues(String avalue) {
            this.value=avalue;
        }

        public String getValue() {
            return value;
        }
    }

    public State(boolean suspended) {
        if (suspended) this.state=StateValues.SUSPENDED.getValue();
        else this.state=StateValues.PENDING.getValue();
    }

    protected State() {}

    public String getState() {
        return state;
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isPending() {
        return state.equals(StateValues.PENDING.getValue());
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isSuspended() {
        return state.equals(StateValues.SUSPENDED.getValue());
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isExecuting() {
        return state.equals(StateValues.EXECUTING.getValue());
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isHalted() {
        return state.equals(StateValues.EXECUTION_HALTED.getValue());
    }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public boolean isFinished() {
        return state.equals(StateValues.FINISHED.getValue());
    }

    public void setPending() {
        state=StateValues.PENDING.getValue();
    }

    public void setSuspended() {
        state=StateValues.SUSPENDED.getValue();
    }

    public void setExecuting() {
        state=StateValues.EXECUTING.getValue();
    }

    public void setHalted() {
        state=StateValues.EXECUTION_HALTED.getValue();
    }

    public void setFinished() {
        state=StateValues.FINISHED.getValue();
    }

    @Override
    public int compareTo(State o) {
        return state.compareTo(o.state);
    }

    @Override
    public String toString() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.state);
        return hash;
    }
}
