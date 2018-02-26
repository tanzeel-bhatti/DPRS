package com.lums.serl.dprs;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class Hello implements Runnable {

	protected Shell shell;
	private Text pathRecommendation;
	private Text outputRecommendation;
	private Text outputRepoBuilding;
	private Text pathRepoBuilding;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Hello window = new Hello();
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
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}		
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(600, 400);
		shell.setText("Design Pattern Retrieval System");
		shell.setLayout(new GridLayout(1, false));
		// Menu and Menu Items
		//
		//
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmNew = new MenuItem(menu_1, SWT.NONE);
		mntmNew.setText("New");
		
		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.setText("Open");
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
            {
            	try {
        			shell.dispose();
        		} catch (Exception exp) {
        			exp.printStackTrace();
        		}
            }
		});
		
		MenuItem mntmConnection = new MenuItem(menu, SWT.CASCADE);
		mntmConnection.setText("Connection");
		
		Menu menu_2 = new Menu(mntmConnection);
		mntmConnection.setMenu(menu_2);
		
		MenuItem mntmConnectToDb = new MenuItem(menu_2, SWT.NONE);
		mntmConnectToDb.setText("Connect To DB");
		mntmConnectToDb.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) 
            {
            	try {
//        			DBConnection dbConnection = new DBConnection();
//        			dbConnection.open();
        		} catch (Exception exp) {
        			exp.printStackTrace();
        		}
            }
        });
		
		MenuItem mntmSqlServer = new MenuItem(menu_2, SWT.NONE);
		mntmSqlServer.setText("SQL SERVER");
		
		MenuItem mntmMysql = new MenuItem(menu_2, SWT.NONE);
		mntmMysql.setText("MySQL");
		
		MenuItem mntmOracle = new MenuItem(menu_2, SWT.NONE);
		mntmOracle.setText("Oracle");
		
		MenuItem mntmSqlLite = new MenuItem(menu_2, SWT.NONE);
		mntmSqlLite.setText("SQL Lite");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menu_3 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_3);
		
		MenuItem mntmAbout = new MenuItem(menu_3, SWT.NONE);
		mntmAbout.setText("About");
		mntmAbout.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) 
            {
            	try {
//        			About about = new About();
//        			about.open();
        		} catch (Exception exp) {
        			exp.printStackTrace();
        		}
            }
        });
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
								   
		
		ToolItem tltmBuildRepo = new ToolItem(toolBar, SWT.NONE);
		tltmBuildRepo.setImage(SWTResourceManager.getImage("E:\\MS\\MSProject\\rsz_green-play-button-icon-17897.png"));
		tltmBuildRepo.setToolTipText("Build Projects Repository");
		tltmBuildRepo.setText("Build Repository");
		tltmBuildRepo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) 
            {
            	try {
            		
        		} catch (Exception exp) {
        			exp.printStackTrace();
        		}
            }
        });
		
		ToolItem tltmRecommendPatterns = new ToolItem(toolBar, SWT.CHECK);
		tltmRecommendPatterns.setText("Recommend Patterns");
		
		Group grpDesignPatternRetrieval = new Group(shell, SWT.NONE);
		grpDesignPatternRetrieval.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpDesignPatternRetrieval.setLayout(new GridLayout(1,false));
		grpDesignPatternRetrieval.setText("Design Pattern Retrieval System");
		
		CTabFolder tabFolder = new CTabFolder(grpDesignPatternRetrieval, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tabFolder.setLayout(new GridLayout(1, true));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmRepoUtility = new CTabItem(tabFolder, SWT.NONE);
		tbtmRepoUtility.setText("Repo Building Utility");
		
		CTabItem tbtmRecommendationSytem = new CTabItem(tabFolder, SWT.NONE);
		tbtmRecommendationSytem.setText("Recommendation Sytem");
		
		Composite composite_1 = new Composite(tabFolder, SWT.RESIZE);
		tbtmRecommendationSytem.setControl(composite_1);
		composite_1.setLayout(new FormLayout());
		
		SashForm sashForm = new SashForm(composite_1, SWT.NONE);
		FormData fd_sashForm = new FormData();
		fd_sashForm.top = new FormAttachment(0, 119);
		fd_sashForm.left = new FormAttachment(0, 171);
		fd_sashForm.bottom = new FormAttachment(0, -53);
		fd_sashForm.right = new FormAttachment(0, 356);
		sashForm.setLayoutData(new FormData(SWT.FILL, SWT.FILL));
		
		Label labelProjectDirectoryRecommendation = new Label(composite_1, SWT.NONE);
		FormData fd_labelProjectDirectoryRecommendation = new FormData();
		fd_labelProjectDirectoryRecommendation.top = new FormAttachment(0, 13);
		fd_labelProjectDirectoryRecommendation.left = new FormAttachment(0, 10);
		labelProjectDirectoryRecommendation.setLayoutData(fd_labelProjectDirectoryRecommendation);
		labelProjectDirectoryRecommendation.setText("Projects Directory:");
		
		pathRecommendation = new Text(composite_1, SWT.BORDER);
		FormData fd_pathRecommendation = new FormData();
		fd_pathRecommendation.left = new FormAttachment(labelProjectDirectoryRecommendation, 12);
		fd_pathRecommendation.top = new FormAttachment(0, 10);
		pathRecommendation.setLayoutData(fd_pathRecommendation);
		
		Button browseRecommendation = new Button(composite_1, SWT.NONE);
		fd_pathRecommendation.right = new FormAttachment(100, -92);
		FormData fd_browseRecommendation = new FormData();
		fd_browseRecommendation.left = new FormAttachment(pathRecommendation, 6);
		fd_browseRecommendation.right = new FormAttachment(100, -10);
		fd_browseRecommendation.top = new FormAttachment(0, 8);
		browseRecommendation.setLayoutData(fd_browseRecommendation);
		browseRecommendation.setText("Browse");
		browseRecommendation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(shell);

		        // Set the initial filter path according
		        // to anything they've selected or typed in
		        dlg.setFilterPath(pathRecommendation.getText());

		        // Change the title bar text
		        dlg.setText("Projects Directory Chooser");

		        // Customizable message displayed in the dialog
		        dlg.setMessage("Select a directory containing projects");

		        // Calling open() will open and run the dialog.
		        // It will return the selected directory, or
		        // null if user cancels
		        String dir = dlg.open();
		        if (dir != null) {
		          // Set the text box to the new selection
		        	pathRecommendation.setText(dir);
		        }
			}
		});
		
