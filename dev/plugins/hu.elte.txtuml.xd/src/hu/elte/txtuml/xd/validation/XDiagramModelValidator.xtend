package hu.elte.txtuml.xd.validation

import hu.elte.txtuml.api.model.ModelClass
import hu.elte.txtuml.xd.xDiagramDefinition.XDDiagram
import org.eclipse.core.runtime.Assert
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.common.types.JvmDeclaredType
import hu.elte.txtuml.xd.xDiagramDefinition.XDTypeExpression
import hu.elte.txtuml.xd.xDiagramDefinition.XDPackageDeclaration
import hu.elte.txtuml.xd.xDiagramDefinition.XDTypeExpressionList

class XDiagramModelValidator extends AbstractXDiagramDefinitionValidator {
	protected XDDiagram signature;
	protected XDPackageDeclaration packageDecl;

	@Check
	def checkPackageDeclaration(XDPackageDeclaration pack){
		packageDecl = pack;
		if (packageDecl != null){
			// TODO: check if it's (or it's in) a model package
		}
	}

	@Check
	def checkDiagramSignature(XDDiagram sig) {
		signature = sig;
		if (signature.diagramType == "class-diagram") {
		} else if (signature.diagramType == "state-machine-diagram") {
			// TODO: check the model package as well...
			sig.validateSuperTypes(signature.genArg, ModelClass);
		}
	}

	def private String getDiagramTypeName(){
		if (packageDecl == null) return signature.name;
		return packageDecl.name + "." + signature.name;
	}

	@Check
	def checkTypeExpression(XDTypeExpression te){
		// TODO: check the model package as well...
		
		if (signature.genArg == null) return;
		if (te.phantom != null) return;
		if (te.name.checkDeclaringType(signature.genArg.qualifiedName)) return;
		if (te.name.checkDeclaringType(getDiagramTypeName())) return;
		
		warning("only elements declared in model " + signature.genArg.simpleName + " or " + signature.name + " should be used", te, null);
	}

	def boolean checkSuperTypes(XDTypeExpressionList teList, Class<?>... superTypes){
		for (XDTypeExpression tex : teList.expressions){
			if (!tex.name.checkSuperTypes(superTypes)) return false;
		}
		return true;
	}
	
	def boolean checkSuperTypes(JvmGenericType type, Class<?>... classes) {
		Assert.isNotNull(classes);
		val sup = classes.findFirst[type.actualType.getSuperType(it) != null];
		return sup != null;
	}

	def boolean checkDeclaringType(JvmGenericType type, String declaringTypeName) {
		Assert.isNotNull(type);
		if (declaringTypeName == null) {
			return type.declaringType == null;
		}
		
		var targetType = type as JvmDeclaredType;
		while (targetType != null) {
			if(targetType.qualifiedName.equals(declaringTypeName)) return true;
			targetType = targetType.declaringType;
		}
		return false;
	}

	def validateSuperTypes(EObject source, JvmGenericType type, Class<?>... classes) {
		Assert.isNotNull(classes);
		if (type.checkSuperTypes(classes)) {
			return;
		}
		val errorMessage = new StringBuilder("Argument should extend one of the following types:");
		classes.forEach[errorMessage.append(" " + name)];
		error(errorMessage.toString(), source, null);
	}

}