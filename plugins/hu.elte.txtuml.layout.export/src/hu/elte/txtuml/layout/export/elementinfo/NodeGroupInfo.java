package hu.elte.txtuml.layout.export.elementinfo;

import hu.elte.txtuml.api.layout.AlignmentType;
import hu.elte.txtuml.layout.export.elementinfo.impl.NodeGroupInfoImpl;
import hu.elte.txtuml.layout.export.interfaces.NodeMap;

/**
 * Information holder about a node group in a diagram layout description.
 * 
 * @author D�vid J�nos N�meth
 *
 */
public interface NodeGroupInfo extends GroupInfo {

	static NodeGroupInfo create(Class<?> elementClass, String asString) {
		return new NodeGroupInfoImpl(elementClass, asString);
	}

	NodeMap getAllNodes();

	void addNode(NodeInfo node);

	void setAlignment(AlignmentType alignment);

	AlignmentType getAlignment();

}
