/*****************************************************************************
 * Copyright (c) 2011 Atos.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Atos - Initial API and implementation
 *   Bug 366159 - [ActivityDiagram] Activity Diagram should be able to handle correctly Interruptible Edge
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;

public class NoDirectEditFromDiagramEditPolicy extends AbstractEditPolicy {

	/**
	 * Returns true when the request is a graphical delete
	 * 
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		return RequestConstants.REQ_DIRECT_EDIT.equals(req.getType());
	}

	/**
	 * Returns an unexecutable command for graphical delete.
	 * 
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if(RequestConstants.REQ_DIRECT_EDIT.equals(request.getType())) {
			return UnexecutableCommand.INSTANCE;
		}
		return super.getCommand(request);
	}
}
