package br.com.parts.model.Impl;

import br.com.parts.model.IPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PartImpl implements IPart {
    private final int code;
    private final String name;
    private final String description;
    private final List<SubPart> subParts = new ArrayList<>();
    private final boolean isPrimitive;

    public PartImpl(int code, String name, String description, boolean isPrimitive) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.isPrimitive = isPrimitive;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<SubPart> getSubParts() {
        return Collections.unmodifiableList(subParts);
    }

    @Override
    public boolean isPrimitive() { return isPrimitive; }

    @Override
    public void addSubPart(IPart subPart, int quantity) { subParts.add(new SubPart(subPart, quantity)); }
}