package net.meme20200.modern;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.meme20200.shared.Obj2IntMap;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;


public class FastUtilObj2Int<K> implements Obj2IntMap<K> {
    private final Object2IntOpenHashMap<K> internalMap = new Object2IntOpenHashMap<>();

    @Override
    public void put(K object, int value) {
        internalMap.put(object, value);
    }

    @Override
    public int get(K object) {
        return internalMap.getInt(object);
    }

    @Override
    public void remove(K object) {
        internalMap.removeInt(object);
    }

    @Override
    public void forEachFast(BiConsumer<K, Integer> action) {
        internalMap.object2IntEntrySet().fastForEach(entry -> action.accept(entry.getKey(), entry.getIntValue()));
    }

    @Override
    public void removeIf(BiPredicate<K, Integer> filter) {
        internalMap.object2IntEntrySet().removeIf(entry -> filter.test(entry.getKey(), entry.getIntValue()));
    }

    @Override
    public Iterable<Entry<K>> fastIterable() {
        final Object2IntMap.FastEntrySet<K> fastEntrySet = internalMap.object2IntEntrySet();
        return () -> new Iterator<>() {
            private final Iterator<Object2IntMap.Entry<K>> fastIterator = fastEntrySet.fastIterator();

            @Override
            public boolean hasNext() {
                return fastIterator.hasNext();
            }

            @Override
            public Entry<K> next() {
                final Object2IntMap.Entry<K> next = fastIterator.next();
                return new Entry<>() {
                    @Override
                    public K getKey() {
                        return next.getKey();
                    }

                    @Override
                    public int getIntValue() {
                        return next.getIntValue();
                    }
                };
            }
        };
    }

    @Override
    public int getOrDefault(K key, int defaultValue) {
        return internalMap.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean contains(K userInput) {
        return internalMap.containsKey(userInput);
    }
}
