package com.test;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

//import com.lums.serl.dprs.DBConnection;
import com.lums.serl.dprs.Hello;
import com.lums.serl.dprs.helper;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;

public class Test {

	protected Shell shell;
	private Hello _hello = null;
	
	public Test()
	{
		_hello = new Hello();
		SwingUtilities.invokeLater(_hello);
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Test window = new Test();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		display.syncExec(new Runnable() {
		    public void run() {
		    	createContents();
				shell.open();
				shell.layout();
//				while (!shell.isDisposed()) {
//					if (!display.readAndDispatch()) {
//						display.sleep();
//					}
//				}
		    }
		});		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Button btnClickMe = new Button(shell, SWT.NONE);
					
		btnClickMe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
										
					for(int i=0; i<100; i++)
					{
						helper.InsertText("Printing line numer "  + i + "\n");
					}
					
        		} catch (Exception exp) {
        			exp.printStackTrace();
        			
        		}
			}
		});
		btnClickMe.setBounds(349, 10, 75, 25);
		btnClickMe.setText("Click Me!");

	}
}
