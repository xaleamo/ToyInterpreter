package model.value;

import model.type.Type;

public interface Value extends Cloneable {
    Type getType();
    //String toString();
    //boolean equals(Object o);

    Value clone();
}
