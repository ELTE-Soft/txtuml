package hu.elte.txtuml.xtxtuml;

import hu.elte.txtuml.xtxtuml.compiler.XtxtUMLCompiler;
import hu.elte.txtuml.xtxtuml.naming.XtxtUMLQualifiedNameConverter;
import hu.elte.txtuml.xtxtuml.scoping.XtxtUMLImplicitlyImportedFeatures;
import hu.elte.txtuml.xtxtuml.scoping.XtxtUMLXImportSectionNamespaceScopeProvider;
import hu.elte.txtuml.xtxtuml.typesystem.XtxtUMLEarlyExitComputer;
import hu.elte.txtuml.xtxtuml.typesystem.XtxtUMLReentrantTypeResolver;
import hu.elte.txtuml.xtxtuml.typesystem.XtxtUMLTypeComputer;
import hu.elte.txtuml.xtxtuml.validation.XtxtUMLExpressionHelper;

import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultReentrantTypeResolver;
import org.eclipse.xtext.xbase.typesystem.util.ExtendedEarlyExitComputer;
import org.eclipse.xtext.xbase.util.XExpressionHelper;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class XtxtUMLRuntimeModule extends AbstractXtxtUMLRuntimeModule {

    public Class<? extends ImplicitlyImportedFeatures> bindImplicitlyImportedFeatures() {
        return XtxtUMLImplicitlyImportedFeatures.class;
    }
    
    @Override
    public void configureIScopeProviderDelegate(Binder binder) {
        binder.bind(IScopeProvider.class)
            .annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
            .to(XtxtUMLXImportSectionNamespaceScopeProvider.class);
    }
    
    public Class<? extends XbaseCompiler> bindXbaseCompiler() {
        return XtxtUMLCompiler.class;
    }

    public Class<? extends ITypeComputer> bindITypeComputer() {
        return XtxtUMLTypeComputer.class;
    }
    
    public Class<? extends ExtendedEarlyExitComputer> bindExtendedEarlyExitComputer() {
        return XtxtUMLEarlyExitComputer.class;
    }
    
    @Override
    public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
        return XtxtUMLQualifiedNameConverter.class;
    }
    
    @Override
    public Class<? extends DefaultReentrantTypeResolver> bindDefaultReentrantTypeResolver() {
        return XtxtUMLReentrantTypeResolver.class;
    }
    
    public Class<? extends XExpressionHelper> bindXExpressionHelper() {
        return XtxtUMLExpressionHelper.class;
    }
    
}
