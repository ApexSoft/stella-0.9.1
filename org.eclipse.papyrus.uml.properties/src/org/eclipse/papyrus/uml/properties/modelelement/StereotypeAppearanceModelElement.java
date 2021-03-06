/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.properties.modelelement;

import static org.eclipse.papyrus.uml.appearance.helper.UMLVisualInformationPapyrusConstant.STEREOTYPE_BRACE_LOCATION;
import static org.eclipse.papyrus.uml.appearance.helper.UMLVisualInformationPapyrusConstant.STEREOTYPE_COMMENT_LOCATION;
import static org.eclipse.papyrus.uml.appearance.helper.UMLVisualInformationPapyrusConstant.STEREOTYPE_COMPARTMENT_LOCATION;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.DISPLAY_PLACE;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.HORIZONTAL;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.ICON;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.SHAPE;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.STEREOTYPE_DISPLAY;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.TEXT;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.TEXT_ALIGNMENT;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.TEXT_AND_ICON;
import static org.eclipse.papyrus.uml.properties.util.StereotypeAppearanceConstants.VERTICAL;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;
import org.eclipse.papyrus.uml.properties.Activator;
import org.eclipse.papyrus.uml.properties.databinding.StereotypeAppearanceObservableValue;
import org.eclipse.papyrus.views.properties.modelelement.AbstractModelElement;
import org.eclipse.papyrus.views.properties.modelelement.ModelElement;
import org.eclipse.uml2.uml.Element;

/**
 * A ModelElement for manipulating the stereotype appearance properties :
 * - stereotypeDisplay
 * - textAlignment
 * - displayPlace
 * 
 * @author Camille Letavernier
 * 
 */
public class StereotypeAppearanceModelElement extends AbstractModelElement implements ModelElement {

	/**
	 * The current UML Element
	 */
	protected Element umlSource;

	/**
	 * The editing domain on which the commands will be called
	 */
	protected EditingDomain domain;

	/**
	 * The GMF EModelElement
	 */
	protected EModelElement diagramElement;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param umlSource
	 *        The UML Element on which the stereotypes are applied
	 * @param domain
	 *        The Editing Domain on which the commands will be executed
	 * @param diagramElement
	 *        The GMF EModelElement
	 */
	public StereotypeAppearanceModelElement(Element umlSource, EditingDomain domain, EModelElement diagramElement) {
		this.umlSource = umlSource;
		this.domain = domain;
		this.diagramElement = diagramElement;
	}

	@Override
	public IObservable doGetObservable(String propertyPath) {
		if(propertyPath.equals(STEREOTYPE_DISPLAY) || propertyPath.equals(TEXT_ALIGNMENT) || propertyPath.equals(DISPLAY_PLACE)) {
			return new StereotypeAppearanceObservableValue(diagramElement, umlSource, propertyPath, domain);
		}

		Activator.log.warn("Unknown property : " + propertyPath); //$NON-NLS-1$
		return null;
	}

	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		if(propertyPath.equals(STEREOTYPE_DISPLAY)) {
			return new StaticContentProvider(new String[]{ TEXT, ICON, TEXT_AND_ICON, SHAPE });
		} else if(propertyPath.equals(TEXT_ALIGNMENT)) {
			return new StaticContentProvider(new String[]{ HORIZONTAL, VERTICAL });
		} else if(propertyPath.equals(DISPLAY_PLACE)) {
			return new StaticContentProvider(new String[]{ STEREOTYPE_COMPARTMENT_LOCATION, STEREOTYPE_COMMENT_LOCATION, STEREOTYPE_BRACE_LOCATION });
		}

		return EmptyContentProvider.instance;
	}

	@Override
	public boolean isMandatory(String propertyPath) {
		return true;
	}

}
