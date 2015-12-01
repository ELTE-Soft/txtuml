package hu.elte.txtuml.layout.export.elementinfo.impl;

import hu.elte.txtuml.layout.export.elementinfo.LinkGroupInfo;
import hu.elte.txtuml.layout.export.elementinfo.LinkInfo;
import hu.elte.txtuml.layout.export.interfaces.LinkMap;

/**
 * 
 * @author D�vid J�nos N�meth
 *
 */
public class LinkGroupInfoImpl extends ElementInfoImpl implements LinkGroupInfo {

	private final LinkMap links;
	private boolean beingExported;

	public LinkGroupInfoImpl(Class<?> elementClass, String asString) {
		super(elementClass, asString);
		links = LinkMap.create();
		beingExported = false;
	}

	@Override
	public boolean beingExported() {
		return beingExported;
	}

	@Override
	public void setBeingExported(boolean val) {
		beingExported = val;
	}

	@Override
	public LinkMap getAllLinks() {
		return links;
	}

	@Override
	public void addLink(LinkInfo link) {
		links.put(link.getElementClass(), link);
	}

}
