package br.com.parts.model.Impl;

import br.com.parts.model.IPart;
import java.io.Serializable;

public class SubPart implements Serializable {
    private final IPart part;
    private final int quantity;

    public SubPart(IPart part, int quantity) {
        this.part = part;
        this.quantity = quantity;
    }

    public IPart getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "SubPart{" +
                "part=" + part +
                ", quantity=" + quantity +
                '}';
    }
}
