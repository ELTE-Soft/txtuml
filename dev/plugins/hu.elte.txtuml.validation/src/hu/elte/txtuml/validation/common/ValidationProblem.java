package hu.elte.txtuml.validation.common;

import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Base class for all txtUML validation problems.
 * <p>
 * By default, {@link #isError}, {@link #isWarning} and {@link isInfo} all
 * return false.
 */
public abstract class ValidationProblem extends CategorizedProblem {

	private final SourceInfo sourceInfo;
	private int sourceStart;
	private int sourceEnd;
	private int lineNumber;

	public ValidationProblem(SourceInfo sourceInfo, ASTNode node) {
		this.sourceInfo = sourceInfo;
		this.sourceStart = node.getStartPosition();
		this.sourceEnd = node.getStartPosition() + node.getLength() - 1;
		this.lineNumber = sourceInfo.getSourceLineNumber(getSourceEnd());
	}

	@Override
	public String[] getArguments() {
		return new String[0];
	}

	@Override
	public char[] getOriginatingFileName() {
		return sourceInfo.getOriginatingFileName().toCharArray();
	}

	@Override
	public int getCategoryID() {
		return 0;
	}

	@Override
	public boolean isError() {
		return false;
	}

	@Override
	public boolean isWarning() {
		return false;
	}

	@Override
	public boolean isInfo() {
		return super.isInfo();
	}

	@Override
	public int getSourceStart() {
		return sourceStart;
	}

	@Override
	public int getSourceEnd() {
		return sourceEnd;
	}

	@Override
	public int getSourceLineNumber() {
		return lineNumber;
	}

	@Override
	public void setSourceStart(int sourceStart) {
		this.sourceStart = sourceStart;
	}

	@Override
	public void setSourceEnd(int sourceEnd) {
		this.sourceEnd = sourceEnd;
	}

	@Override
	public void setSourceLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}
