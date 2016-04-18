package hu.elte.txtuml.export.uml2.transform.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.uml2.uml.Class;

import hu.elte.txtuml.export.uml2.transform.exporters.PortExporter;
import hu.elte.txtuml.utils.jdt.ElementTypeTeller;

public class PortVisitor extends ASTVisitor {

	private final PortExporter portExporter;
	private final Class ownerClassifier;

	public PortVisitor(PortExporter portExporter, Class classifier) {
		this.portExporter = portExporter;
		this.ownerClassifier = classifier;
	}

	@Override
	public boolean visit(TypeDeclaration typeDeclaration) {
		if (ElementTypeTeller.isPort(typeDeclaration)) {
			portExporter.exportPort(typeDeclaration, ownerClassifier);
		}
		return true;
	}
}
