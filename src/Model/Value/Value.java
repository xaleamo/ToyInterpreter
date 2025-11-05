package Model.Value;

import Model.Type.Type;

public interface Value {
    Type getType();
    @Override
    String toString();
    @Override
    boolean equals(Object o);
}
