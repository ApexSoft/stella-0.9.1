package org.eclipse.papyrus.uml.diagram.sequence.apex.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.sequence.apex.part.tweaks.TweakStructuredContentProvider;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;

public class SequenceTweakContentProvider extends TweakStructuredContentProvider {

	private EditPartViewer fEditPartViewer;
	
	public SequenceTweakContentProvider(EditPartViewer editPartViewer) {
		super();
		fEditPartViewer = editPartViewer;
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EditPart) {
			return getLifelineEditParts(fEditPartViewer).toArray();
		}
		if (inputElement instanceof Diagram) {
			Diagram diagram = (Diagram)inputElement;
//			return getChildren(diagram).toArray();
			return getLifelines(diagram).toArray();
		}
		return super.getElements(inputElement);
	}
	
	private Collection<?> getLifelineEditParts(EditPartViewer editPartViewer) {
		Set<LifelineEditPart> editParts = new HashSet<LifelineEditPart>();
		Map registry = editPartViewer.getEditPartRegistry();
		for (Iterator iterator = registry.keySet().iterator(); iterator.hasNext(); ) {
			Object key = iterator.next();
			Object value = registry.get(key);
			if (value instanceof LifelineEditPart) {
				editParts.add((LifelineEditPart) value);
			}
		}
		return editParts;
	}

	private List<Lifeline> getLifelines(View view) {
		if (view.getElement() instanceof Interaction) {
			Interaction interaction = (Interaction)view.getElement();
			List<Lifeline> lifelines = new ArrayList<Lifeline>(interaction.getLifelines());
			return lifelines;
		}
		return Collections.emptyList();
	}
	
	private List<View> getChildren(View view) {
		List<View> views = new ArrayList<View>();
		
		for (Object child : view.getChildren()) {
			if (child instanceof View) {
				View childView = (View)child;
				if (childView.getElement() instanceof Lifeline) {
					views.add(childView);
				}
				
				for (View c : getChildren(childView)) {
					if (c.getElement().equals(childView.getElement()) == false) {
						views.add(c);
					}
				}
			}
		}
		return views;
	}
}