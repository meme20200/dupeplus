package net.meme20200.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 An interface for (Object, long) map. It is used in DupePlus to fall back to the normal Java HashMap if Fastutil isn't installed.
 @param <K> The Object
 */
public interface Obj2LongMap<K> {
    void put(K object, long value);
    long get(K object);

    long remove(K object);

    void forEachFast(BiConsumer<K, Long> action);

    void defaultReturnValue(long value);
    void removeIf(BiPredicate<K, Long> filter);

    boolean isEmpty();

    class JavaImplementation<K> implements Obj2LongMap<K> {
        private final Map<K, Long> internalMap = new HashMap<>();
        private long defaultValue = 0L;

        @Override
        public void put(K object, long value) {
            internalMap.put(object, value);
        }

        @Override
        public long get(K object) {
            return internalMap.getOrDefault(object, defaultValue);
        }

        @Override
        public long remove(K object) {
            return internalMap.remove(object);
        }

        @Override
        public void forEachFast(BiConsumer<K, Long> action) {
            internalMap.forEach(action);
        }

        @Override
        public void defaultReturnValue(long value) {
            this.defaultValue = value;
        }

        @Override
        public void removeIf(BiPredicate<K, Long> filter) {
            internalMap.entrySet().removeIf(entry -> filter.test(entry.getKey(), entry.getValue()));
        }

        @Override
        public boolean isEmpty() {
            return internalMap.isEmpty();
        }
    }
}
