package com.holland.util;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeUtil {

    public static class T {
        public String orgid;
        public String pid;
        public String orgName;

        public T() {
        }

        public T(String orgid, String pid, String orgName) {
            this.orgid = orgid;
            this.pid = pid;
            this.orgName = orgName;
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        final String[] strings = FileUtil.INSTANCE.readFile("C:\\Users\\xd\\Documents\\WeChat Files\\z514503337\\FileStorage\\File\\2021-06", "新建文本文档(1).txt");
        final String json = strings[0];
        final List list = new Gson().fromJson(json, List.class);
        final Object collect = list.stream().map(it -> {
            final Object orgid = ((Map) it).get("ORGID");
            final Object pid = ((Map) it).get("PID");
            final Object org_name = ((Map) it).get("ORG_NAME");
            return new T((String) orgid, (String) pid, (String) org_name);
        }).collect(Collectors.toList());
        final List<T> collect1 = (List<T>) collect;

        final List<TreeUtil.Node<T>> tree = getTree(collect1, "orgid", "pid", "510000000000");
        System.out.println((tree));
    }

    private static class TreeEntity {
        private Integer pid;
        private String name;
        private Integer id;
        private Object o;

        public TreeEntity(int id, String name, int pid, Object o) {
            this.pid = pid;
            this.name = name;
            this.id = id;
            this.o = o;
        }

        @Override
        public String toString() {
            return "TreeEntity{" +
                    "pid=" + pid +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    ", o=" + o +
                    '}';
        }
    }

    /**
     * @param list        节点集合
     * @param propertyId  节点id属性的名称
     * @param propertyPid 节点parentId属性的名称
     * @param topId       顶级节点的id
     * @param <T>         节点类型
     * @return 以多个顶级节点为起点的树形结构，至少返回空的list
     * @throws NoSuchFieldException   示意类中没有这样的属性
     * @throws IllegalAccessException 理论上不会发生
     */
    public static <T> List<Node<T>> getTree(List<T> list, String propertyId, String propertyPid, String topId) throws NoSuchFieldException, IllegalAccessException {
        final List<Node<T>> result = new ArrayList<>();
        if (null == list || list.isEmpty()) return result;
        final List<Node<T>> restOfNode = new ArrayList<>();

        final Class<?> nodeClass = list.get(0).getClass();
        for (T node : list) {
            final Field fieldId = nodeClass.getDeclaredField(propertyId);
            fieldId.setAccessible(true);
            final Field fieldPid = nodeClass.getDeclaredField(propertyPid);
            fieldPid.setAccessible(true);

            final String id = (String) fieldId.get(node);
            final String pid = (String) fieldPid.get(node);

            if (topId.equals(pid)) {
                result.add(new Node<>(id, pid, node));
            } else {
                restOfNode.add(new Node<>(id, pid, node));
            }
        }

        if (result.isEmpty()) return result;
        return result
                .stream()
                .peek(node -> node.childList = getChildNode(node, restOfNode))
                .collect(Collectors.toList());
    }

    private static <T> List<Node<T>> getChildNode(Node<T> root, List<Node<T>> allListTree) {
        return allListTree.stream()
                .filter((node) -> root.id.equals(node.pid))
                .peek((node) -> node.childList = (getChildNode(node, allListTree)))
                .collect(Collectors.toList());
    }

    public static class Node<T> {
        private final String id;
        private final String pid;
        private final T node;
        private List<Node<T>> childList;

        public Node(String id, String pid, T node) {
            this.id = id;
            this.pid = pid;
            this.node = node;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "node=" + node +
                    ", childList=" + childList +
                    '}';
        }

        public T getNode() {
            return node;
        }

        public List<Node<T>> getChildList() {
            return childList;
        }
    }
}
