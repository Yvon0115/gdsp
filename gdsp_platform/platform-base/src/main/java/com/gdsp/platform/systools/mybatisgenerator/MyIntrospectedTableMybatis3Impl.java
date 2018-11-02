package com.gdsp.platform.systools.mybatisgenerator;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleAnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;
/**
 * 消除默认生成的无用内容
 * @author 七言
 *
 */
public class MyIntrospectedTableMybatis3Impl extends IntrospectedTableMyBatis3Impl {
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings, ProgressCallback progressCallback) {
        this.xmlMapperGenerator = new MySimpleXMLMapperGenerator();
        this.initializeAbstractGenerator(this.xmlMapperGenerator,warnings,progressCallback);
    }

    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if(this.context.getJavaClientGeneratorConfiguration()==null){
            return null;
        }else {
            String type =this.context.getJavaClientGeneratorConfiguration().getConfigurationType();
            Object javaGenerator;
            if("XMLMAPPER".equalsIgnoreCase(type)){
                javaGenerator = new MySimpleJavaClientGenerator();
            }else if("ANNOTATEDMAPPER".equalsIgnoreCase(type)){
                javaGenerator = new SimpleAnnotatedClientGenerator();
            }else if("MAPPER".equalsIgnoreCase(type)){
                javaGenerator = new MySimpleJavaClientGenerator();
            }else{
                javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
            }
            return (AbstractJavaClientGenerator)javaGenerator;
        }
    }

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        super.calculateJavaModelGenerators(warnings, progressCallback);
    }
}


/**
 * 消除xml中的无用内容
 * @author 七言
 *
 */
class MySimpleXMLMapperGenerator extends SimpleXMLMapperGenerator {
    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.12", table.toString()));
        XmlElement answer = new XmlElement("mapper");
        String namespace = this.introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace));
        this.context.getCommentGenerator().addRootComment(answer);
		this.addResultMapElement(answer);
        this.addInsertElement(answer);
        return answer;
    }
}

/**
 * 去除dao接口里默认生成的方法和注释
 * @author 七言
 *
 */
class MySimpleJavaClientGenerator extends SimpleJavaClientGenerator {
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.17",this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze =new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        String rootInterface = this.introspectedTable.getTableConfigurationProperty("rootInterface");
        if(!StringUtility.stringHasValue(rootInterface)){
            rootInterface = this.context.getJavaClientGeneratorConfiguration().getProperty("rootInterface");
        }
        if(StringUtility.stringHasValue(rootInterface)){
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }
        List<CompilationUnit> answer = new ArrayList<>();
        if(this.context.getPlugins().clientGenerated(interfaze,(TopLevelClass)null ,this.introspectedTable)){
            answer.add(interfaze);
        }
        List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
        if(extraCompilationUnits!=null){
            answer.addAll(extraCompilationUnits);
        }
        return answer;
    }
}
