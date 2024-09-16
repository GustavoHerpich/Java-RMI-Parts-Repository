package br.com.parts.factory;

import br.com.parts.model.IPart;
import br.com.parts.model.Impl.PartImpl;
import br.com.parts.model.Impl.SubPart;

public class PartFactory {
    public static IPart createPart(int code, String name, String description, boolean isPrimitive) {
        return new PartImpl(code, name, description, isPrimitive);
    }
    public static SubPart createSubPart(IPart part, int quantity) {
        return new SubPart(part, quantity);
    }
}
