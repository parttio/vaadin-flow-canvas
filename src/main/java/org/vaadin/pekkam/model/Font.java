package org.vaadin.pekkam.model;

public class Font {
    private final Style style;
    private final Variant variant;
    private final Weight weight;
    private final int size;
    private final Family family;

    Font(FontBuilder builder) {
        this.style = builder.style;
        this.variant = builder.variant;
        this.weight = builder.weight;
        this.size = builder.size;
        this.family = builder.family;
    }

    @Override
    public String toString() {
        return "%s %s %s %dpx %s".formatted(
                style,
                variant,
                weight,
                size,
                family
        );
    }

    public static FontBuilder builder() {
        return new FontBuilder();
    }

    public static class FontBuilder {
        private Style style = Style.NORMAL;
        private Variant variant = Variant.NORMAL;
        private Weight weight = Weight.NORMAL;
        private int size = 12;
        private Family family;

        public FontBuilder style(Style style) {
            this.style = style;
            return this;
        }

        public FontBuilder variant(Variant variant) {
            this.variant = variant;
            return this;
        }

        public FontBuilder weight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public FontBuilder size(int size) {
            this.size = size;
            return this;
        }

        public FontBuilder family(Family family) {
            this.family = family;
            return this;
        }

        public Font build() {
            return new Font(this);
        }
    }

    public enum Style {
        NORMAL("normal"),
        ITALIC("italic"),
        OBLIQUE("oblique");

        private final String value;

        Style(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Variant {
        NORMAL("normal"),
        SMALL_CAPS("small-caps");

        private final String value;

        Variant(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Weight {
        NORMAL("normal"),
        BOLD("bold"),
        BOLDER("bolder"),
        LIGHTER("lighter"),
        W_100("100"),
        W_200("200"),
        W_300("300"),
        W_400("400"),
        W_500("500"),
        W_600("600"),
        W_700("700"),
        W_800("800"),
        W_900("900");

        private final String value;

        Weight(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Family {
        SERIF("serif"),
        SANS_SERIF("sans-serif"),
        MONOSPACE("monospace"),
        CURSIVE("cursive"),
        FANTASY("fantasy");

        private final String value;

        Family(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
