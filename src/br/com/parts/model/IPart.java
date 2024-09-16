package br.com.parts.model;

import br.com.parts.model.Impl.SubPart;

import java.io.Serializable;
import java.util.List;

public interface IPart extends Serializable {
    int getCode();
    String getName();
    String getDescription();
    List<SubPart> getSubParts();
    boolean isPrimitive();
    void addSubPart(IPart subPart, int quantity);
}