package dprs.views;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import cc.mallet.javaTotext.javaTotext;

public class DPRSHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (firstElement instanceof IAdaptable)
	        {
	            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
	            IPath path = project.getLocation();
	            System.out.println(path);
				ProgressMonitorDialog d = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
				IRunnableWithProgress runnable = new IRunnableWithProgress() {
					
					@Override
					public void run(IProgressMonitor arg0) throws InvocationTargetException, InterruptedException {
						arg0.beginTask("Loading Recommendations for \"" + project.getName() + "\"", IProgressMonitor.UNKNOWN);
						try {
							javaTotext.run(new File(path.toString()));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				};
				try {
					d.run(true, true, runnable);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	    }
		// TODO Auto-generated method stub
		return null;
	}
	

}
