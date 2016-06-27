package com.vladsch.flexmark.internal.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OrderedMap<K, V> implements Map<K, V> {
    final private OrderedSet<K> orderedSet;
    final private ArrayList<V> valueList;

    public OrderedMap() {
        this(0);
    }

    public OrderedMap(int capacity) {
        this.valueList = new ArrayList<V>(capacity);
        this.orderedSet = new OrderedSet<K>(capacity, new OrderedSetHost<K>() {
            @Override
            public void adding(int index, K k, Object v) {
                OrderedMap.this.adding(index, k, v);
            }

            @Override
            public Object removing(int index, K k) {
                return OrderedMap.this.removing(index, k);
            }

            @Override
            public void clearing() {
                OrderedMap.this.clearing();
            }
        });
    }

    void adding(int index, K k, Object v) {
        if (v == null) throw new IllegalArgumentException();
        valueList.add((V) v);
    }

    Object removing(int index, K k) {
        if (index == valueList.size() - 1) {
            return valueList.remove(index);
        }
        return valueList.get(index);
    }

    void clearing() {
        valueList.clear();
    }

    @Override
    public int size() {
        return orderedSet.size();
    }

    @Override
    public boolean isEmpty() {
        return orderedSet.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return orderedSet.contains(o);
    }

    @Override
    public boolean containsValue(Object o) {
        int index = valueList.indexOf(o);
        return orderedSet.isValidIndex(index);
    }

    @Override
    public V get(Object o) {
        int index = orderedSet.indexOf(o);
        return index == -1 ? null : valueList.get(index);
    }

    @Override
    public V put(K k, V v) {
        int index = orderedSet.indexOf(k);
        if (index == -1) {
            orderedSet.add(k, v);
            return null;
        }

        V old = valueList.get(index);
        valueList.set(index, v);
        return old;
    }

    @Override
    public V remove(Object o) {
        return (V) orderedSet.removeHosted(o);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        orderedSet.clear();
    }

    @Override
    public OrderedSet<K> keySet() {
        return orderedSet;
    }

    @Override
    public Collection<V> values() {
        if (!orderedSet.isSparse()) {
            return valueList;
        }

        ArrayList<V> values = new ArrayList<V>(orderedSet.size());
        SparseIterator<Integer> iterator = orderedSet.indexIterator();
        while (iterator.hasNext()) {
            values.add(valueList.get(iterator.getIndex()));
        }
        return values;
    }

    public K getKey(int index) {
        if (!orderedSet.isValidIndex(index)) return null;
        return orderedSet.getValueList().get(index);
    }

    public V getValue(int index) {
        if (!orderedSet.isValidIndex(index)) return null;
        return valueList.get(index);
    }

    public static class Entry<T, E> implements Map.Entry<T, E> {
        final private OrderedMap<T, E> orderedMap;
        final private int index;
        final private T key;
        private E value;

        public Entry(OrderedMap<T, E> orderedMap, int index) {
            assert orderedMap.keySet().isValidIndex(index);

            this.orderedMap = orderedMap;
            this.index = index;
            this.key = orderedMap.getKey(index);
            this.value = orderedMap.getValue(index);
        }

        @Override
        public T getKey() {
            return key;
        }

        @Override
        public E getValue() {
            return value;
        }

        @Override
        public E setValue(E value) {
            E old = value;
            orderedMap.valueList.set(index, value);
            this.value = value;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?, ?> entry = (Entry<?, ?>) o;

            if (!key.equals(entry.key)) return false;
            if (!value.equals(entry.value)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    @Override
    public OrderedSet<Map.Entry<K, V>> entrySet() {
        final boolean[] hostActivated = new boolean[] { false };
        OrderedSet<Map.Entry<K, V>> values = new OrderedSet<>(orderedSet.size(), new OrderedSetHost<Map.Entry<K, V>>() {
            @Override
            public void adding(int index, Map.Entry<K, V> entry, Object v) {
                assert v == null;
                if (hostActivated[0]) {
                    OrderedMap.this.orderedSet.add(entry.getKey(), entry.getValue());
                }
            }

            @Override
            public Object removing(int index, Map.Entry<K, V> entry) {
                OrderedMap.this.orderedSet.remove(index);
                return entry;
            }

            @Override
            public void clearing() {
                OrderedMap.this.orderedSet.clear();
            }
        });

        SparseIterator<Integer> iterator = orderedSet.indexIterator();
        while (iterator.hasNext()) {
            iterator.next();
            values.add(new Entry<K, V>(this, iterator.getIndex()));
        }

        hostActivated[0] = true;
        return values;
    }

    public static abstract class AbstractIterator<T, E, X> extends OrderedSet.AbstractIndexedIterator<T, X> {
        protected final OrderedMap<T, E> orderedMap;

        public AbstractIterator(OrderedMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public AbstractIterator(OrderedMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap.orderedSet, reversed);
            this.orderedMap = orderedMap;
        }
    }

    public static class ValueIterator<T, E> extends AbstractIterator<T, E, E> {
        public ValueIterator(OrderedMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public ValueIterator(OrderedMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<E> reversed() {
            return new ValueIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected E getValueAt(int index) {
            return orderedMap.getValue(index);
        }
    }

    public static class KeyIterator<T, E> extends AbstractIterator<T, E, T> {
        public KeyIterator(OrderedMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public KeyIterator(OrderedMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<T> reversed() {
            return new KeyIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected T getValueAt(int index) {
            return orderedMap.getKey(index);
        }
    }

    public static class EntryIterator<T, E> extends AbstractIterator<T, E, Entry<T,E>> {
        public EntryIterator(OrderedMap<T, E> orderedMap) {
            this(orderedMap, false);
        }

        public EntryIterator(OrderedMap<T, E> orderedMap, boolean reversed) {
            super(orderedMap, reversed);
        }

        @Override
        public SparseIterator<Entry<T,E>> reversed() {
            return new EntryIterator<T, E>(orderedMap, !isReversed());
        }

        @Override
        protected Entry<T,E> getValueAt(int index) {
            return new Entry<T, E>(orderedMap, index);
        }
    }

    public ReversibleIterator<V> valueIterator() {
        return new ValueIterator<K, V>(this);
    }

    public ReversibleIterator<V> reversedValueIterator() {
        return new ValueIterator<K, V>(this, true);
    }

    public ReversibleIterator<K> keyIterator() {
        return new KeyIterator<K, V>(this);
    }

    public ReversibleIterator<K> reversedKeyIterator() {
        return new KeyIterator<K, V>(this, true);
    }

    public ReversibleIterator<Entry<K,V>> entryIterator() {
        return new EntryIterator<K, V>(this);
    }

    public ReversibleIterator<Entry<K,V>> reversedEntryIterator() {
        return new EntryIterator<K, V>(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedMap<?, ?> set = (OrderedMap<?, ?>) o;

        if (size() != set.size()) return false;
        if (!entrySet().equals(set.entrySet())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = orderedSet.hashCode();
        result = 31 * result + valueList.hashCode();
        return result;
    }
}
