package hu.elte.txtuml.validation.model.visitors;

import static hu.elte.txtuml.validation.model.ModelErrors.INVALID_MODIFIER;
import static hu.elte.txtuml.validation.model.ModelErrors.INVALID_TYPE_PARAMETER;

import java.util.function.Predicate;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

import hu.elte.txtuml.utils.jdt.ElementTypeTeller;
import hu.elte.txtuml.validation.common.ProblemCollector;

public class Utils {

	public static void checkTypeParameter(ProblemCollector collector, TypeDeclaration elem) {
		if (elem.typeParameters().size() > 0) {
			collector.report(INVALID_TYPE_PARAMETER.create(collector.getSourceInfo(), (TypeParameter) (elem.typeParameters().get(0))));
		}
	}

	public static void checkModifiers(ProblemCollector collector, BodyDeclaration elem) {
		checkModifiers(collector, elem, m -> false);
	}

	public static void checkModifiers(ProblemCollector collector, BodyDeclaration elem,
			Predicate<Modifier> allowSpecific) {
		for (Object obj : elem.modifiers()) {
			if (!(obj instanceof Modifier)) {
				continue;
			}
			Modifier modifier = (Modifier) obj;
			boolean valid;
			if (allowSpecific.test(modifier)) {
				valid = true;
			} else {
				valid = modifier.isPrivate() || modifier.isPublic() || modifier.isProtected() || modifier.isFinal();
			}
			if (!valid) {
				collector.report(INVALID_MODIFIER.create(collector.getSourceInfo(), modifier));
			}
		}
	}

	public static boolean isAllowedAttributeType(Type type, boolean isVoidAllowed) {
		if (isBasicType(type, isVoidAllowed)) {
			return true;
		}
		ITypeBinding binding = type.resolveBinding();
		return !ElementTypeTeller.isExternal(binding)
				&& (ElementTypeTeller.isDataType(binding) || ElementTypeTeller.isModelEnum(type.resolveBinding()));
	}

	public static boolean isAllowedParameterType(Type type, boolean isVoidAllowed) {
		if (isAllowedAttributeType(type, isVoidAllowed)) {
			return true;
		}

		ITypeBinding binding = type.resolveBinding();
		if (!ElementTypeTeller.isExternal(binding)
				&& (ElementTypeTeller.isModelClass(binding) || ElementTypeTeller.isSignal(type.resolveBinding()))) {
			return true;
		}

		return false;
	}

	public static boolean isVoid(Type type) {
		if (type instanceof PrimitiveType) {
			return ((PrimitiveType) type).getPrimitiveTypeCode().equals(PrimitiveType.VOID);
		} else {
			return false;
		}
	}

	public static boolean isBoolean(Type type) {
		if (type instanceof PrimitiveType) {
			return ((PrimitiveType) type).getPrimitiveTypeCode().equals(PrimitiveType.BOOLEAN);
		} else {
			return false;
		}
	}

	public static boolean isBasicType(Type type, boolean isVoidAllowed) {
		if (type.isPrimitiveType()) {
			PrimitiveType.Code code = ((PrimitiveType) type).getPrimitiveTypeCode();
			return (code == PrimitiveType.BOOLEAN || code == PrimitiveType.DOUBLE || code == PrimitiveType.INT
					|| (code == PrimitiveType.VOID && isVoidAllowed));
		}
		if (type.resolveBinding().getQualifiedName().equals(String.class.getCanonicalName())) {
			return true;
		}
		return false;
	}

}