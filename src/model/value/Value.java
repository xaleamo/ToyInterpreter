package model.value;

import model.type.Type;

public interface Value {
    Type getType();
    @Override
    String toString();
    @Override
    boolean equals(Object o);
}
