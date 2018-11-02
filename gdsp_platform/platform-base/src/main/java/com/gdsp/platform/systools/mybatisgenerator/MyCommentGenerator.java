package com.gdsp.platform.systools.mybatisgenerator;

import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 控制Model的注释
 * @author 七言
 *
 */
public class MyCommentGenerator implements CommentGenerator {

	@Override
	public void addConfigurationProperties(Properties properties) {
		
		
	}

	@Override
	public void addFieldComment(Field field,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		
	    if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
	        field.addJavaDocLine("/**");
	        field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
	       // addJavadocTag(field, false);
	        field.addJavaDocLine(" */");
	    }
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		

		
	}

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addClassComment(InnerClass innerClass,
			IntrospectedTable introspectedTable) {
		
		
	}

	@Override
	public void addClassComment(InnerClass innerClass,
			IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		
		
	}

	@Override
	public void addEnumComment(InnerEnum innerEnum,
			IntrospectedTable introspectedTable) {
		
		
	}

	@Override
	public void addGetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		
		
	}

	@Override
	public void addSetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		
		
	}

	@Override
	public void addGeneralMethodComment(Method method,
			IntrospectedTable introspectedTable) {
		
		
	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		
		
	}

	@Override
	public void addComment(XmlElement xmlElement) {
		
		
	}

	@Override
	public void addRootComment(XmlElement rootElement) {
		
		
	}

	@Override
	public void addClassAnnotation(InnerClass arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {
		
		
	}

	@Override
	public void addFieldAnnotation(Field arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {
		
		
	}

	@Override
	public void addFieldAnnotation(Field arg0, IntrospectedTable arg1, IntrospectedColumn arg2,
			Set<FullyQualifiedJavaType> arg3) {
		
		
	}

	@Override
	public void addGeneralMethodAnnotation(Method arg0, IntrospectedTable arg1, Set<FullyQualifiedJavaType> arg2) {
		
		
	}

	@Override
	public void addGeneralMethodAnnotation(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2,
			Set<FullyQualifiedJavaType> arg3) {
		
		
	}

}
