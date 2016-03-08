package hu.elte.txtuml.xtxtuml.validation

import hu.elte.txtuml.xtxtuml.xtxtUML.TUAssociation
import hu.elte.txtuml.xtxtuml.xtxtUML.TUAssociationEnd
import hu.elte.txtuml.xtxtuml.xtxtUML.TUComposition
import hu.elte.txtuml.xtxtuml.xtxtUML.XtxtUMLPackage
import org.eclipse.xtext.validation.Check

class XtxtUMLAssociationValidator extends AbstractXtxtUMLValidator {

	public static val ASSOCIATION_END_COUNT_MISMATCH = "hu.elte.txtuml.xtxtuml.issues.AssociationEndCountMismatch";
	public static val ASSOCIATION_END_NAME_IS_NOT_UNIQUE = "hu.elte.txtuml.xtxtuml.issues.AssociationEndNameIsNotUnique";
	public static val ASSOCIATION_CONTAINS_CONTAINER_END = "hu.elte.txtuml.xtxtuml.issues.AssociationContainsContainerEnd";
	public static val CONTAINER_END_COUNT_MISMATCH = "hu.elte.txtuml.xtxtuml.issues.CompositionMissesContainerEnd";

	@Check
	def checkAssociationsHaveExactlyTwoEnds(TUAssociation association) {
		val numberOfEnds = association.ends.length
		if (2 != numberOfEnds) {
			error("Association " + association.name + " must have exactly two ends", association,
				XtxtUMLPackage.eINSTANCE.TUModelElement_Name, ASSOCIATION_END_COUNT_MISMATCH, numberOfEnds.toString)
		}
	}

	@Check
	def checkAssociationEndNamesAreUnique(TUAssociationEnd associationEnd) {
		val association = associationEnd.eContainer as TUAssociation
		if (1 < association.ends.filter[name == associationEnd.name].length) {
			error("Association end " + associationEnd.name + " in association " + association.name +
				" must have a unique name", associationEnd, XtxtUMLPackage.eINSTANCE.TUClassProperty_Name,
				ASSOCIATION_END_NAME_IS_NOT_UNIQUE, associationEnd.name)
		}
	}

	@Check
	def checkContainerEndIsAllowedAndNeededOnlyInComposition(TUAssociation association) {
		val containerEnds = association.ends.filter[isContainer]
		val numberOfContainerEnds = containerEnds.length
		if (association instanceof TUComposition) {
			if (1 != numberOfContainerEnds) {
				error("Composition " + association.name + " must have exactly one container end", association,
					XtxtUMLPackage.eINSTANCE.TUModelElement_Name,
					XtxtUMLAssociationValidator.CONTAINER_END_COUNT_MISMATCH, association.name)
			}
		} else {
			containerEnds.forEach [
				error("Container end " + it.name + " must not be present in an association", it,
					XtxtUMLPackage.eINSTANCE.TUAssociationEnd_Container, ASSOCIATION_CONTAINS_CONTAINER_END, it.name)
			]
		}
	}

}
