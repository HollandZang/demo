package com.holland.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<TreeEntity> listTree = new ArrayList<>();
        listTree.add(new TreeEntity(1, "爷", 0, null));
        listTree.add(new TreeEntity(2, "二爷", 0, null));
        listTree.add(new TreeEntity(3, "爷-爸", 1, null));
        listTree.add(new TreeEntity(4, "爷-叔", 1, null));
        listTree.add(new TreeEntity(5, "二爷-爸", 2, null));
        listTree.add(new TreeEntity(6, "爷-爸-大儿子", 3, null));
        listTree.add(new TreeEntity(7, "爷-爸-小儿子", 3, null));
        listTree.add(new TreeEntity(10, "爷-叔-大儿子", 4, null));
        listTree.add(new TreeEntity(11, "爷-叔-小儿子", 4, null));
        listTree.add(new TreeEntity(8, "二爷-爸-大儿子", 5, null));
        listTree.add(new TreeEntity(9, "二爷-爸-小儿子", 5, null));

        final List<TreeUtil.Node<TreeEntity>> tree = getTree(listTree, "id", "pid", 0);
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
    public static <T> List<Node<T>> getTree(List<T> list, String propertyId, String propertyPid, Integer topId) throws NoSuchFieldException, IllegalAccessException {
        final List<Node<T>> result = new ArrayList<>();
        if (null == list || list.isEmpty()) return result;
        final List<Node<T>> restOfNode = new ArrayList<>();

        final Class<?> nodeClass = list.get(0).getClass();
        for (T node : list) {
            final Field fieldId = nodeClass.getDeclaredField(propertyId);
            fieldId.setAccessible(true);
            final Field fieldPid = nodeClass.getDeclaredField(propertyPid);
            fieldPid.setAccessible(true);

            final int id = (Integer) fieldId.get(node);
            final int pid = (Integer) fieldPid.get(node);

            if (topId.equals(pid)) {
                result.add(new Node<>(id, pid, node));
            } else {
                restOfNode.add(new Node<>(id, pid, node));
            }
        }

        if (result.isEmpty()) return result;
        return result.stream().peek(node -> node.childList = getChildNode(node, restOfNode)).collect(Collectors.toList());
    }

    private static <T> List<Node<T>> getChildNode(Node<T> root, List<Node<T>> allListTree) {
        return allListTree.stream()
                .filter((node) -> root.id.equals(node.pid))
                .peek((node) -> node.childList = (getChildNode(node, allListTree)))
                .collect(Collectors.toList());
    }

    public static class Node<T> {
        private final Integer id;
        private final Integer pid;
        private final T node;
        private List<Node<T>> childList;

        public Node(int id, int pid, T node) {
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
