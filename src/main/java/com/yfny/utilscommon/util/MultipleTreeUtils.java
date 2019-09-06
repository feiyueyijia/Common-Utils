package com.yfny.utilscommon.util;

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
                node.label = (String) dataRecord.get("name");
                node.parentId = (String) dataRecord.get("parentId");
                node.level = (int) dataRecord.get("level");
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
         * 节点标签
         */
        private String label;
        /**
         * 父节点编号
         */
        private String parentId;
        /**
         * 节点层级
         */
        private int level;
        /**
         * 孩子节点列表
         */
        private Children children = new Children();

        // 先序遍历，拼接JSON字符串
//        public String toString() {
//            String result = "{"
//                    + "\"id\" : \"" + id + "\""
//                    + ", \"label\" : \"" + label + "\"";
//
//            if (children != null && children.getSize() != 0) {
//                result += ", \"children\" : " + children.toString();
//            } else {
//                result += ", \"children\" : \"[]\"";
//            }
//
//            return result + "}";
//        }

        public String toString() {
            String result = "{"
                    + "id : \'" + id + "\'"
                    + ", label : \'" + label + "\'";

            if (children != null && children.getSize() != 0) {
                result += ", children : " + children.toString();
            } else {
                result += ", children : []";
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
//            int j1 = Integer.parseInt(((Node) o1).id);
//            int j2 = Integer.parseInt(((Node) o2).id);
            int j1 = ((Node) o1).level;
            int j2 = ((Node) o2).level;
            return (Integer.compare(j1, j2));
        }
    }

    public static void main(String[] args) {
        exapmle();
    }

    public static void exapmle() {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] ids = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] names = {"项目名称", "一级功能1", "一级功能2", "二级功能1", "二级功能2", "二级功能3", "二级功能4", "三级功能1", "三级功能2"};
        String[] parentIds = {"", "1", "1", "2", "2", "3", "3", "4", "4"};
        int[] levels = {0, 1, 1, 2, 2, 2, 2, 3, 3};

        for (int i = 0; i < ids.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ids[i]);
            map.put("name", names[i]);
            map.put("parentId", parentIds[i]);
            map.put("level", levels[i]);
            list.add(map);
        }
        Object treeList = MultipleTreeUtils.getTreeList(list);
        System.out.println(treeList);
    }

}

