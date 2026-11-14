package net.meme20200.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;


/**
 An interface for (Object, int) map. It is used in DupePlus to fall back to the normal Java HashMap if Fastutil isn't installed.
 @param <K> The Object
 */
public interface Obj2IntMap<K> {
    void put(K object, int value);
    int get(K object);

    void remove(K object);

    void forEachFast(BiConsumer<K, Integer> action);
    void removeIf(BiPredicate<K, Integer> filter);

    Iterable<Entry<K>> fastIterable();

    int getOrDefault(K key, int defaultValue);

    boolean contains(K userInput);

    interface Entry<K> {
        K getKey();
        int getIntValue();
    }

    class JavaImplementation<K> implements Obj2IntMap<K> {
        private final Map<K, Integer> internalMap = new HashMap<>();

        @Override
        public void put(K object, int value) {
            internalMap.put(object, value);
        }

        @Override
        public int get(K object) {
            return internalMap.get(object);
        }

        @Override
        public void remove(K object) {
            internalMap.remove(object);
        }

        @Override
        public void forEachFast(BiConsumer<K, Integer> action) {
            internalMap.forEach(action);
        }

        @Override
        public void removeIf(BiPredicate<K, Integer> filter) {
            internalMap.entrySet().removeIf(e -> filter.test(e.getKey(), e.getValue()));
        }

        @Override
        public Iterable<Entry<K>> fastIterable() {
            return () -> internalMap.entrySet().stream().map(e -> (Entry<K>) new Entry<K>() {
                @Override
                public K getKey() {
                    return e.getKey();
                }

                @Override
                public int getIntValue() {
                    return e.getValue();
                }
            }).iterator();
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
}
