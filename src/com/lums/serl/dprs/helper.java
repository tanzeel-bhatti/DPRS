package com.lums.serl.dprs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class helper extends OutputStream {
	
	private static Text _textBox;	
	private String totalString;
	private ArrayList<Integer> chars = new ArrayList<Integer>();	
	
	public static void setTextBox(Text textBox)
	{
		_textBox = textBox;		
	}
	
	public static void InsertText(String text)
	{
		_textBox.insert(text);
	}

	@Override
	 public void write(int b) {
		if(b == 10)
		{
			chars.add(b);
			int length = chars.size();			
			int[] bytes = chars.stream()
					  .mapToInt(Integer::intValue)
					  .toArray();
			write(bytes, 0, length);
		}
		else
		{
			chars.add(b);
		}		
	 }
	
	 public void write(int[] bytes, int offset, int length) {
	    String s = new String(bytes, offset, length);
	    	
	    	Display.getDefault().syncExec(new Runnable() {
	    	    public void run() {
	    	         _textBox.append(s);
	    	         chars.clear();
	    	    }
	    	});	       	
	 }
}