//		outputRecommendation = new Text(composite_1, SWT.BORDER | SWT.RESIZE);
//		FormData fd_outputRecommendation = new FormData();
//		fd_outputRecommendation.bottom = new FormAttachment(100);
//		fd_outputRecommendation.right = new FormAttachment(100);
//		fd_outputRecommendation.top = new FormAttachment(labelProjectDirectoryRecommendation, 28);
//		fd_outputRecommendation.left = new FormAttachment(sashForm, 0, SWT.LEFT);
//		outputRecommendation.setLayoutData(fd_outputRecommendation);
		table = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.RESIZE | SWT.CHECK);		
		FormData fd_tableRecommendation = new FormData();
		fd_tableRecommendation.bottom = new FormAttachment(100);
		fd_tableRecommendation.right = new FormAttachment(100);
		fd_tableRecommendation.top = new FormAttachment(labelProjectDirectoryRecommendation, 28);
		fd_tableRecommendation.left = new FormAttachment(sashForm, 0, SWT.LEFT);
		table.setLayoutData(fd_tableRecommendation);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnSelect = new TableColumn(table, SWT.NONE);
		tblclmnSelect.setWidth(100);
		tblclmnSelect.setText("Select");
		
		TableColumn tblclmnPatternRecommended = new TableColumn(table, SWT.NONE);
		tblclmnPatternRecommended.setWidth(132);
		tblclmnPatternRecommended.setText("Pattern Recommended");
		
		TableColumn tblclmnCandidateClass = new TableColumn(table, SWT.NONE);
		tblclmnCandidateClass.setWidth(107);
		tblclmnCandidateClass.setText("Candidate Class");
		
		TableColumn tblclmnParticipatingMethods = new TableColumn(table, SWT.NONE);
		tblclmnParticipatingMethods.setWidth(137);
		tblclmnParticipatingMethods.setText("Participating Methods");
		
