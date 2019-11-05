package com.yfny.utilscommon.util;

import com.alibaba.fastjson.JSONObject;
import com.yfny.utilscommon.basemvc.common.BaseTree;

import java.util.*;

/**
 * 数据列表转树结构工具
 * Created by jisongZhou on 2017/12/28.
 **/

public class MultipleTreeUtils {

    public static String getTreeList(List dataList) {
        return getTreeList(dataList, BaseTree.NODE_NUMBER);
    }

    public static String getTreeList(List dataList, String type) {
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
                if (BaseTree.NODE_STRING.equals(type)) {
                    node.level = (int) dataRecord.get("level");
                } else if (BaseTree.NODE_NUMBER.equals(type)) {
                    node.level = AsciiUtils.SumStrAscii(node.id);
                }

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
        public String toString() {
            String result = "{"
                    + "\"id\" : \"" + id + "\""
                    + ", \"label\" : \"" + label + "\"";

            if (children != null && children.getSize() != 0) {
                result += ", \"children\" : " + children.toString();
            } else {
                result += ", \"children\" : []";
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
        //example();
        String tree = "{\"id\" : \"PROPERTY_PARAM\", \"label\" : \"特性参数\", \"children\" : [{\"id\" : \"26381891678503001\", \"label\" : \"CHAR\", \"children\" : [{\"id\" : \"26381891678503010\", \"label\" : \"度\", \"children\" : []},{\"id\" : \"26381891678503020\", \"label\" : \"瓦特\", \"children\" : []},{\"id\" : \"26381891678503011\", \"label\" : \"平方米\", \"children\" : []},{\"id\" : \"26381891678503030\", \"label\" : \"千米\", \"children\" : []},{\"id\" : \"26381891678503021\", \"label\" : \"伏特安培\", \"children\" : []},{\"id\" : \"26381891678503012\", \"label\" : \"小时\", \"children\" : []},{\"id\" : \"26381891678503040\", \"label\" : \"皮法拉\", \"children\" : []},{\"id\" : \"26381891678503031\", \"label\" : \"千瓦特\", \"children\" : []},{\"id\" : \"26381891678503022\", \"label\" : \"毫米每千伏\", \"children\" : []},{\"id\" : \"26381891678503013\", \"label\" : \"米每秒\", \"children\" : []},{\"id\" : \"26381891678503004\", \"label\" : \"其他\", \"children\" : []},{\"id\" : \"26381891678503041\", \"label\" : \"毫亨利\", \"children\" : []},{\"id\" : \"26381891678503032\", \"label\" : \"兆瓦特\", \"children\" : []},{\"id\" : \"26381891678503023\", \"label\" : \"安时\", \"children\" : []},{\"id\" : \"26381891678503014\", \"label\" : \"节\", \"children\" : []},{\"id\" : \"26381891678503005\", \"label\" : \"米\", \"children\" : []},{\"id\" : \"26381891678503050\", \"label\" : \"纳米\", \"children\" : []},{\"id\" : \"26381891678503042\", \"label\" : \"千赫兹\", \"children\" : []},{\"id\" : \"26381891678503033\", \"label\" : \"千乏\", \"children\" : []},{\"id\" : \"26381891678503024\", \"label\" : \"台\", \"children\" : []},{\"id\" : \"26381891678503015\", \"label\" : \"赫兹\", \"children\" : []},{\"id\" : \"26381891678503006\", \"label\" : \"千克\", \"children\" : []},{\"id\" : \"26381891678503043\", \"label\" : \"次每时\", \"children\" : []},{\"id\" : \"26381891678503034\", \"label\" : \"兆乏\", \"children\" : []},{\"id\" : \"26381891678503025\", \"label\" : \"个\", \"children\" : []},{\"id\" : \"26381891678503016\", \"label\" : \"分贝\", \"children\" : []},{\"id\" : \"26381891678503007\", \"label\" : \"秒\", \"children\" : []},{\"id\" : \"26381891678503044\", \"label\" : \"毫米\", \"children\" : []},{\"id\" : \"26381891678503035\", \"label\" : \"千安培\", \"children\" : []},{\"id\" : \"26381891678503026\", \"label\" : \"路\", \"children\" : []},{\"id\" : \"26381891678503017\", \"label\" : \"摄氏度\", \"children\" : []},{\"id\" : \"26381891678503008\", \"label\" : \"安培\", \"children\" : []},{\"id\" : \"26381891678503045\", \"label\" : \"兆比特每秒\", \"children\" : []},{\"id\" : \"26381891678503036\", \"label\" : \"千伏特\", \"children\" : []},{\"id\" : \"26381891678503027\", \"label\" : \"次\", \"children\" : []},{\"id\" : \"26381891678503018\", \"label\" : \"伏特\", \"children\" : []},{\"id\" : \"26381891678503009\", \"label\" : \"开尔文\", \"children\" : []},{\"id\" : \"26381891678503046\", \"label\" : \"兆比特每秒\", \"children\" : []},{\"id\" : \"26381891678503037\", \"label\" : \"毫秒\", \"children\" : []},{\"id\" : \"26381891678503028\", \"label\" : \"分贝毫瓦\", \"children\" : []},{\"id\" : \"26381891678503019\", \"label\" : \"欧姆\", \"children\" : []},{\"id\" : \"26381891678503047\", \"label\" : \"芯\", \"children\" : []},{\"id\" : \"26381891678503038\", \"label\" : \"兆帕斯卡\", \"children\" : []},{\"id\" : \"26381891678503029\", \"label\" : \"厘米\", \"children\" : []},{\"id\" : \"26381891678503048\", \"label\" : \"兆赫兹\", \"children\" : []},{\"id\" : \"26381891678503039\", \"label\" : \"微法拉\", \"children\" : []},{\"id\" : \"26381891678503049\", \"label\" : \"百分比\", \"children\" : []}]},{\"id\" : \"26381891678503002\", \"label\" : \"DATA_TIME\", \"children\" : [{\"id\" : \"26381891678503119\", \"label\" : \"其他\", \"children\" : []}]},{\"id\" : \"26381891678503003\", \"label\" : \"NUMBER\", \"children\" : [{\"id\" : \"26381891678503100\", \"label\" : \"微法拉\", \"children\" : []},{\"id\" : \"26381891678503110\", \"label\" : \"兆比特每秒\", \"children\" : []},{\"id\" : \"26381891678503101\", \"label\" : \"皮法拉\", \"children\" : []},{\"id\" : \"26381891678503111\", \"label\" : \"K比特每秒\", \"children\" : []},{\"id\" : \"26381891678503102\", \"label\" : \"毫亨利\", \"children\" : []},{\"id\" : \"26381891678503112\", \"label\" : \"兆比特每秒\", \"children\" : []},{\"id\" : \"26381891678503103\", \"label\" : \"千赫兹\", \"children\" : []},{\"id\" : \"26381891678503113\", \"label\" : \"欧姆每千米\", \"children\" : []},{\"id\" : \"26381891678503104\", \"label\" : \"微法拉每千米\", \"children\" : []},{\"id\" : \"26381891678503114\", \"label\" : \"吉帕斯卡\", \"children\" : []},{\"id\" : \"26381891678503060\", \"label\" : \"升\", \"children\" : []},{\"id\" : \"26381891678503105\", \"label\" : \"转每分\", \"children\" : []},{\"id\" : \"26381891678503051\", \"label\" : \"其他\", \"children\" : []},{\"id\" : \"26381891678503070\", \"label\" : \"瓦特\", \"children\" : []},{\"id\" : \"26381891678503115\", \"label\" : \"芯\", \"children\" : []},{\"id\" : \"26381891678503061\", \"label\" : \"节\", \"children\" : []},{\"id\" : \"26381891678503106\", \"label\" : \"吉比特\", \"children\" : []},{\"id\" : \"26381891678503052\", \"label\" : \"米\", \"children\" : []},{\"id\" : \"26381891678503080\", \"label\" : \"个\", \"children\" : []},{\"id\" : \"26381891678503071\", \"label\" : \"伏特安培\", \"children\" : []},{\"id\" : \"26381891678503116\", \"label\" : \"兆赫兹\", \"children\" : []},{\"id\" : \"26381891678503062\", \"label\" : \"赫兹\", \"children\" : []},{\"id\" : \"26381891678503107\", \"label\" : \"太比特\", \"children\" : []},{\"id\" : \"26381891678503053\", \"label\" : \"千克\", \"children\" : []},{\"id\" : \"26381891678503090\", \"label\" : \"千乏\", \"children\" : []},{\"id\" : \"26381891678503081\", \"label\" : \"块\", \"children\" : []},{\"id\" : \"26381891678503072\", \"label\" : \"毫米每千伏\", \"children\" : []},{\"id\" : \"26381891678503117\", \"label\" : \"百分比\", \"children\" : []},{\"id\" : \"26381891678503108\", \"label\" : \"毫米\", \"children\" : []},{\"id\" : \"26381891678503063\", \"label\" : \"分贝\", \"children\" : []},{\"id\" : \"26381891678503054\", \"label\" : \"秒\", \"children\" : []},{\"id\" : \"26381891678503091\", \"label\" : \"兆乏\", \"children\" : []},{\"id\" : \"26381891678503082\", \"label\" : \"路\", \"children\" : []},{\"id\" : \"26381891678503073\", \"label\" : \"安时\", \"children\" : []},{\"id\" : \"26381891678503118\", \"label\" : \"门\", \"children\" : []},{\"id\" : \"26381891678503109\", \"label\" : \"百万分率\", \"children\" : []},{\"id\" : \"26381891678503064\", \"label\" : \"吨\", \"children\" : []},{\"id\" : \"26381891678503055\", \"label\" : \"安培\", \"children\" : []},{\"id\" : \"26381891678503092\", \"label\" : \"毫安培\", \"children\" : []},{\"id\" : \"26381891678503083\", \"label\" : \"只\", \"children\" : []},{\"id\" : \"26381891678503074\", \"label\" : \"片\", \"children\" : []},{\"id\" : \"26381891678503065\", \"label\" : \"牛顿\", \"children\" : []},{\"id\" : \"26381891678503056\", \"label\" : \"开尔文\", \"children\" : []},{\"id\" : \"26381891678503093\", \"label\" : \"千安培\", \"children\" : []},{\"id\" : \"26381891678503084\", \"label\" : \"次\", \"children\" : []},{\"id\" : \"26381891678503075\", \"label\" : \"串\", \"children\" : []},{\"id\" : \"26381891678503066\", \"label\" : \"立方米每秒\", \"children\" : []},{\"id\" : \"26381891678503057\", \"label\" : \"度\", \"children\" : []},{\"id\" : \"26381891678503094\", \"label\" : \"千伏特\", \"children\" : []},{\"id\" : \"26381891678503085\", \"label\" : \"分贝毫瓦\", \"children\" : []},{\"id\" : \"26381891678503076\", \"label\" : \"面\", \"children\" : []},{\"id\" : \"26381891678503067\", \"label\" : \"摄氏度\", \"children\" : []},{\"id\" : \"26381891678503058\", \"label\" : \"平方米\", \"children\" : []},{\"id\" : \"26381891678503095\", \"label\" : \"毫秒\", \"children\" : []},{\"id\" : \"26381891678503086\", \"label\" : \"千米\", \"children\" : []},{\"id\" : \"26381891678503077\", \"label\" : \"台\", \"children\" : []},{\"id\" : \"26381891678503068\", \"label\" : \"伏特\", \"children\" : []},{\"id\" : \"26381891678503059\", \"label\" : \"立方米\", \"children\" : []},{\"id\" : \"26381891678503096\", \"label\" : \"千帕斯卡\", \"children\" : []},{\"id\" : \"26381891678503087\", \"label\" : \"克\", \"children\" : []},{\"id\" : \"26381891678503078\", \"label\" : \"组\", \"children\" : []},{\"id\" : \"26381891678503069\", \"label\" : \"欧姆\", \"children\" : []},{\"id\" : \"26381891678503097\", \"label\" : \"兆帕斯卡\", \"children\" : []},{\"id\" : \"26381891678503088\", \"label\" : \"千瓦特\", \"children\" : []},{\"id\" : \"26381891678503079\", \"label\" : \"条\", \"children\" : []},{\"id\" : \"26381891678503098\", \"label\" : \"微欧姆\", \"children\" : []},{\"id\" : \"26381891678503089\", \"label\" : \"兆瓦特\", \"children\" : []},{\"id\" : \"26381891678503099\", \"label\" : \"千欧姆\", \"children\" : []}]}]}";
        System.out.println(JSONObject.parse(tree));
    }

    public static void example() {
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
            map.put("level", levels);
            list.add(map);
        }
        String treeJson = MultipleTreeUtils.getTreeList(list);
        System.out.println(treeJson);
    }

}

