package org.springframework.hateoas;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link implements Serializable {

    public static final String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";
    public static final String REL_SELF = "self";
    public static final String REL_FIRST = "first";
    public static final String REL_PREVIOUS = "prev";
    public static final String REL_NEXT = "next";
    public static final String REL_LAST = "last";
    private static final long serialVersionUID = -9037755944661782121L;
    private String rel;
    private String href;

    /**
     * Creates a new link to the given URI with the self rel.
     *
     * @param href must not be {@literal null} or empty.
     * @see #REL_SELF
     */
    public Link(String href) {
        this(href, REL_SELF);
    }

    /**
     * Creates a new {@link org.springframework.hateoas.Link} to the given URI with the given rel.
     *
     * @param href must not be {@literal null} or empty.
     * @param rel  must not be {@literal null} or empty.
     */
    public Link(String href, String rel) {
        this.href = href;
        this.rel = rel;
    }

    /**
     * Empty constructor required by the marshalling framework.
     */
    protected Link() {
    }

    /**
     * Factory method to easily create {@link org.springframework.hateoas.Link} instances from RFC-5988 compatible {@link String} representations of a
     * link. Will return {@literal null} if an empty or {@literal null} {@link String} is given.
     *
     * @param element an RFC-5899 compatible representation of a link.
     * @return
     * @throws IllegalArgumentException if a non-empty {@link String} was given that does not adhere to RFC-5899.
     * @throws IllegalArgumentException if no {@code rel} attribute could be found.
     */
    public static org.springframework.hateoas.Link valueOf(String element) {
        if (!StringUtils.hasText(element)) {
            return null;
        }
        Pattern uriAndAttributes = Pattern.compile("<(.*)>;(.*)");
        Matcher matcher = uriAndAttributes.matcher(element);
        if (matcher.find()) {
            Map<String, String> attributes = getAttributeMap(matcher.group(2));
            if (!attributes.containsKey("rel")) {
                throw new IllegalArgumentException("Link does not provide a rel attribute!");
            }
            return new org.springframework.hateoas.Link(matcher.group(1), attributes.get("rel"));
        } else {
            throw new IllegalArgumentException(
                    String.format("Given link header %s is not RFC5988 compliant!", element));
        }
    }

    /**
     * Parses the links attributes from the given source {@link String}.
     *
     * @param source
     * @return
     */
    private static Map<String, String> getAttributeMap(String source) {
        if (!StringUtils.hasText(source)) {
            return Collections.emptyMap();
        }
        Map<String, String> attributes = new HashMap<String, String>();
        Pattern keyAndValue = Pattern.compile("(\\w+)=\\\"(\\p{Alnum}*)\"");
        Matcher matcher = keyAndValue.matcher(source);
        while (matcher.find()) {
            attributes.put(matcher.group(1), matcher.group(2));
        }
        return attributes;
    }

    /**
     * Returns the actual URI the link is pointing to.
     *
     * @return
     */
    public String getHref() {
        return href;
    }

    /**
     * Returns the rel of the link.
     *
     * @return
     */
    public String getRel() {
        return rel;
    }

    /**
     * Returns a {@link org.springframework.hateoas.Link} pointing to the same URI but with the given relation.
     *
     * @param rel must not be {@literal null} or empty.
     * @return
     */
    public org.springframework.hateoas.Link withRel(String rel) {
        return new org.springframework.hateoas.Link(href, rel);
    }

    /**
     * Returns a {@link org.springframework.hateoas.Link} pointing to the same URI but with the {@code self} relation.
     *
     * @return
     */
    public org.springframework.hateoas.Link withSelfRel() {
        return withRel(org.springframework.hateoas.Link.REL_SELF);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.springframework.hateoas.Link)) {
            return false;
        }
        org.springframework.hateoas.Link that = (org.springframework.hateoas.Link) obj;
        return this.href.equals(that.href) && this.rel.equals(that.rel);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * href.hashCode();
        result += 31 * rel.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("<%s>;rel=\"%s\"", href, rel);
    }
}
