package hu.elte.txtuml.export.uml2.transform.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Instances of this class are responsible from obtaining a txtUML model from a
 * Java compilation unit.
 * 
 * @author �d�m Ancsin
 *
 */
public class ModelObtainer extends ASTVisitor {

	private CompilationUnit compilationUnit;
	private TypeDeclaration model;

	public ModelObtainer(CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	public TypeDeclaration getModel() {
		compilationUnit.accept(this);
		return model;
	}

	@Override
	public boolean visit(TypeDeclaration typeDeclaration) {
		Type superClassType = typeDeclaration.getSuperclassType();
		if (superClassType != null && superClassType.toString().equals("Model")) {
			model = typeDeclaration;
			return false;
		}

		return true;
	}
}
