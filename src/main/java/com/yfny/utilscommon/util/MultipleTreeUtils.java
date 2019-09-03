package com.yfny.utilscommon.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * 数据列表转树结构工具
 * Created by jisongZhou on 2017/12/28.
 **/

public class MultipleTreeUtils {

    public static String getTreeList(List dataList) {
        String result = "";
        if (dataList != null && dataList.size() > 0) {
            // 节点列表（散列表，用于临时存储节点对象）
            HashMap nodeList = new HashMap();
            // 根节点
            Node root = null;
            // 根据结果集构造节点列表（存入散列表）
            for (Iterator it = dataList.iterator(); it.hasNext(); ) {
                Map dataRecord = (Map) it.next();
                Node node = new Node();
                node.id = (String) dataRecord.get("id");
                node.text = (String) dataRecord.get("text");
                node.parentId = (String) dataRecord.get("parentId");
                nodeList.put(node.id, node);
            }
            // 构造无序的多叉树
            Set entrySet = nodeList.entrySet();
            for (Iterator it = entrySet.iterator(); it.hasNext(); ) {
                Node node = (Node) ((Map.Entry) it.next()).getValue();
                if (node.parentId == null || node.parentId.equals("")) {
                    root = node;
                } else {
                    ((Node) nodeList.get(node.parentId)).addChild(node);
                }
            }
            // 输出无序的树形菜单的JSON字符串
            //System.out.println(root.toString());
            // 对多叉树进行横向排序
            root.sortChildren();
            // 输出有序的树形菜单的JSON字符串
            result = root.toString();
            //System.out.println(root.toString());
        }
        return result;
    }

    /**
     * 节点类
     */
    static class Node {
        /**
         * 节点编号
         */
        private String id;
        /**
         * 节点内容
         */
        private String text;
        /**
         * 父节点编号
         */
        private String parentId;
        /**
         * 孩子节点列表
         */
        private Children children = new Children();

        // 先序遍历，拼接JSON字符串
        public String toString() {
            String result = "{"
                    + "\"id\" : \"" + id + "\""
                    + ", \"text\" : \"" + text + "\"";

            if (children != null && children.getSize() != 0) {
                result += ", \"children\" : " + children.toString();
            } else {
                result += ", \"children\" : \"[]\"";
            }

            return result + "}";
        }

        // 兄弟节点横向排序
        private void sortChildren() {
            if (children != null && children.getSize() != 0) {
                children.sortChildren();
            }
        }

        // 添加孩子节点
        private void addChild(Node node) {
            this.children.addChild(node);
        }
    }

    /**
     * 孩子列表类
     */
    static class Children {
        private List list = new ArrayList();

        private int getSize() {
            return list.size();
        }

        private void addChild(Node node) {
            list.add(node);
        }

        // 拼接孩子节点的JSON字符串
        public String toString() {
            String result = "[";
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                result += ((Node) it.next()).toString();
                result += ",";
            }
            result = result.substring(0, result.length() - 1);
            result += "]";
            return result;
        }

        // 孩子节点排序
        public void sortChildren() {
            // 对本层节点进行排序
            // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
            Collections.sort(list, new NodeIDComparator());
            // 对每个节点的下一层节点进行排序
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                ((Node) it.next()).sortChildren();
            }
        }
    }

    /**
     * 节点比较器
     */
    static class NodeIDComparator implements Comparator {
        // 按照节点编号比较
        public int compare(Object o1, Object o2) {
            int j1 = Integer.parseInt(((Node) o1).id);
            int j2 = Integer.parseInt(((Node) o2).id);
            return (Integer.compare(j1, j2));
        }
    }

    /**
     * 获取目录树
     *
     * @param dataList 数据列表
     * @return json字符串
     */
    public static String getTree(List dataList) {
        String result = "";
        if (dataList != null && dataList.size() > 0) {
            // 节点列表（散列表，用于临时存储节点对象）
            JSONArray json = new JSONArray();
            // 根据结果集构造节点列表（存入散列表）
            for (Iterator it = dataList.iterator(); it.hasNext(); ) {
                Map dataRecord = (Map) it.next();
                JSONObject jo = new JSONObject();
                jo.put("id", (String) dataRecord.get("id"));
                jo.put("text", (String) dataRecord.get("text"));
//                jo.put("state", (String) dataRecord.get("state"));
//                jo.put("type",(String)dataRecord.get("type"));
//                jo.put("status",(String)dataRecord.get("status"));
//                jo.put("iconCls",(String)dataRecord.get("iconCls"));
//                jo.put("number",(String)dataRecord.get("number"));
//                jo.put("description",(String)dataRecord.get("description"));
//                jo.put("path",(String)dataRecord.get("path"));
//                jo.put("metaModelId",dataRecord.get("metaModelId"));
//                jo.put("metaModelName",dataRecord.get("metaModelName"));
//                jo.put("fileName",dataRecord.get("fileName"));
//                jo.put("createBy",dataRecord.get("createBy"));
//                jo.put("modelType",dataRecord.get("modelType"));
//                jo.put("count",dataRecord.get("count"));
                json.put(jo);
            }
            result = json.toString();
        }
        return result;
    }
}

