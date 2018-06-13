package application.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ListCacheUtil<I, T> {

    private Supplier<Stream<T>> streamSupplier;
    private BiPredicate<I, T> predicate;
    private Map<I, T> cache = new HashMap<>();

    public ListCacheUtil(List<T> list, BiPredicate<I, T> predicate) {
        this.streamSupplier = list::stream;
        this.predicate = predicate;
    }

    public T getItem(I input) {
        if (cache.containsKey(input))
            return cache.get(input);
        else {
            Optional<T> result = streamSupplier.get().filter(t -> predicate.test(input, t)).findFirst();
            T item = result.orElse(null);
            cache.put(input, item);
            return item;
        }
    }

    public void addItem(I input, T item) {
        cache.put(input, item);
    }

    public void removeItem(I input) {
        cache.remove(input);
    }
}
