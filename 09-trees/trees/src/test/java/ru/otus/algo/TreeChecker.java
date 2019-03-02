package ru.otus.algo;

import ru.otus.algo.common.ReflectionEntry;

import java.util.*;
import java.util.function.BiPredicate;

class TreeChecker<V> {

    private class Rule<V> {
        private final BiPredicate<ReflectionEntry, Comparator<? super V>> rule;
        private final Comparator<? super V> cmp;

        public Rule(BiPredicate<ReflectionEntry, Comparator<? super V>> rule, Comparator<? super V> cmp) {
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

    void addCheck(Class cl, BiPredicate<ReflectionEntry, Comparator<? super V>> check, Comparator<? super V> cmp) {
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

    boolean check(AbstractBinarySearchTree tree) {
        for (Map.Entry<Class<?>, List<Rule<V>>> entry: invariants.entrySet()) {
            ReflectionEntry root = new ReflectionEntry(tree);
            for (Rule<V> rule: entry.getValue()) {
                if (!checkRecursive(root.getField("root"), rule))
                return false;
            }
        }

        return true;
    }

    private boolean checkRecursive(ReflectionEntry node, Rule<V> rule) {
        if (node == null)
            return true;

        if (!rule.rule.test(node, rule.cmp)) {
            return false;
        }

        return checkRecursive(node.getField("left"), rule) && checkRecursive(node.getField("right"), rule);
    }
}
