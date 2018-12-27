package ru.otus.algo;

class DArray<T> {

    private Object[] _arr;

    public DArray() {
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T)_arr[index];
    }

    private void relocate(int newsize, int index) {
        Object[] tmp = new Object[newsize];

        if (_arr != null)
            for(int i=0; i < _arr.length; i++)
                if (i<index)
                    tmp[i] = _arr[i];
                else
                    tmp[i+1] = _arr[i];
        _arr = tmp;
    }

    public void add(int index, T element) {
        if (_arr == null || _arr.length <= index)
            relocate(index+1, index);
        _arr[index] = (Object)element;
    }

    public void set(int index, T element) {
        _arr[index] = (Object)element;
    }

    public int size( ) {
        return _arr.length;
    }
}
