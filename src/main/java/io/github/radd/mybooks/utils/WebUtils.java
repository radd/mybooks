package io.github.radd.mybooks.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class WebUtils {

    //http://www.codecodex.com/wiki/Generate_a_url_slug

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String makeSlug(String text) {
        int length = text.length();
        length = length <= 1900 ? length : 1900;
        String nowhitespace = WHITESPACE.matcher(text.substring(0, length)).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("ł", "l");
        slug = NONLATIN.matcher(slug).replaceAll("");
        slug = slug.toLowerCase();
        return slug;
    }

}
