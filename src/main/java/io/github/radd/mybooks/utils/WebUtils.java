package io.github.radd.mybooks.utils;

import io.github.radd.mybooks.domain.Book;
import io.github.radd.mybooks.domain.Review;
import org.springframework.beans.factory.annotation.Value;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class WebUtils {

    public static class Slug {

        //http://www.codecodex.com/wiki/Generate_a_url_slug

        private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
        private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

        public static String makeSlug(String text) {
           return prepareSlug(text, "-", true);
        }

        public static String makeTagSlug(String text) {
            return prepareSlug(text, "", false);
        }

        private static String prepareSlug(String text, String replacement, boolean toLowerCase) {
            int length = text.length();
            length = length <= 1900 ? length : 1900;
            String nowhitespace = WHITESPACE.matcher(text.substring(0, length)).replaceAll(replacement);
            String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
            String slug = normalized.replaceAll("ł", "l");
            slug = NONLATIN.matcher(slug).replaceAll("");
            slug = toLowerCase ? slug.toLowerCase() : slug;
            return slug;
        }
    }

    //TODO  route system
    public static class Link {

        //TODO dynamic optain context path !!!
        private static final String contextPath = "mybooks";

        public static String get(Object obj) {

            String path = "/" + contextPath + "/";
            if(obj instanceof Book) {
                Book b = (Book) obj;
                path += "book/" + b.getSlug();
            }
            else if(obj instanceof Review) {
                Review r = (Review) obj;
                path += "review/" + r.getSlug();
            }

            return path;
        }
    }

}
