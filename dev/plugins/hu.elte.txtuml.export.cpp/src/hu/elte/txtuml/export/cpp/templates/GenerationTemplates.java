package hu.elte.txtuml.export.cpp.templates;

import java.io.File;
import java.util.List;

import hu.elte.txtuml.export.cpp.templates.GenerationNames.FileNames;
import hu.elte.txtuml.export.cpp.templates.GenerationNames.HierarchicalStateMachineNames;
import hu.elte.txtuml.export.cpp.templates.GenerationNames.PointerAndMemoryNames;
import hu.elte.txtuml.export.cpp.templates.GenerationNames.TypeDeclarationKeywords;

public class GenerationTemplates {

	public static final String StandardIOinclude = GenerationNames.StandardIOInclude;
	public static final String DeploymentHeader = GenerationNames.DeploymentHeaderName;

	public static String headerName(String className) {
		return className + "." + FileNames.HeaderExtension;
	}

	public static String sourceName(String className) {
		return className + "." + FileNames.SourceExtension;
	}

	public static String dataType(String datatTypeName, String attributes) {
		return TypeDeclarationKeywords.DataType + " " + datatTypeName + "\n" + "{\n" + attributes + "}";
	}
	

	public static String generatedAbstractClassName(String className) {
		return "Abstract" + className;
	}
	
	public static String generatedErrorMessage(String functionName) {
		return " Not implemented external method: " + functionName + "\n";
	}
	
	public static String generatedExternalInclude(String directory, String className, String functionName, String tail) {
		return " \"" + directory + File.separator + className + "_" + functionName + tail + "\"\n";
	}

	public static String paramName(String paramName) {

		return GenerationNames.formatIncomingParamName(paramName);
	}

	public static String forwardDeclaration(String className) {
		return GenerationNames.TypeDeclarationKeywords.ClassType + " " + PrivateFunctionalTemplates.mapUMLClassToCppClass(className) + ";\n";
	}

	public static String putNamespace(String source, String namespace) {
		return "namespace " + namespace + "\n{\n" + source + "\n}\n";
	}

	public static String formatSubSmFunctions(String source) {
		return source.replaceAll(PointerAndMemoryNames.Self, HierarchicalStateMachineNames.ParentSmMemberName);
	}


	public static String createObject(String typeName, String objName, boolean sharedObject) {
		return createObject(typeName, objName, null, null, sharedObject);
	}

	public static String createObject(String typeName, String objName, List<String> params, boolean sharedObject) {
		return createObject(typeName, objName, null, params, sharedObject);
	}

	public static String createObject(String typeName, String objName, List<String> templateParams, List<String> params,
			boolean sharedObject) {
		String templateParameters = "";
		if (templateParams != null) {
			templateParameters = "<";
			for (int i = 0; i < templateParams.size() - 1; i++) {
				templateParameters = templateParameters + templateParams.get(i) + ",";
			}
			templateParameters = templateParameters + templateParams.get(templateParams.size() - 1) + ">";
		}
		if (!sharedObject) {
			return GenerationNames.pointerType(typeName + templateParameters) + " " + objName + " = "
					+ allocateObject(typeName + templateParameters, templateParams, params, false) + ";\n";
		} else {
			return GenerationNames.sharedPtrType(typeName + templateParameters) + " " + objName + " = "
					+ allocateObject(typeName + templateParameters, templateParams, params, true) + ";\n";
		}

	}

	public static String allocateObject(String typeName, List<String> templateParams, List<String> params,
			boolean sharedObject) {

		String parameters = "(";
		if (params != null && params.size() > 0) {

			for (int i = 0; i < params.size() - 1; i++) {
				parameters = parameters + params.get(i) + ",";
			}
			parameters = parameters + params.get(params.size() - 1);
		}
		parameters = parameters + ")";

		String templateParameters = "";
		if (templateParams != null) {
			templateParameters = "<";
			for (int i = 0; i < templateParams.size() - 1; i++) {
				templateParameters = templateParameters + templateParams.get(i) + ",";
			}
			templateParameters = templateParameters + templateParams.get(templateParams.size() - 1) + ">";
		}

		String allocatedObject = PointerAndMemoryNames.MemoryAllocator + " " + typeName + templateParameters
				+ parameters;
		if (!sharedObject) {
			return allocatedObject;
		} else {
			return GenerationNames.PointerAndMemoryNames.SmartPtr + "<" + typeName + ">" + "(" + allocatedObject + ")";
		}

	}

	public static String allocateObject(String typeName, List<String> params, boolean sharedObject) {
		return allocateObject(typeName, null, params, sharedObject);
	}

	public static String allocateObject(String typeName) {
		return allocateObject(typeName, null, null, false);
	}

	public static String staticCreate(String typeName, String returnType, String objName, String creatorMethod) {
		return returnType + " " + objName + " = " + staticMethodInvoke(typeName, creatorMethod) + ";\n";
	}

	public static String staticMethodInvoke(String className, String method) {
		return className + "::" + method + "()";
	}

	public static String debugOnlyCodeBlock(String code_) {
		return "#ifndef " + GenerationNames.NoDebugSymbol + "\n" + code_ + "#endif\n";
	}

	public static String usingTemplateType(String usedName, String typeName, List<String> templateParams) {
		String templateParameters = "<";
		templateParameters = "<";
		for (int i = 0; i < templateParams.size() - 1; i++) {
			templateParameters = templateParameters + templateParams.get(i) + ",";
		}
		templateParameters = templateParameters + templateParams.get(templateParams.size() - 1) + ">";

		return "using " + usedName + " = " + typeName + templateParameters + ";\n";

	}

}
