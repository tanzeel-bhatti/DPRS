package com.lums.serl.dprs;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;

/**
 * Simple visitor implementation for visiting MethodDeclaration nodes.
 */
public class MethodVisitor extends VoidVisitorAdapter{
	
	private String methodName;
	private int beginLine;
	private int endLine;
	
	public MethodVisitor(File src, String method) throws ParseException, IOException
	{
		this.setMethodName(method);
		CompilationUnit cu = JavaParser.parse(src);
		visit(cu, null);
	}
	
    @Override
    public void visit(MethodDeclaration m, Object arg) {
    	if (this.getMethodName().contains(m.getName()))
    	{
    		this.setBeginLine(m.getBeginLine());
    		this.setEndLine(m.getEndLine());
        }
    }

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getBeginLine() {
		return beginLine;
	}

	public void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
}

