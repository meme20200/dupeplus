package net.meme20200.dupeplus.utilities.types;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Translation extends MiniMessageTranslator {
    private final Map<Locale, Map<String, String>> translationStore = new HashMap<>();

    public Translation() {
        // By default, the standard MiniMessage instance will be used.
        // You can specify a custom one in the super constructor.
        super(MiniMessage.miniMessage());
    }

    /**
     * Returns a raw MiniMessage string for the given key.
     *
     * <p>If no string is found for the given key, returning {@code null} will use the
     * {@link TranslatableComponent#fallback() translatable component's fallback} (or the
     * key itself).</p>
     *
     * @param key    the key
     * @param locale the locale
     * @return the resulting MiniMessage string
     * @since 4.20.0
     */
    @Override
    @Nullable
    public String getMiniMessageString(@NotNull String key, @NotNull Locale locale) {
        Map<String, String> keys = translationStore.get(locale);
        if (keys != null && keys.containsKey(key)) return keys.get(key);
        Map<String, String> fallbackKeys =
                (translationStore.get(Locale.US) != null) ? translationStore.get(Locale.US) : translationStore.entrySet().iterator().next().getValue();
        if (fallbackKeys != null) return fallbackKeys.get(key);
        return null;
    }

    public void register(String key, Locale locale, String message) {
        translationStore.computeIfAbsent(locale, k -> new HashMap<>()).put(key, message);
    }



    /**
     * A key identifying this translation source.
     *
     * <p>Intended to be used for display to users.</p>
     *
     * @return an identifier for this translation source
     * @since 4.0.0
     */
    @Override
    public @NotNull Key name() {
        return Key.key("dupeplus", "messages");
    }
}
