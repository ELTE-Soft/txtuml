package hu.elte.txtuml.validation.model;

import org.eclipse.jdt.core.dom.ASTNode;

import hu.elte.txtuml.validation.common.SourceInfo;
import hu.elte.txtuml.validation.common.ValidationProblem;

/**
 * Base class for all JtxtUML model problems.
 */
public abstract class ModelValidationError extends ValidationProblem {

	public ModelValidationError(SourceInfo sourceInfo, ASTNode node) {
		super(sourceInfo, node);
	}

	@Override
	public abstract int getID();

	public abstract ModelErrors getType();

	@Override
	public boolean isError() {
		return true;
	}
	
	@Override
	public String getMarkerType() {
		return JtxtUMLModelCompilationParticipant.JTXTUML_MODEL_MARKER_TYPE;
	}
	
	@Override
	public String toString() {
		return getType() + " (" + getMessage() + ")";
	}

}