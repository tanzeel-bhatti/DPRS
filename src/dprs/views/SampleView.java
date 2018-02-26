package dprs.views;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.lums.serl.dprs.MethodVisitor;
import com.lums.serl.dprs.Node;
import com.lums.serl.dprs.Node.TYPE;

/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "dprs.views.SampleView";

	@Inject IWorkbench workbench;
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action action3;
	private Action doubleClickAction;
//	private StyledText text;
//	private StyleRange style;
	 
//	class TreeObject implements IAdaptable {
//		private String name;
//		private TreeParent parent;
//		
//		public TreeObject(String name) {
//			this.name = name;
//		}
//		public String getName() {
//			return name;
//		}
//		public void setParent(TreeParent parent) {
//			this.parent = parent;
//		}
//		public TreeParent getParent() {
//			return parent;
//		}
//		@Override
//		public String toString() {
//			return getName();
//		}
//		@Override
//		public <T> T getAdapter(Class<T> key) {
//			return null;
//		}
//	}
	
//	class TreeParent extends TreeObject {
//		private ArrayList children;
//		public TreeParent(String name) {
//			super(name);
//			children = new ArrayList();
//		}
//		public void addChild(TreeObject child) {
//			children.add(child);
//			child.setParent(this);
//		}
//		public void removeChild(TreeObject child) {
//			children.remove(child);
//			child.setParent(null);
//		}
//		public TreeObject [] getChildren() {
//			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
//		}
//		public boolean hasChildren() {
//			return children.size()>0;
//		}
//	}

	class ViewContentProvider implements ITreeContentProvider {

		public Object[] getElements(Object parent) {
			return ((Node)parent).getChildren();
		}
		public Object getParent(Object child) {
			return ((Node)child).getParent();
		}
		public Object [] getChildren(Object parent) {
			return ((Node)parent).getChildren();
		}
		public boolean hasChildren(Object parent) {
			return ((Node)parent).hasChildren();
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
//		private void initialize() {
//			TreeObject to1 = new TreeObject("uniqueInstance: private static ChessGameKenai.ChessBoardView _instance");
//			TreeObject to2 = new TreeObject("uniqueInstance: private static ChessGameKenai.Chess_Data _instance");
//			TreeObject to3 = new TreeObject("uniqueInstance: private static data.Bulletin _bulletinBoard");
//			TreeParent p1 = new TreeParent("Singleten Pattern");
//			
//			
//			p1.addChild(to1);
//			p1.addChild(to2);
//			p1.addChild(to3);
//			
//			TreeParent p2 = new TreeParent("State/Strategy Pattern");
//			TreeParent p3 = new TreeParent("Context: data.ComputerPlayer");
//			TreeObject to4 = new TreeObject("State/Strategy: data.AI ");
//			TreeParent p31 = new TreeParent("Context: ChessGameKenai.ChessBoardView");
//			TreeObject to41 = new TreeObject("State/Strategy: ChessGameKenai.Board");
//			p31.addChild(to41);
//			p3.addChild(to4);
//			p2.addChild(p3);
//			p2.addChild(p31);
//			
//			TreeParent project_chess = new TreeParent("Chess Game");
//			project_chess.addChild(p1);
//			project_chess.addChild(p2);
//			
//			TreeParent project_grid = new TreeParent("Grid Game");
//			project_grid.addChild(p1);
//			project_grid.addChild(p2);
//			
//			TreeParent root = new TreeParent("Recommendations");
//			root.addChild(project_chess);
//			root.addChild(project_grid);
//			
//			invisibleRoot = new TreeParent("");
//			invisibleRoot.addChild(root);
//		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			if (((Node)obj).getType() == TYPE.PROJECT)
				return  ((Node)obj).getName() + " [Similarity Score: " + String.valueOf(((Node)obj).getScore()) + "%]";
			return ((Node)obj).getName();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_FILE;
			if (((Node)obj).getType() == TYPE.ROOT)
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
			else if (((Node)obj).getType() == TYPE.PROJECT)
				imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			return workbench.getSharedImages().getImage(imageKey);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
	
//		Composite container = new Composite(parent, SWT.BORDER);
//		text = new StyledText(container, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
//		FormData fd_text = new FormData();
//		fd_text.top = new FormAttachment(0);
//		text.setLayoutData(fd_text);
	viewer.setContentProvider(new ViewContentProvider());
	viewer.setInput(null);
	viewer.setLabelProvider(new ViewLabelProvider());
	viewer.setComparator(new ViewerComparator()
			{
		@Override
		public int category(Object element) {
			// TODO Auto-generated method stub
			return (int)((Node)element).getScore()*-1;
		}
			});

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "DPRS.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}
	
	public void setInput(Object input) {
		viewer.setInput(input);
		viewer.refresh();
		viewer.expandToLevel(2);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
		manager.add(new Separator());
		manager.add(action3);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Similarity Score is: " + ((Node)obj).getScore());
			}
		};
		action1.setText("Show Similarity Score");
		action1.setToolTipText("Show Similarity Score");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Project Category is: " + ((Node)obj).getCategory());
			}
		};
		action2.setText("Show Project's Category");
		action2.setToolTipText("Show Project's Category");
		action2.setImageDescriptor(workbench.getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				if (((Node)obj).getType() == TYPE.PROJECT)
				{
					showMessage("Topics of the Project Are:\n\n"+  String.join(", ", ((Node)obj).getTopics()));
				}
			}
		};
		
		action3 = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				if (((Node)obj).getType() == TYPE.CLASS || ((Node)obj).getType() == TYPE.METHOD)
				{
					showMessage("Source File Path:\n\n"+  ((Node)obj).getSourcePath());
				}
			}
		};
		action3.setText("Show Source Path");
		action3.setToolTipText("Show Source Path");
		action3.setImageDescriptor(workbench.getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				if (((Node)obj).getType() == TYPE.CLASS || ((Node)obj).getType() == TYPE.METHOD)
				{
					File fileToOpen = new File(((Node)obj).getSourcePath());
					 
					if (fileToOpen.exists() && fileToOpen.isFile()) {
					    IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
					    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					 
					    try {
					    	IEditorPart editorPart = IDE.openEditorOnFileStore( page, fileStore );
					    	ITextEditor textEditor = (ITextEditor) editorPart;
					    	//////////////////////////////////////////////////
					    	IFile ifile= getIFile(fileToOpen);
					    	String text = getFileContent(fileToOpen);
					    	//////////////////////////////////////////////////
					    	if (((Node)obj).getType() == TYPE.METHOD)
					    	{
//						    	if( textEditor instanceof ITextEditor ) {
//					                IDocument document = textEditor.getDocumentProvider().getDocument( textEditor.getEditorInput() ); 
//					                // get IRegion instance of the line 
//					                MethodVisitor mv = new MethodVisitor(fileToOpen, ((Node)obj).getName());
//					                IRegion region = document.getLineInformation( mv.getBeginLine() - 1);
//					                
//					                // highlight line 
////					                textEditor.setHighlightRange( region.getOffset(), region.getLength(), true );
//					                textEditor.selectAndReveal(region.getOffset(), region.getLength());
//						    	}
					    		//////////////////////////////////////////////////////////////
					    		try{
					    			MethodVisitor mv = new MethodVisitor(fileToOpen, ((Node)obj).getName());
									IMarker marker = ifile.createMarker("dprs.views.customtextmarker");
									IDocumentProvider dp = textEditor.getDocumentProvider();
									IDocument doc = dp.getDocument(textEditor.getEditorInput());
									int startChar = doc.getLineOffset(mv.getBeginLine() - 1);
									int endChar = startChar + (doc.getLineOffset(mv.getEndLine()) - startChar);
//									textEditor.setHighlightRange(startChar, endChar, true);
									marker.setAttribute(IMarker.CHAR_START, startChar);
									marker.setAttribute(IMarker.CHAR_END, endChar);
									IDE.openEditor(page, marker);
								}
								catch(Exception e){
									e.printStackTrace();
								}
					    		////////////////////////////////////////////////////////////////
					    	}
				                
				                
					    } catch ( PartInitException /*| ParseException | IOException | BadLocationException */e ) {
					        //Put your exception handler here if you wish to
					    }
					} else {
						showMessage("Source File Path does not exist:\n\n"+  ((Node)obj).getSourcePath());
//						AlertDialog.displayErrorDialog("File not found", 
//								"The file couldn't be opened because it's not currently on your file system.");
					}					
				}
			}
		};
	}
	
	private IFile getIFile(File fileToOpen){
//		IPath location= Path.fromOSString(fileToOpen.getAbsolutePath()); 
//		IWorkspace workspace= ResourcesPlugin.getWorkspace();    
//		return workspace.getRoot().getFileForLocation(location);
		IFile file = null;
		try {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject("External Files");
		if (!project.exists())
		    project.create(null);
		if (!project.isOpen())
		    project.open(null);
		IPath location = new Path(fileToOpen.getAbsolutePath());
		 file = project.getFile(location.lastSegment());
		file.createLink(location, IResource.NONE, null);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return file;
	}
	
	private String getFileContent(File fileToOpen) {
		String content="";
		try {
			FileInputStream fis = new FileInputStream(fileToOpen);
			byte[] data = new byte[(int) fileToOpen.length()];
			fis.read(data);
			fis.close();
			content = new String(data, "UTF-8");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return content;
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Recommendation System",
			message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
