/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.parts;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.dragtracker.NoScrollDragEditPartsTrackerEx;
import org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart;

/**
 * this class was used to remove scrollbar
 * Now it is not used (but can be when this functionality will be operational)
 *
 */
public abstract class ConstrainedNamedElementEditPart extends NamedElementEditPart {

	public ConstrainedNamedElementEditPart(View view) {
		super(view);
	}
	/**
	 * the drag tracker has been specialized in order to constraint mvt inside its container without 
	 * scroll bar
	 * {@inheritDoc}
	 */
	@Override
	public DragTracker getDragTracker(Request request) {
		return new NoScrollDragEditPartsTrackerEx(this);
	}
}
