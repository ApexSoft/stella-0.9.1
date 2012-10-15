/**
 *  Copyright (c) 2011 Atos.
 *  
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *  Atos - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.views.modelexplorer.core.ui.exception;

/**
 * Exception thrown by the model explorer plugin
 * 
 * @author "Arthur Daussy <a href="mailto:arthur.daussy@atos.net">arthur.daussy@atos.net</a>"
 * 
 */
public class ModelExplorerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1197723371777623860L;

	public ModelExplorerException() {
		super();
	}

	public ModelExplorerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelExplorerException(String message) {
		super(message);
	}

	public ModelExplorerException(Throwable cause) {
		super(cause);
	}

}
