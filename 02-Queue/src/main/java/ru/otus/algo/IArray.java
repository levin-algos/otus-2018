package ru.otus.algo;

/**
 * Implementation of dynamic array.
 * Main idea is to keep array as several fixed capacity subarrays.
 * When size of subarray grows to some value (ex. if size() > 0.7* subarray capacity) then subarray splits
 * into two subarrays.
 * Example:
 * subarray capacity: 10
 * GROW_FACTOR: 0.7
 * IArray:
 * {1, 2, 3, 4, 5, 6}
 * After add(0, 0) IArray converts to:
 * IArray:
 * {0, 1, 2, 3}
 * {4, 5, 6}
 *
 * Remove:
 * If as a result of removing an element we have an empty subarray, it have to be deleted from dynamic array.
 *
 * @param <T>
 */

public class IArray<T> implements DynamicArray<T> {

    IArray() {
        _arr = new BArray<>(1);
        _arr.add(0, new BArray<>(initialCap));
    }

    IArray(int initialSize) {
        _arr = new BArray<>(1);
        _arr.add(0, new BArray<>(initialSize));
        initialCap = initialSize;
    }

    IArray(T... arr) {
        this();
        for (int i = 0; i < arr.length; i++) add(i, arr[i]);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        ElementPosition bin = getElementPosition(index, false);
        return _arr.get(bin.bin).get(bin.pos);
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || size() < index)
            throw new ArrayIndexOutOfBoundsException(index);

        ElementPosition pos = getElementPosition(index, true);
        BArray<T> bin = _arr.get(pos.bin);
        bin.add(pos.pos, element);
        if (resizeNeeded(bin))
            resizeBin(pos.bin);

        size++;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        ElementPosition bin = getElementPosition(index, false);
        _arr.get(bin.bin).set(bin.pos, element);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        ElementPosition pos = getElementPosition(index, false);
        BArray<T> array = _arr.get(pos.bin);
        T res = array.remove(pos.pos);
        if (array.size() == 0)
            _arr.remove(pos.bin);

        size--;
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] toArray(T[] cl) {
        BArray<T> res = new BArray<>(size());
        int count = 0;
        for (int i=0; i<_arr.size(); i++) {
            BArray<T> bin = _arr.get(i);
            for (int j=0; j<bin.size(); j++) {
                res.add(count++, bin.get(j));
            }
        }
        return res.toArray(cl);
    }

    private boolean resizeNeeded(BArray<T> bin) {
        return bin.size()/(float) initialCap > GROW_FACTOR;
    }

    private void resizeBin(int x) {
        BArray<T> curBin = _arr.get(x);
        int splitPoint = curBin.size() / 2;
        _arr.add(x+1, curBin.split(splitPoint));
    }

    private ElementPosition getElementPosition(int index, boolean adding) {
        int sum = 0;
        int binNum = 0;
        for (int i=0; i < _arr.size(); i++) {
            BArray<T> bin = _arr.get(i);
            if (adding ? index - sum <= bin.size()
                    : index - sum < bin.size()) {
                break;
            }
            sum +=bin.size();
            binNum++;
        }
        return ElementPosition.of(binNum, Math.max(0, index - sum));
    }

    private final BArray<BArray<T>> _arr;
    private static int initialCap = 100;
    private final static float GROW_FACTOR = 0.75f;
    private int size;

    private static class ElementPosition {
        private final int bin, pos;

        private ElementPosition(int bin, int pos) {
            this.bin = bin; this.pos = pos;
        }

        static ElementPosition of(int x, int y) { return new ElementPosition(x, y);}

        @Override
        public String toString() {
            return "ElementPosition{" +
                    "bin=" + bin +
                    ", pos=" + pos +
                    '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < _arr.size(); i++) {
            sb.append(_arr.get(i)).append(System.lineSeparator());
        }
        return sb.toString();
    }
}