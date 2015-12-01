package hu.elte.txtuml.layout.export.impl;

import hu.elte.txtuml.layout.export.interfaces.StatementList;
import hu.elte.txtuml.layout.visualizer.statements.Statement;
import hu.elte.txtuml.layout.visualizer.statements.StatementType;

import java.util.ArrayList;

/**
 * Default implementation for {@link StatementList}.
 * 
 * @author Gabor Ferenc Kovacs
 *
 */
@SuppressWarnings("serial")
public class StatementListImpl extends ArrayList<Statement> implements StatementList {

	@Override
	public void addNew(StatementType type, String... params) {
		add(new Statement(type, params));
	}

}
