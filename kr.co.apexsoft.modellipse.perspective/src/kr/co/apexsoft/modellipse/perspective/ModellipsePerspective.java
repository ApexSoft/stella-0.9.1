/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package kr.co.apexsoft.modellipse.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ModellipsePerspective implements IPerspectiveFactory {

	/** constant for the definition of papyrus model explorer **/
//	protected static final String ID_MODELEXPLORER = "org.eclipse.papyrus.views.modelexplorer.modelexplorer";
	
	/** constant for the definition of papyrus model explorer **/
	protected static final String ID_MODELLIPSE_EXPLORER = "kr.co.apexsoft.modellipse.explorer.view";

	/**
	 * 
	 * this method create the layout attached to this perspective
	 * 
	 * @param layout
	 */
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	/**
	 * Add actions into the workbench UI.
	 * 
	 * @param layout
	 *        the page layout
	 * 
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void defineActions(IPageLayout layout) {
		// Add "new wizards".
//		layout.addNewWizardShortcut(NewPapyrusProjectWizard.WIZARD_ID);
//		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
//		layout.addNewWizardShortcut(CreateModelWizard.WIZARD_ID);
		// Add "show views".
//		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
//		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
//		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
//		layout.addShowViewShortcut(ID_MODELEXPLORER);
//
//		layout.addActionSet("org.eclipse.debug.ui.launchActionSet");

		// add perspectives
//		layout.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective");
//		layout.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective");
	}

	/**
	 * Defines the layout of the perspective (where and which views are available).
	 * 
	 * @param layout
	 *        the page layout
	 * 
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void defineLayout(IPageLayout layout) {
		// Editors are placed for free.
		String editorArea = layout.getEditorArea();

		// Place the the Resource Navigator to the top left of editor area.
//		layout.addView(IPageLayout.ID_PROJECT_EXPLORER, IPageLayout.LEFT, 0.2f, editorArea);
		// Place the the Modellipse Explorer to the top left of editor area.
		layout.addView(ID_MODELLIPSE_EXPLORER, IPageLayout.LEFT, 0.2f, editorArea);
		
		// place outline under the modellipse explorer
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.BOTTOM, 0.7f, ID_MODELLIPSE_EXPLORER);

		// Place the ModelExplorer under the Navigator
//		layout.addView(ID_MODELEXPLORER, IPageLayout.BOTTOM, 0.33f, IPageLayout.ID_PROJECT_EXPLORER);

		// place properties under the editor
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, (float)0.70, editorArea);
		// bottom.addView("org.eclipse.pde.runtime.LogView");

		// place outline under the model explorer
//		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.8f, IPageLayout.ID_PROP_SHEET);
	}
}
