/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package kr.co.apexsoft.modellipse.explorer.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.apexsoft.modellipse.explorer.core.ApexModellipseProjectMap;
import kr.co.apexsoft.modellipse.explorer.core.ApexProjectWrapper;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.facet.infra.browser.uicore.CustomizableModelContentProvider;
import org.eclipse.emf.facet.infra.browser.uicore.internal.AppearanceConfiguration;
import org.eclipse.emf.facet.infra.browser.uicore.internal.model.ItemsFactory;
import org.eclipse.emf.facet.infra.browser.uicore.internal.model.ModelElementItem;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.infra.core.resource.uml.UmlModel;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.onefile.utils.OneFileUtils;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;

/**
 * this is a specific content provider used to not display UML stereotype applications
 * 
 * @deprecated Use {@link kr.co.apexsoft.modellipse.explorer.provider.ApexUMLContentProvider.providers.UMLContentProvider} instead
 */
@Deprecated
public class ApexUMLContentProvider extends CustomizableModelContentProvider implements IResourceChangeListener {

	private Viewer viewer;

	private final AppearanceConfiguration appearanceConfiguration;	

	public ApexUMLContentProvider() {
//		super();
		super(Activator.getDefault().getCustomizationManager());
		appearanceConfiguration = new AppearanceConfiguration(new ItemsFactory());
//      아래는 없어도 자동으로 리스너로 등록되는 모양
//		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}	

	/**
	 * apex updated
	 */
	@Override
	public Object[] getElements(final Object inputElement) {

		Object[] rootElements = getRootElements(inputElement);

		if (rootElements == null) {
			return null;
		}

		List<Object> result = new ArrayList<Object>();

		for (Object element : rootElements) {

			if (element instanceof EObject) {
				EObject eObject = (EObject) element;
				result.add(new ModelElementItem(eObject, null, this.appearanceConfiguration));
			} else {
				result.add(element);
			}
		}
		return result.toArray();
	}

	/**
	 * apex updated
	 * 
	 * Return the initial values from the input.
	 * Input should be of type {@link UmlModel}.
	 * 
	 * @see org.eclipse.gmt.modisco.infra.browser.uicore.CustomizableModelContentProvider#getRootElements(java.lang.Object)
	 * 
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getRootElements(Object inputElement) {		
		/* apex improved start */
		List<Object> result = new ArrayList<Object>();
		try {
			if ( inputElement instanceof IWorkspaceRoot ) {
				IWorkspaceRoot root = (IWorkspaceRoot)inputElement;
				IProject[] projects = root.getProjects();

				for ( IProject project : projects ) {					
					result.add(project);
				}
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}
		return result.toArray();
		/* apex improved end */	
	}	

	/**
	 * apex updated
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		ArrayList<Object> result = new ArrayList<Object>();

		if ( parentElement instanceof IProject ) {
			IProject project = (IProject)parentElement;
			IResource[] members;
			try {
				members = project.members();

				for ( IResource r : members ) {

					if ( r instanceof IFile ) {
						IFile file = (IFile)r;

						if(OneFileUtils.isDi(file)) {
							result.add(file);
						}
					} 
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if ( parentElement instanceof IFile ) {
			IFile diFile = (IFile)parentElement;

			if(OneFileUtils.isDi(diFile)) {
				String diFilePath = diFile.getLocationURI().getPath();
				String projectPath = diFile.getParent().getLocationURI().getPath();

				Map<String, ApexProjectWrapper> projectMap = ApexModellipseProjectMap.getProjectMap();
				ApexProjectWrapper aProjectWrapper = projectMap.get(projectPath);



				// 프로젝트 내 di를 최초로 펼친 경우 - (1)
				// 프로젝트 래퍼가 없고,
				// diPath에 대한 umlModel 도 없다.
				// ->에디터 열고 ServiceReg를 가져와 세팅(최초 뷰 구성 시)

				// 프로젝트 내 di를 한 번 펼친 후 다른 di를 펼친 경우 - (2)
				// 프로젝트 래퍼는 있고,
				// diPath에 대한 umlModel은 없다
				// ->에디터 열고 ServiceReg를 가져와 세팅(최초 뷰 구성 시)

				// 더블클릭에 의한 refresh() 시 - (3)
				// 더블클릭 리스너가 직접 Editor를 열고 ServiceReg를 세팅
				// 프로젝트 래퍼가 있고,
				// diPath에 대한 umlModel 도 있다.
				// 여기선 가져와서 트리 만들어주기만 하믄 됨		

				// 에디터 닫아서 ApexMEView에서 refresh() 호출된 경우 - (4)
				// 프로젝트 래퍼는 있고
				// diPath에 대한 umlModel은 없다 (2)와 겹침
				// 에디터가 닫은 모델은 IsDisposed 가 true 인 것으로 구분
				// 그냥 지나친다

				if ( aProjectWrapper == null ) { // (1)
					createEditorAndSetUpTree(aProjectWrapper, diFile, result);
				} else if (aProjectWrapper.getIsDisposed(diFilePath)) { // (4)

				} else if ( aProjectWrapper.getUmlModel(diFilePath) == null ) { // (2)
					createEditorAndSetUpTree(aProjectWrapper, diFile, result);						
				} else { // (3)
					UmlModel umlModel = aProjectWrapper.getUmlModel(diFilePath);

					if ( umlModel != null ) {
						makeModelElementItemList(umlModel, result);
					}
				}
			}			
		}
		else {
			Object[] arrayObject = super.getChildren(parentElement);

			if(arrayObject != null) {

				for(int i = 0; i < arrayObject.length; i++) {

					if ( arrayObject[i] instanceof UmlModel ) {
						UmlModel umlModel = (UmlModel) arrayObject[i];
						makeModelElementItemList(umlModel, result);
					} else {
						result.add(arrayObject[i]);
					}
				}
			}
		}
		return result.toArray();
	}	

	@Override
	public boolean hasChildren(Object element) {

		boolean hasChildren = false;

		if ( element instanceof IProject ) {
			IProject project = (IProject)element;

			try {
				hasChildren = project.members().length > 0;
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else if ( element instanceof IFile ) {
			IFile diFile = (IFile)element;

			if(OneFileUtils.isDi(diFile)) {
				hasChildren = true;
			}
		} else {
			hasChildren = super.getChildren(element).length > 0;
		}
		return hasChildren;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);

		this.viewer = viewer;
		IWorkspace oldWorkspace = null;
		IWorkspace newWorkspace = null;

		if (oldInput instanceof IWorkspace) {
			oldWorkspace = (IWorkspace) oldInput;
		} else if (oldInput instanceof IContainer) {
			oldWorkspace = ((IContainer) oldInput).getWorkspace();
		}

		if (newInput instanceof IWorkspace) {
			newWorkspace = (IWorkspace) newInput;
		} else if (newInput instanceof IContainer) {
			newWorkspace = ((IContainer) newInput).getWorkspace();
		}

		if (oldWorkspace != newWorkspace) {
			if (oldWorkspace != null) {
				oldWorkspace.removeResourceChangeListener(this);
			}
			if (newWorkspace != null) {
				newWorkspace.addResourceChangeListener(this,
						IResourceChangeEvent.POST_CHANGE);
			}
		}
	}

	private void makeModelElementItemList(UmlModel umlModel, List<Object> result) {
		EList<EObject> contents = umlModel.getResource().getContents();

		for ( EObject eObj : contents ) {

			if ( eObj instanceof ModelImpl ) {
				ModelElementItem modelItem = new ModelElementItem(eObj, null, this.appearanceConfiguration); 
				result.add(modelItem);	
			}
		}
	}

	private void createEditorAndSetUpTree(ApexProjectWrapper aProjectWrapper, IFile diFile, List<Object> result) {
		String diFilePath = diFile.getLocationURI().getPath();
		IEditorPart editor = ApexModellipseProjectMap.openEditor(diFile);

		if ( editor != null && editor instanceof PapyrusMultiDiagramEditor ) {
			ServicesRegistry servicesRegistry = ((PapyrusMultiDiagramEditor)editor).getServicesRegistry();
			aProjectWrapper = ApexModellipseProjectMap.setUpModelServices(diFile, servicesRegistry);

			UmlModel umlModel = aProjectWrapper.getUmlModel(diFilePath);
			makeModelElementItemList(umlModel, result);
		}	
	}
	
	/**
	 * 아래부터 org.eclipse.ui.model.WorkbenchContentProvider에서 가져옴
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		processDelta(event.getDelta());
	}	
	
	/**
	 * Process the resource delta.
	 * 
	 * @param delta
	 */
	protected void processDelta(IResourceDelta delta) {		

		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}
		
		
		final Collection runnables = new ArrayList();
		processDelta(delta, runnables);

		if (runnables.isEmpty()) {
			return;
		}

		//Are we in the UIThread? If so spin it until we are done
		if (ctrl.getDisplay().getThread() == Thread.currentThread()) {
			runUpdates(runnables);
		} else {
			ctrl.getDisplay().asyncExec(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {
					//Abort if this happens after disposes
					Control ctrl = viewer.getControl();
					if (ctrl == null || ctrl.isDisposed()) {
						return;
					}
					
					runUpdates(runnables);
				}
			});
		}

	}

	/**
	 * Run all of the runnables that are the widget updates
	 * @param runnables
	 */
	private void runUpdates(Collection runnables) {
		Iterator runnableIterator = runnables.iterator();
		while(runnableIterator.hasNext()){
			((Runnable)runnableIterator.next()).run();
		}
		
	}

	/**
	 * Process a resource delta. Add any runnables
	 */
	private void processDelta(IResourceDelta delta, Collection runnables) {
		//he widget may have been destroyed
		// by the time this is run. Check for this and do nothing if so.
		Control ctrl = viewer.getControl();
		if (ctrl == null || ctrl.isDisposed()) {
			return;
		}

		// Get the affected resource
		final IResource resource = delta.getResource();
	
		// If any children have changed type, just do a full refresh of this
		// parent,
		// since a simple update on such children won't work,
		// and trying to map the change to a remove and add is too dicey.
		// The case is: folder A renamed to existing file B, answering yes to
		// overwrite B.
		IResourceDelta[] affectedChildren = delta
				.getAffectedChildren(IResourceDelta.CHANGED);
		for (int i = 0; i < affectedChildren.length; i++) {
			if ((affectedChildren[i].getFlags() & IResourceDelta.TYPE) != 0) {
				runnables.add(getRefreshRunnable(resource));
				return;
			}
		}

		// Opening a project just affects icon, but we need to refresh when
		// a project is closed because if child items have not yet been created
		// in the tree we still need to update the item's children
		int changeFlags = delta.getFlags();
		if ((changeFlags & IResourceDelta.OPEN) != 0) {
			if (resource.isAccessible())  {
				runnables.add(getUpdateRunnable(resource));
			} else {
				runnables.add(getRefreshRunnable(resource));
				return;
			}
		}
		// Check the flags for changes the Navigator cares about.
		// See ResourceLabelProvider for the aspects it cares about.
		// Notice we don't care about F_CONTENT or F_MARKERS currently.
		if ((changeFlags & (IResourceDelta.SYNC
				| IResourceDelta.TYPE | IResourceDelta.DESCRIPTION)) != 0) {
			runnables.add(getUpdateRunnable(resource));
		}
		// Replacing a resource may affect its label and its children
		if ((changeFlags & IResourceDelta.REPLACED) != 0) {
			runnables.add(getRefreshRunnable(resource));
			return;
		}

		// Handle changed children .
		for (int i = 0; i < affectedChildren.length; i++) {
			processDelta(affectedChildren[i], runnables);
		}

		// @issue several problems here:
		//  - should process removals before additions, to avoid multiple equal
		// elements in viewer
		//   - Kim: processing removals before additions was the indirect cause of
		// 44081 and its varients
		//   - Nick: no delta should have an add and a remove on the same element,
		// so processing adds first is probably OK
		//  - using setRedraw will cause extra flashiness
		//  - setRedraw is used even for simple changes
		//  - to avoid seeing a rename in two stages, should turn redraw on/off
		// around combined removal and addition
		//   - Kim: done, and only in the case of a rename (both remove and add
		// changes in one delta).

		IResourceDelta[] addedChildren = delta
				.getAffectedChildren(IResourceDelta.ADDED);
		IResourceDelta[] removedChildren = delta
				.getAffectedChildren(IResourceDelta.REMOVED);

		if (addedChildren.length == 0 && removedChildren.length == 0) {
			return;
		}

		final Object[] addedObjects;
		final Object[] removedObjects;

		// Process additions before removals as to not cause selection
		// preservation prior to new objects being added
		// Handle added children. Issue one update for all insertions.
		int numMovedFrom = 0;
		int numMovedTo = 0;
		if (addedChildren.length > 0) {
			addedObjects = new Object[addedChildren.length];
			for (int i = 0; i < addedChildren.length; i++) {
				addedObjects[i] = addedChildren[i].getResource();
				if ((addedChildren[i].getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					++numMovedFrom;
				}
			}
		} else {
			addedObjects = new Object[0];
		}

		// Handle removed children. Issue one update for all removals.
		if (removedChildren.length > 0) {
			removedObjects = new Object[removedChildren.length];
			for (int i = 0; i < removedChildren.length; i++) {
				removedObjects[i] = removedChildren[i].getResource();
				if ((removedChildren[i].getFlags() & IResourceDelta.MOVED_TO) != 0) {
					++numMovedTo;
				}
			}
		} else {
			removedObjects = new Object[0];
		}
		// heuristic test for items moving within same folder (i.e. renames)
		final boolean hasRename = numMovedFrom > 0 && numMovedTo > 0;
		
		Runnable addAndRemove = new Runnable(){
			public void run() {
				if (viewer instanceof AbstractTreeViewer) {
					AbstractTreeViewer treeViewer = (AbstractTreeViewer) viewer;
					// Disable redraw until the operation is finished so we don't
					// get a flash of both the new and old item (in the case of
					// rename)
					// Only do this if we're both adding and removing files (the
					// rename case)
					if (hasRename) {
						treeViewer.getControl().setRedraw(false);
					}
					try {
						if (addedObjects.length > 0) {
							treeViewer.add(resource, addedObjects);
						}
						if (removedObjects.length > 0) {
							treeViewer.remove(removedObjects);
						}
					}
					finally {
						if (hasRename) {
							treeViewer.getControl().setRedraw(true);
						}
					}
				} else {
					((StructuredViewer) viewer).refresh(resource);
				}
			}
		};
		runnables.add(addAndRemove);
	}
	/**
	 * Return a runnable for refreshing a resource.
	 * @param resource
	 * @return Runnable
	 */
	private Runnable getRefreshRunnable(final IResource resource) {
		return new Runnable(){
			public void run() {
				((StructuredViewer) viewer).refresh(resource);
			}
		};
	}

		/**
		 * Return a runnable for refreshing a resource.
		 * @param resource
		 * @return Runnable
		 */
	private Runnable getUpdateRunnable(final IResource resource) {
		return new Runnable(){
			public void run() {
				((StructuredViewer) viewer).update(resource, null);
			}
		};
	}
}