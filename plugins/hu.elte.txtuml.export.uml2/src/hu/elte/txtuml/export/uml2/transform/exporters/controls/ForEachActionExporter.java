package hu.elte.txtuml.export.uml2.transform.exporters.controls;

import hu.elte.txtuml.export.uml2.transform.exporters.BlockExporter;

import org.eclipse.jdt.core.dom.Statement;

public class ForEachActionExporter extends AbstractLoopExporter {

	public ForEachActionExporter(BlockExporter blockExporter) {
		super(blockExporter);
	}

	public void exportForEachStatement(Statement statement) {
		// TODO export forEach
		/*
		EnhancedForStatement forState = (EnhancedForStatement) statement;

		SingleVariableDeclaration parameter = forState.getParameter();
		Expression expression = forState.getExpression();
		Statement forEachBody = forState.getBody();

		Type exprType = this.getExpressionType(expression);
		String expr = this.getExpressionString(expression);

		ExpansionNode currentNode = (ExpansionNode) this.activity
				.createOwnedNode("LOOP_NODE_FOREACH_" + this.hashCode(),
						UMLPackage.Literals.EXPANSION_NODE);

		ExpansionRegion currentRegion = (ExpansionRegion) this.activity
				.createOwnedNode("LOOP_REGION_FOREACH_" + this.hashCode(),
						UMLPackage.Literals.EXPANSION_REGION);

		currentNode.setRegionAsInput(currentRegion);
		currentNode.setRegionAsOutput(currentRegion);

		this.currentNode = currentRegion;

		Variable loopVar = this.currentNode.createVariable("LOOP_VAR_"
				+ this.currentNode.getName(), exprType);
		AddVariableValueAction writeVar = (AddVariableValueAction) this.currentNode
				.createNode("INIT_LOOP_VAR_" + this.currentNode.getName(),
						UMLPackage.Literals.ADD_VARIABLE_VALUE_ACTION);

		writeVar.setVariable(loopVar);

		ValuePin valuePin = (ValuePin) writeVar.createValue(parameter.getName()
				.toString() + "_" + this.hashCode(), exprType,
				UMLPackage.Literals.VALUE_PIN);
		this.createAndAddOpaqueExpressionToValuePin(valuePin, expr, exprType);

		this.createEdgeBetweenActivityNodes(this.getLastNode(), writeVar);
		this.setLastNode(writeVar);

		++AbstractActionCodeExporter.blockBodiesBeingExported;

		this.exportBodyFromStatement(forEachBody);

		--AbstractActionCodeExporter.blockBodiesBeingExported;

		this.methodBodyExporter.getBodyNode().getExecutableNodes()
				.add(this.currentNode);

		// this.setLastNode(this.currentNode);
		 */
	}
/*
	protected MethodBodyExporter exportBodyFromStatement(Statement statement) {
		MethodBodyExporter imp = super.exportBodyFromStatement(statement);

		SequenceNode seqNode = imp.getBodyNode();

		this.currentNode.getNodes().add(seqNode);

		Action lastAction = (Action) seqNode.getExecutableNodes().get(
				seqNode.getExecutableNodes().size() - 1);

		this.currentNode.getOutputs().addAll(lastAction.getOutputs());
		this.loopBody = seqNode;

		return imp;
	}
*/
}
