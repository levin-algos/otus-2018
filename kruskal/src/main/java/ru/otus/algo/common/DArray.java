package ru.otus.algo.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ForkJoinTask;

public class DArray<T> implements DynamicArray<T> {

    private static final int INSERTION_BORDER = 120;
    private static final int FORK_BORDER = 1000;
    private static final int ININITAL_CAPACITY = 100;
    private Object[] _arr;
    private int size;

    public DArray() {}

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (_arr == null || index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException(index);

        return (T) _arr[index];
    }

    private void relocate(int index) {
        if (_arr == null) {
            _arr =  new Object[newArraySize(size())];
            return;
        }

        if (size() >= _arr.length) {
            Object[] tmp = new Object[newArraySize(size())];
            System.arraycopy(_arr, 0, tmp, 0, index);
            System.arraycopy(_arr, index, tmp, index + 1, size() - index);
            _arr = tmp;
        } else {
            System.arraycopy(_arr, index, _arr, index + 1, size() - index);
        }
    }

    public void add(int index, T element) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException(index);

        relocate(index);
        _arr[index] = element;
        size++;
    }

    @Override
    public void add(T element) {
        this.add(size, element);
    }

    int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(_arr, size, a.getClass());

        System.arraycopy(_arr, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;

        return a;
    }

    private int newArraySize(int oldSize) {
        return oldSize == 0? ININITAL_CAPACITY : oldSize * 2 ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (size() >0) {
            for (int i = 0; i < size() - 1; i++) {
                sb.append(_arr[i]).append(",");
            }
            sb.append(_arr[size() - 1]);
        }
        return sb.append(']').toString();
    }


    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);

        T[] res = (T[])Arrays.copyOf(_arr, _arr.length);
        mergesort((T[])_arr, 0, size, res, comparator);
    }

    private void mergesort(T[] ints, int from, int to, T[] res, Comparator<? super T> cmp) {
        int len = to - from;
        if (len > INSERTION_BORDER) {
            int middle = (from + to) / 2;

            if (len < FORK_BORDER) {
                mergesort(ints, from, middle, res, cmp);
                mergesort(ints, middle, to, res, cmp);
            } else {
                ForkJoinTask<?> adapt = ForkJoinTask.adapt(() -> mergesort(ints, from, middle, res, cmp));
                adapt.fork();
                mergesort(ints, middle, to, res, cmp);
                adapt.join();
            }

            merge(ints, from, middle, to, res, cmp);
        } else {
            for (int i = from + 1; i < to; i++) {
                int j = i;
                T tmp = ints[j];
                while (j > from &&  cmp.compare(ints[j - 1], tmp) > 0) {
                    ints[j] = ints[--j];
                }
                ints[j] = tmp;
            }
        }
    }

    private void merge(T[] ints, int begin, int middle, int end, T[] res, Comparator<? super T> cmp) {
        int fst = begin, snd = middle;

        for (int j = begin; j < end; j++) {
            if (fst < middle && (snd >= end || cmp.compare(ints[fst], ints[snd])< 0)) {
                res[j] = ints[fst++];
            } else {
                res[j] = ints[snd++];
            }
        }
        System.arraycopy(res, begin, ints, begin, end - begin);
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int cur;
            @Override
            public boolean hasNext() {
                return cur < size;
            }

            @Override
            public T next() {
                //noinspection unchecked
                return (T)_arr[cur++];
            }
        };
    }
}