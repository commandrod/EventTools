package me.commandrod.eventtools.utils;

import me.commandrod.eventtools.Main;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.intellij.lang.annotations.RegExp;

public class Replacement {

    private final TextReplacementConfig.Builder replacementBuilder;
    private final MiniMessage miniMessage;

    public Replacement(TextReplacementConfig.Builder replacementBuilder, MiniMessage miniMessage) {
        this.replacementBuilder = replacementBuilder;
        this.miniMessage = miniMessage;
    }

    public static Replacement builder() {
        return new Replacement(TextReplacementConfig.builder(), Main.getMiniMessage());
    }

    public Replacement replace(@RegExp String placeholder, String value) {
        this.replacementBuilder.match(placeholder).replacement(miniMessage.deserialize(value));
        return this;
    }

    public Replacement replace(@RegExp String placeholder, ComponentLike value) {
        this.replacementBuilder.match(placeholder).replacement(value);
        return this;
    }

    public TextReplacementConfig build() {
        return this.replacementBuilder.build();
    }
}
