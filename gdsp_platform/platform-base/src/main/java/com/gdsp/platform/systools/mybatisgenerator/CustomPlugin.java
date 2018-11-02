package com.gdsp.platform.systools.mybatisgenerator;

import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 生成内容主体
 * @author 七言
 *
 */
public class CustomPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        AbstractXmlElementGenerator elementGenerator = new CustomAbstractXmlElementGenerator();
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.addElements(document.getRootElement());
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {
	@Override
	public void addElements(XmlElement xmlElement) {
		StringBuilder builder = new StringBuilder();
		for (IntrospectedColumn introspectedColumns : introspectedTable.getPrimaryKeyColumns()) {
			builder.setLength(0);
			builder.append("where ");
			builder.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumns));
			builder.append(" = ");
			builder.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumns));
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		Iterator<IntrospectedColumn> introspectedColumn = introspectedTable.getAllColumns().iterator();
		while (introspectedColumn.hasNext()) {
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn.next()));
			if (!introspectedColumn.hasNext()) {
				sb.append(" ");
			} else {
				sb.append(",");
			}
		}
		sb.append("from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		TextElement selectText = new TextElement(sb.toString());
		StringBuilder ss = new StringBuilder();
		ss.append(" where ");
		ss.append(introspectedTable.getPrimaryKeyColumns().iterator().next().getActualColumnName());
		ss.append(" in ");
		TextElement selectTextt = new TextElement(ss.toString());
		XmlElement selectElement = new XmlElement("foreach");
		selectElement.addAttribute(new Attribute("collection", "array"));
		selectElement.addAttribute(new Attribute("index", "index"));
		selectElement.addAttribute(new Attribute("item", "item"));
		selectElement.addAttribute(new Attribute("open", "("));
		selectElement.addAttribute(new Attribute("separator", ","));
		selectElement.addAttribute(new Attribute("close", ")"));
		ss.setLength(0);
		selectElement.addElement(new TextElement("${item}"));

		// 增加load
		XmlElement load = new XmlElement("select");
		load.addAttribute(new Attribute("id", "load"));
		load.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		load.addAttribute(new Attribute("resultType", introspectedTable.getBaseRecordType()));
		StringBuilder ssb = new StringBuilder();
		ssb.append("select ");
		Iterator<IntrospectedColumn> introspectedColumnd = introspectedTable.getAllColumns().iterator();
		while (introspectedColumnd.hasNext()) {
			ssb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumnd.next()));
			if (introspectedColumnd.hasNext()) {
				ssb.append(',');
			}
			load.addElement(new TextElement(ssb.toString()));
			// set up for the next column
			if (introspectedColumnd.hasNext()) {
				ssb.setLength(0);
				OutputUtilities.xmlIndent(ssb, 1);
			}
		}
		ssb.setLength(0);
		ssb.append("from ");
		ssb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		load.addElement(new TextElement(ssb.toString()));
		load.addElement(new TextElement(builder.toString()));
		xmlElement.addElement(load);

		// 增加update
		XmlElement update = new XmlElement("update");
		update.addAttribute(new Attribute("id", "update"));
		String parameterType;
		parameterType = introspectedTable.getBaseRecordType();
		update.addAttribute(new Attribute("parameterType", parameterType));
		StringBuilder stringb = new StringBuilder();
		stringb.append("update ");
		stringb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		update.addElement(new TextElement(stringb.toString()));

		// set up for first column
		stringb.setLength(0);
		stringb.append("set ");
		Iterator<IntrospectedColumn> iter = ListUtilities
				.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspecte = iter.next();
			stringb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspecte));
			stringb.append(" = ");
			stringb.append(MyBatis3FormattingUtilities.getParameterClause(introspecte));
			if (iter.hasNext()) {
				stringb.append(',');
			}
			update.addElement(new TextElement(stringb.toString()));
			// set up for the next column
			if (iter.hasNext()) {
				stringb.setLength(0);
				OutputUtilities.xmlIndent(stringb, 1);
			}
		}
		update.addElement(new TextElement(builder.toString()));
		xmlElement.addElement(update);

		// 增加delete
		XmlElement delete = new XmlElement("delete");
		delete.addAttribute(new Attribute("id", "delete"));
		String parameterClass;
		parameterClass = introspectedTable.getBaseRecordType();
		delete.addAttribute(new Attribute("parameterType", parameterClass));

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("delete from ");
		stringBuilder.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		delete.addElement(new TextElement(stringBuilder.toString()));
		delete.addElement(selectTextt);
		delete.addElement(selectElement);
		xmlElement.addElement(delete);

		// 增加findAll
		XmlElement findAll = new XmlElement("select");
		findAll.addAttribute(new Attribute("id", "findAll"));
		findAll.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		findAll.addElement(selectText);
		xmlElement.addElement(findAll);

		// 增加findPage
		XmlElement findPage = new XmlElement("select");
		findPage.addAttribute(new Attribute("id", "findPage"));
		findPage.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		findPage.addElement(selectText);
		xmlElement.addElement(findPage);

		// 增加findListByCondition
		XmlElement findListByCondition = new XmlElement("select");
		findListByCondition.addAttribute(new Attribute("id", "findlistByCondition"));
		findListByCondition.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		findListByCondition.addElement(selectText);
		XmlElement TrimElement = new XmlElement("trim");
		TrimElement.addAttribute(new Attribute("prefix", "where"));
		TrimElement.addAttribute(new Attribute("prefixOverrides", "and | or"));
		XmlElement NotNullElement = new XmlElement("if");
		sb.setLength(0);
		sb.append("cond != null");
		NotNullElement.addAttribute(new Attribute("test", sb.toString()));
		NotNullElement.addElement(new TextElement("${cond._CONDITION_}"));
		TrimElement.addElement(NotNullElement);
		findListByCondition.addElement(TrimElement);
		xmlElement.addElement(findListByCondition);

		// 增加findPageByCondition
		XmlElement findPageByCondition = new XmlElement("select");
		findPageByCondition.addAttribute(new Attribute("id", "findPageByCondition"));
		findPageByCondition.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		findPageByCondition.addElement(selectText);
		XmlElement selectTrimElement = new XmlElement("trim");
		selectTrimElement.addAttribute(new Attribute("prefix", "where"));
		selectTrimElement.addAttribute(new Attribute("prefixOverrides", "and | or"));
		XmlElement selectNotNullElement = new XmlElement("if");
		sb.setLength(0);
		sb.append("cond != null");
		selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
		selectNotNullElement.addElement(new TextElement("${cond._CONDITION_}"));
		selectTrimElement.addElement(selectNotNullElement);
		findPageByCondition.addElement(selectTrimElement);
		xmlElement.addElement(findPageByCondition);

		// findListByIds
		XmlElement findListByIds = new XmlElement("select");
		findListByIds.addAttribute(new Attribute("id", "findListByIds"));
		findListByIds.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		findListByIds.addElement(selectText);
		findListByIds.addElement(selectTextt);
		findListByIds.addElement(selectElement);
		xmlElement.addElement(findListByIds);
	}

}
