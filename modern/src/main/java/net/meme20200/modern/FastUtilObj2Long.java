package net.meme20200.modern;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.meme20200.shared.Obj2LongMap;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class FastUtilObj2Long<K> implements Obj2LongMap<K> {
    private final Object2LongOpenHashMap<K> internalMap = new Object2LongOpenHashMap<>();

    @Override
    public void put(K object, long value) {
        internalMap.put(object, value);
    }

    @Override
    public long get(K object) {
        return internalMap.getLong(object);
    }

    @Override
    public long remove(K object) {
        return internalMap.removeLong(object);
    }

    @Override
    public void forEachFast(BiConsumer<K, Long> action) {
        internalMap.object2LongEntrySet().fastForEach(entry -> action.accept(entry.getKey(), entry.getLongValue()));
    }

    @Override
    public void defaultReturnValue(long value) {
        internalMap.defaultReturnValue(value);
    }

    @Override
    public void removeIf(BiPredicate<K, Long> filter) {
        internalMap.object2LongEntrySet().removeIf(entry -> filter.test(entry.getKey(), entry.getLongValue()));
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }
}
