package hu.elte.txtuml.validation;

import java.io.IOException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.compiler.BuildContext;
import org.eclipse.jdt.core.compiler.ReconcileContext;
import org.eclipse.jdt.core.dom.CompilationUnit;

import hu.elte.txtuml.export.uml2.utils.SharedUtils;

/**
 * Compilation participant for JtxtUML validation.
 * 
 */
public class JtxtUMLCompilationParticipant extends org.eclipse.jdt.core.compiler.CompilationParticipant {
	private static final String TXTUML_NATURE_ID = "hu.elte.txtuml.project.txtumlprojectNature";
	public static final String JTXTUML_MARKER_TYPE = "hu.elte.txtuml.validation.jtxtumlmarker";

	private JtxtUMLValidator validator;

	@Override
	public boolean isActive(IJavaProject project) {
		try {
			boolean txtUMLProject = (project.getProject().getNature(TXTUML_NATURE_ID) != null);
			return txtUMLProject;
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	public int aboutToBuild(IJavaProject project) {
		validator = new JtxtUMLValidator(project);
		return READY_FOR_BUILD;
	}

	@Override
	public void reconcile(ReconcileContext context) {
		try {
			IMarker[] markers = context.getWorkingCopy().getResource().findMarkers(JTXTUML_MARKER_TYPE, true,
					IResource.DEPTH_ZERO);
			CompilationUnit unit = context.getAST8();
			ProblemCollectorForReconcile collector = new ProblemCollectorForReconcile(context, markers);
			validator.validate(unit, collector);
		} catch (CoreException e) {
		}
	}

	@Override
	public void buildStarting(BuildContext[] files, boolean isBatch) {
		for (BuildContext file : files) {
			java.io.File systemFile = null;
			IPath location = file.getFile().getLocation();
			if (location != null) {
				systemFile = location.toFile();
			}
			if (systemFile == null) {
				return;
			}

			IProject project = file.getFile().getProject();
			IJavaProject javaProject = JavaCore.create(project);
			CompilationUnit unit = null;
			try {
				unit = SharedUtils.parseJavaSource(systemFile, javaProject);
			} catch (IOException | JavaModelException e) {
				// Validation is not possible, return.
				return;
			}
			if (unit == null) {
				// Validation is not possible, return.
				return;
			}

			ProblemCollectorForBuild collector = new ProblemCollectorForBuild(file.getFile().getName(), unit);
			validator.validate(unit, collector);
			file.recordNewProblems(collector.getProblems());
		}
	}

	@Override
	public void buildFinished(IJavaProject project) {
		validator = null;
	}

}