//		String[] titles = { " ", "C", "!", "Description", "Resource", "In Folder", "Location" };
//	    for (int i = 0; i < titles.length; i++) {
//	      TableColumn column = new TableColumn(table, SWT.NONE);
//	      column.setText(titles[i]);
//	    }
		
		Label labelOutputRecommendation = new Label(composite_1, SWT.NONE);
		FormData fd_labelOutputRecommendation = new FormData();
		fd_labelOutputRecommendation.bottom = new FormAttachment(table, -6);
		fd_labelOutputRecommendation.left = new FormAttachment(sashForm, 0, SWT.LEFT);
		labelOutputRecommendation.setLayoutData(fd_labelOutputRecommendation);
		labelOutputRecommendation.setText("Output:");
		
		tabFolder.setSelection(tbtmRepoUtility);
		
		Composite composite = new Composite(tabFolder, SWT.RESIZE);
		tbtmRepoUtility.setControl(composite);
		composite.setLayout(new FormLayout());
		
		SashForm sashForm_1 = new SashForm(composite, SWT.NONE);
		FormData fd_sashForm_1 = new FormData();
		fd_sashForm_1.top = new FormAttachment(0, 119);
		fd_sashForm_1.left = new FormAttachment(0, 171);
		fd_sashForm_1.bottom = new FormAttachment(0, -53);
		fd_sashForm_1.right = new FormAttachment(0, 356);
		sashForm_1.setLayoutData(new FormData(SWT.FILL, SWT.FILL));
		
		Label labelProjectDirectoryRepoBuilding = new Label(composite, SWT.NONE);
		FormData fd_labelProjectDirectoryRepoBuilding = new FormData();
		fd_labelProjectDirectoryRepoBuilding.left = new FormAttachment(0, 10);
		fd_labelProjectDirectoryRepoBuilding.top = new FormAttachment(0, 13);
		labelProjectDirectoryRepoBuilding.setLayoutData(fd_labelProjectDirectoryRepoBuilding);
		labelProjectDirectoryRepoBuilding.setText("Projects Directory:");
		
		pathRepoBuilding = new Text(composite, SWT.BORDER);
		FormData fd_pathRepoBuilding = new FormData();
		fd_pathRepoBuilding.left = new FormAttachment(labelProjectDirectoryRepoBuilding, 12);
		fd_pathRepoBuilding.top = new FormAttachment( 0, 10);
		pathRepoBuilding.setLayoutData(fd_pathRepoBuilding);
		fd_pathRepoBuilding.right = new FormAttachment(100, -92);
		
		Button browseRepoBuilding = new Button(composite, SWT.NONE);
		FormData fd_browseRepoBuilding = new FormData();
		fd_browseRepoBuilding.right = new FormAttachment(100, -10);
		fd_browseRepoBuilding.top = new FormAttachment(0, 8);
		fd_browseRepoBuilding.left = new FormAttachment(pathRepoBuilding, 6);
		browseRepoBuilding.setLayoutData(fd_browseRepoBuilding);
		browseRepoBuilding.setText("Browse");
		browseRepoBuilding.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(shell);

		        // Set the initial filter path according
		        // to anything they've selected or typed in
		        dlg.setFilterPath(pathRepoBuilding.getText());

		        // Change the title bar text
		        dlg.setText("Projects Directory Chooser");

		        // Customizable message displayed in the dialog
		        dlg.setMessage("Select a directory containing projects");

		        // Calling open() will open and run the dialog.
		        // It will return the selected directory, or
		        // null if user cancels
		        String dir = dlg.open();
		        if (dir != null) {
		          // Set the text box to the new selection
		        	pathRepoBuilding.setText(dir);
		        }
			}
		});
		
		outputRepoBuilding = new Text(composite, SWT.BORDER | SWT.RESIZE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		FormData fd_outputRepoBuilding = new FormData();
		fd_outputRepoBuilding.top = new FormAttachment(labelProjectDirectoryRepoBuilding, 28);
		fd_outputRepoBuilding.bottom = new FormAttachment(100);
		fd_outputRepoBuilding.right = new FormAttachment(100);
		fd_outputRepoBuilding.left = new FormAttachment(sashForm_1, 0, SWT.LEFT);
		outputRepoBuilding.setLayoutData(fd_outputRepoBuilding);	
		helper.setTextBox(outputRepoBuilding);
		
		Label labelOutputRepoBuilding = new Label(composite, SWT.NONE);
		FormData fd_labelOutputRepoBuilding = new FormData();
		fd_labelOutputRepoBuilding.bottom = new FormAttachment(outputRepoBuilding, -6);
		fd_labelOutputRepoBuilding.left = new FormAttachment(sashForm_1, 0, SWT.LEFT);
		labelOutputRepoBuilding.setLayoutData(fd_labelOutputRepoBuilding);
		labelOutputRepoBuilding.setText("Output:");
		
		Button btnCloseDialogBox = new Button(shell, SWT.NONE);
		btnCloseDialogBox.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnCloseDialogBox.setText("Close Dialog Box");
		btnCloseDialogBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// customized MessageDialog with configured buttons
				MessageDialog dialog = new MessageDialog(shell, "Close Design Pattern Retrieval System", null,
					"Are you sure you want to close the wizard?", MessageDialog.CONFIRM, new String[] { "OK", "Cancel" }, 0);
				if (dialog.open() == 0)
				{
					shell.dispose();
				}
			}
		});
	}

	@Override
	public void run() {
		try {
			Hello window = new Hello();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
