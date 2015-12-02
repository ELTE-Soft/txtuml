package hu.elte.txtuml.layout.export.interfaces;

import hu.elte.txtuml.layout.export.elementinfo.NodeInfo;
import hu.elte.txtuml.layout.export.impl.NodeListImpl;
import hu.elte.txtuml.layout.visualizer.model.RectangleObject;

import java.util.List;
import java.util.Set;

public interface NodeList extends List<NodeInfo> {

    static NodeList create() {
        return new NodeListImpl();
    }
    
    Set<RectangleObject> convert();
       
}
