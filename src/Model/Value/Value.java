package Model.Value;

import Model.Type.Type;

public interface Value {
    public Type getType();
    @Override
    public String toString();
}
