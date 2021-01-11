package uk.nstr.perworldserver.util.string;

public class StringPlaceholder {

    private String placeholder;
    private String fill;

    public static StringPlaceholder create(final String placeholder, final String fill) {
        return new StringPlaceholder(placeholder, fill);
    }

    public StringPlaceholder(final String placeholder, final String fill) {
        this.placeholder = placeholder;
        this.fill = fill;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public String getFill() {
        return this.fill;
    }

}
