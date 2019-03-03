package ru.otus.algo;

import ru.otus.algo.common.ReflectionEntry;

import java.util.*;
import java.util.function.BiPredicate;

class TreeChecker<V> {

    enum Invocation {
        EACH_NODE,
        ROOT
    }

    private class Rule<R> {
        private final BiPredicate<ReflectionEntry, Comparator<? super R>> rule;
        private final Comparator<? super R> cmp;

        Rule(BiPredicate<ReflectionEntry, Comparator<? super R>> rule, Comparator<? super R> cmp) {
            this.rule = rule;
            this.cmp = cmp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            @SuppressWarnings("unchecked")
            Rule<?> rule1 = (Rule<?>) o;
            return rule.equals(rule1.rule) &&
                    cmp.equals(rule1.cmp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rule, cmp);
        }
    }

    private final Map<Invocation, List<Rule<V>>> invariants;

    TreeChecker() {
        invariants = new HashMap<>();
    }

    void addCheck(BiPredicate<ReflectionEntry, Comparator<? super V>> check, Comparator<? super V> cmp, Invocation inv) {
        if (check == null || cmp == null)
            throw new IllegalArgumentException();

        List<Rule<V>> rules;
        if (invariants.containsKey(inv))
            rules = invariants.get(inv);
        else {
            rules = new ArrayList<>();
            invariants.put(inv, rules);
        }

        Rule<V> rule = new Rule<>(check, cmp);
        if (!rules.contains(rule)) {
            rules.add(rule);
        }
    }

    boolean check(AbstractBinarySearchTree tree) {
        if (!checkRoot(tree))
            return false;

        return checkEachNode(tree);

    }

    private boolean checkRoot(AbstractBinarySearchTree tree) {
        List<Rule<V>> eachNode = invariants.get(Invocation.ROOT);
        if (eachNode != null) {
            for (Rule<V> rule : eachNode) {
                ReflectionEntry root = new ReflectionEntry(tree);
                if (!rule.rule.test(root.getField("root"), rule.cmp))
                    return false;
            }
        }
        return true;
    }

    private boolean checkEachNode(AbstractBinarySearchTree tree) {
        List<Rule<V>> eachNode = invariants.get(Invocation.EACH_NODE);
        if (eachNode != null) {
            ReflectionEntry root = new ReflectionEntry(tree);
            return checkRecursive(root.getField("root"), eachNode);
        }
        return true;
    }

    private boolean checkRecursive(ReflectionEntry node, List<Rule<V>> rules) {
        if (node == null)
            return true;

        for (Rule<V> rule : rules) {
            if (!rule.rule.test(node, rule.cmp)) {
                return false;
            }
        }

        return checkRecursive(node.getField("left"), rules) && checkRecursive(node.getField("right"), rules);
    }
}
