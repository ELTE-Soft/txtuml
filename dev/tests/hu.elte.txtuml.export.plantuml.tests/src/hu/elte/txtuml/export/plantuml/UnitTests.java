package hu.elte.txtuml.export.plantuml;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import hu.elte.txtuml.export.plantuml.tests.BasicSeqDiagElementTests;
import hu.elte.txtuml.export.plantuml.tests.FileManagementTests;
import hu.elte.txtuml.export.plantuml.tests.SequenceDiagramFragmentsTests;

@RunWith(Suite.class)
@SuiteClasses({ FileManagementTests.class, BasicSeqDiagElementTests.class, SequenceDiagramFragmentsTests.class })
public class UnitTests {
}