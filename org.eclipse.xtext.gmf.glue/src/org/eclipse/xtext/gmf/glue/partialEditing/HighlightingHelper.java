/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.gmf.glue.partialEditing;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.xtext.ui.editor.XtextPresentationReconciler;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.XtextSourceViewerConfiguration;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.syntaxcoloring.HighlightingPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class HighlightingHelper implements IPropertyChangeListener {

	@Inject
	private Provider<HighlightingReconciler> reconcilerProvider;
	
	@Inject
	private Provider<HighlightingPresenter> presenterProvider;
	
	@Inject
	private IPreferenceStoreAccess preferenceStoreAccessor;
	
	/** Highlighting presenter */
	private HighlightingPresenter fPresenter;
	/** Highlighting reconciler */
	private HighlightingReconciler fReconciler;

	/** The source viewer */
	private XtextSourceViewer fSourceViewer;
	/** The source viewer configuration */
	private XtextSourceViewerConfiguration fConfiguration;
	/** The presentation reconciler */
	private XtextPresentationReconciler fPresentationReconciler;

	/**
	 * @param configuration 
	 * @param sourceViewer 
	 *
	 */
	public void install(XtextSourceViewerConfiguration configuration, XtextSourceViewer sourceViewer) {
		fSourceViewer= sourceViewer;
		fConfiguration= configuration;
		fPresentationReconciler= (XtextPresentationReconciler) fConfiguration.getPresentationReconciler(sourceViewer);
		preferenceStoreAccessor.getPreferenceStore().addPropertyChangeListener(this);
		enable();
	}

	/**
	 * Enable advanced highlighting.
	 */
	private void enable() {
		fPresenter= getPresenterProvider().get();
		fPresenter.install(fSourceViewer, fPresentationReconciler);

		if (fSourceViewer.getDocument() != null) {
			fReconciler= reconcilerProvider.get();
			fReconciler.install(fSourceViewer, fPresenter);
		}
	}

	/**
	 *
	 */
	public void uninstall() {
		disable();
		preferenceStoreAccessor.getPreferenceStore().removePropertyChangeListener(this);
		fSourceViewer= null;
		fConfiguration= null;
		fPresentationReconciler= null;
	}

	/**
	 * Disable advanced highlighting.
	 */
	private void disable() {
		if (fReconciler != null) {
			fReconciler.uninstall();
			fReconciler= null;
		}

		if (fPresenter != null) {
			fPresenter.uninstall();
			fPresenter= null;
		}
	}

	/**
	 * Returns this hightlighter's reconciler.
	 *
	 * @return the highlighter reconciler or <code>null</code> if none
	 */
	public HighlightingReconciler getReconciler() {
		return fReconciler;
	}

	/**
	 * @param reconcilerProvider 
	 *
	 */
	public void setReconcilerProvider(Provider<HighlightingReconciler> reconcilerProvider) {
		this.reconcilerProvider = reconcilerProvider;
	}

	/**
	 * @return Provider<HightlightingReconciler>
	 *
	 */
	public Provider<HighlightingReconciler> getReconcilerProvider() {
		return reconcilerProvider;
	}

	/**
	 * @param presenterProvider 
	 *
	 */

	public void setPresenterProvider(Provider<HighlightingPresenter> presenterProvider) {
		this.presenterProvider = presenterProvider;
	}

	/**
	 * @return Provider<HightlightingPresented>
	 */
	public Provider<HighlightingPresenter> getPresenterProvider() {
		return presenterProvider;
	}

	/**
	 * @param preferenceStoreAccessor 
	 *
	 */
	public void setPreferenceStoreAccessor(IPreferenceStoreAccess preferenceStoreAccessor) {
		this.preferenceStoreAccessor = preferenceStoreAccessor;
	}

	/**
	 * @return IPreferenceStoreAccessor
	 *
	 */
	public IPreferenceStoreAccess getPreferenceStoreAccessor() {
		return preferenceStoreAccessor;
	}

	public void propertyChange(PropertyChangeEvent event) {
		if (fReconciler != null && event.getProperty().contains(".syntaxColorer.tokenStyles"))
			fReconciler.refresh();
	}
}

