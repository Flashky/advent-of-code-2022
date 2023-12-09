package com.adventofcode.flashk.day16;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class SimpleValve {

    public static final int OPEN_TIME = 1;

    private String name;
    private int flow;
    private boolean open = false;

    public SimpleValve(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimpleValve other = (SimpleValve) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Valve [name=" + name + "]";
    }

}
