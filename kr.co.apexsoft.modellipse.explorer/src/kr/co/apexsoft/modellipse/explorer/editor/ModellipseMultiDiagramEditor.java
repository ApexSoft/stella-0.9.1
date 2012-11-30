package kr.co.apexsoft.modellipse.explorer.editor;

import java.util.Map;

import kr.co.apexsoft.modellipse.explorer.core.ApexModellipseProjectMap;
import kr.co.apexsoft.modellipse.explorer.core.ApexProjectWrapper;

import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

public class ModellipseMultiDiagramEditor extends PapyrusMultiDiagramEditor {

	IFile diFile;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		diFile = ((IFileEditorInput)input).getFile();
	}

	@Override
	public void dispose() {
		ApexModellipseProjectMap.clearModelServices(diFile);
		super.dispose();	
	}		
}