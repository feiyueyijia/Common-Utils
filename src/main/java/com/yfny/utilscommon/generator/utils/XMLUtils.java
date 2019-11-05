package com.yfny.utilscommon.generator.utils;

import com.alibaba.fastjson.JSONObject;
import com.yfny.utilscommon.generator.entity.BCodeMaterials;
import com.yfny.utilscommon.generator.invoker.RelationInvoker;
import com.yfny.utilscommon.util.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jisongZhou on 2017/12/28.
 **/
public class XMLUtils {

    private static Map<String, BCodeMaterials> tableMap;
    private static Map<String, JSONObject> columnMap;
    private static Map<String, String> relationMap;

    /**
     * 利用dom4j进行xml文档的读取操作
     */
    public static Map<String, BCodeMaterials> parserXml(File file) {
        //public static void parserXml(String fileString) {

        tableMap = new HashMap<>();
        columnMap = new HashMap<>();
        relationMap = new HashMap<>();

        Document document = null;

        // 使用 SAXReader 解析 XML 文档catalog.xml：
        SAXReader saxReader = new SAXReader();

        try {
            // 将文件转为XML
            document = saxReader.read(file);
            // 将字符串转为XML
            // document = DocumentHelper.parseText(fileString);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 获取根节点
        Element root = document.getRootElement();
        // 打印节点名称
        //System.out.println("<" + root.getName() + ">");

        parserElement(root);
        //System.out.println("</" + root.getName() + ">");

        return tableMap;
    }

    private static void parserElement(Element element) {
        // 获取指定节点下的子节点遍历
        Iterator<?> iter = element.elementIterator();
        // 遍历element节点
        while (iter.hasNext()) {
            // 获取当前子节点
            Element subElement = (Element) iter.next();
            //System.out.println("<" + subElement.getName() + ">");
            // 获取当前子节点的属性遍历
            Iterator<?> attrList = subElement.attributeIterator();
            while (attrList.hasNext()) {
                screenElement(subElement);
                Attribute attr = (Attribute) attrList.next();
                //System.out.println(attr.getName() + "=" + attr.getValue());
            }
            parserElement(subElement);
            //System.out.println("</" + subElement.getName() + ">");
        }
        matchRelation();
    }

    private static void screenElement(Element element) {
        //唯一标识
        Attribute id = element.attribute("id");
        if (id != null) {
            //父级唯一标识
            Attribute parent = element.attribute("parent");
            //关系标识
            Attribute edge = element.attribute("edge");
            if (parent != null) {
                if (edge != null) {
                    //关系处理逻辑
                    //关系主键
                    Attribute source = element.attribute("source");
                    //关系外键
                    Attribute target = element.attribute("target");
                    if (source != null && target != null) {
                        relationMap.put(target.getValue(), source.getValue());
                    }
                } else {
                    //对象处理逻辑
                    if ("1".equals(parent.getValue())) {
                        //表头处理逻辑
                        //表名
                        Attribute value = element.attribute("value");
                        //表备注
                        Attribute comment = element.attribute("comment");

                        String tableName = "";
                        String className = "";
                        String description = "";
                        String foreignKey = "";
                        Map<String, String> relationClassNameMap = new HashMap<>();

                        String valueStr = value.getValue();
                        String commentStr = comment.getValue();

                        String[] classNames = StringUtils.split(valueStr, "_");

                        tableName = valueStr;
                        className = classNames[classNames.length - 1];
                        className = StringUtils.toCapitalizeCamelCase(className);
                        description = commentStr;

                        BCodeMaterials materials = new BCodeMaterials();
                        materials.setId(id.getValue());
                        materials.setTableName(tableName);
                        materials.setClassName(className);
                        materials.setDescription(description);
//                        materials.setForeignKey(foreignKey);
//                        materials.setRelationClassNameMap(relationClassNameMap);

                        tableMap.put(materials.getId(), materials);
                    } else {
                        //字段标识
                        Attribute object = element.attribute("object");
                        if (object != null) {
                            //字段处理逻辑
                            //字段名
                            Attribute name = element.attribute("name");
                            JSONObject json = new JSONObject();
                            json.put("parent", parent.getValue());
                            json.put("name", name.getValue());
                            columnMap.put(id.getValue(), json);
                        }
                    }
                }
            }
        }
    }

    private static void matchRelation() {
        if (relationMap != null && relationMap.size() > 0) {
            for (String target : relationMap.keySet()) {
                String source = relationMap.get(target);
                if (columnMap.containsValue(target) && columnMap.containsValue(source)) {
                    String tParent = columnMap.get(target).getString("parent");
                    String tForeignKey = columnMap.get(target).getString("name");
                    String tForeignKeyPropertyName = StringUtil.columnName2PropertyName(tForeignKey);
                    String tClassName = tableMap.get(tParent).getClassName();
                    String sParent = columnMap.get(target).getString("parent");
                    String relationType = RelationInvoker.Builder.ONE_TO_MANY;
//                    tableMap.get(tParent).setForeignKey(tForeignKeyPropertyName);
//                    tableMap.get(sParent).getRelationClassNameMap().put(tClassName, relationType);
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/ZXKJ/Documents/Tencent Files/568777035/FileRecv/测试表单.xml");
        Map<String, BCodeMaterials> stringBCodeMaterialsMap = XMLUtils.parserXml(file);
        System.out.println("----------------------------------------------------------");
    }
}
