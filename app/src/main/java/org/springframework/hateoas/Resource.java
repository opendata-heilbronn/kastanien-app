package org.springframework.hateoas;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Arrays;

public class Resource<T> extends ResourceSupport {

    private final T content;

    /**
     * Creates an empty {@link org.springframework.hateoas.Resource}.
     */
    Resource() {
        this.content = null;
    }

    /**
     * Creates a new {@link org.springframework.hateoas.Resource} with the given content and {@link org.springframework.hateoas.Link}s (optional).
     *
     * @param content must not be {@literal null}.
     * @param links   the links to add to the {@link org.springframework.hateoas.Resource}.
     */
    public Resource(T content, Link... links) {
        this(content, Arrays.asList(links));
    }

    /**
     * Creates a new {@link org.springframework.hateoas.Resource} with the given content and {@link org.springframework.hateoas.Link}s.
     *
     * @param content must not be {@literal null}.
     * @param links   the links to add to the {@link org.springframework.hateoas.Resource}.
     */
    public Resource(T content, Iterable<Link> links) {
        this.content = content;
        this.add(links);
    }

    /**
     * Returns the underlying entity.
     *
     * @return the content
     */
    @JsonUnwrapped
    public T getContent() {
        return content;
    }

    /*
     * (non-Javadoc)
     * @see org.org.springframework.hateoas.ResourceSupport#toString()
     */
    @Override
    public String toString() {
        return String.format("Resource { content: %s, %s }", getContent(), super.toString());
    }

    /*
     * (non-Javadoc)
     * @see org.org.springframework.hateoas.ResourceSupport#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        org.springframework.hateoas.Resource<?> that = (org.springframework.hateoas.Resource<?>) obj;
        boolean contentEqual = this.content == null ? that.content == null : this.content.equals(that.content);
        return contentEqual ? super.equals(obj) : false;
    }

    /*
     * (non-Javadoc)
     * @see org.org.springframework.hateoas.ResourceSupport#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += content == null ? 0 : 17 * content.hashCode();
        return result;
    }
}
