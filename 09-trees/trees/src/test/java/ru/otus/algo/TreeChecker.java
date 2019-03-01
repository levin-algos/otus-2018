package ru.otus.algo;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiPredicate;

class TreeChecker<V> {

    private class Rule<V> {
        private final BiPredicate<AbstractBinarySearchTree.Node<V>, Comparator<? super V>> rule;
        private final Comparator<? super V> cmp;

        public Rule(BiPredicate<AbstractBinarySearchTree.Node<V>, Comparator<? super V>> rule, Comparator<? super V> cmp) {
            this.rule = rule;
            this.cmp = cmp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rule<?> rule1 = (Rule<?>) o;
            return rule.equals(rule1.rule) &&
                    cmp.equals(rule1.cmp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rule, cmp);
        }
    }

    private Map<Class<?>, List<Rule<V>>> invariants;

    TreeChecker() {
        invariants = new HashMap<>();
    }

    void addCheck(Class cl, BiPredicate<AbstractBinarySearchTree.Node<V>, Comparator<? super V>> check, Comparator<? super V> cmp) {
        if (cl == null || check == null || cmp == null)
            throw new IllegalArgumentException();

        List<Rule<V>> predicates;
        if (invariants.containsKey(cl)) {
            predicates = invariants.get(cl);
        } else {
            predicates = new ArrayList<>();
            invariants.put(cl, predicates);
        }
        Rule<V> rule = new Rule<V>(check, cmp);
        if (!predicates.contains(rule)) {
            predicates.add(rule);
        }
    }

    boolean check(AbstractBinarySearchTree tree) throws IllegalAccessException {
        for (Map.Entry<Class<?>, List<Rule<V>>> entry: invariants.entrySet()) {
            Field root = FieldUtils.getField(entry.getKey(), "root", true);
            AbstractBinarySearchTree.Node node = (AbstractBinarySearchTree.Node<V>) root.get(tree);
            for (Rule<V> rule: entry.getValue()) {
                if (!checkRecursive(node, root.getType(), rule))
                return false;
            }
        }

        return true;
    }

    private boolean checkRecursive(AbstractBinarySearchTree.Node<V> node,
                                   Class<?> nodeType,
                                   Rule<V> rule) {
        if (node == null)
            return true;

        if (!rule.rule.test(node, rule.cmp)) {
            return false;
        }

        return checkRecursive(node.left, nodeType, rule) && checkRecursive(node.right, nodeType, rule);
    }
}
