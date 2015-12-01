package hu.elte.txtuml.export.papyrus.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Initializes the preferences
 *
 * @author Andr�s Dobreff
 */
public class PreferencesInitializer extends AbstractPreferenceInitializer {

  /**
   * Initializes the default preferences
   */
  @Override
  public void initializeDefaultPreferences() {
	  PreferencesManager.setDefaults();
  }
}