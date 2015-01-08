package org.springframework.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ResourceSupport implements Identifiable<org.springframework.hateoas.Link> {

    private final List<org.springframework.hateoas.Link> links;

    public ResourceSupport() {
        this.links = new ArrayList<org.springframework.hateoas.Link>();
    }

    /**
     * Returns the {@link org.springframework.hateoas.Link} with a rel of {@link org.springframework.hateoas.Link#REL_SELF}.
     */
    @JsonIgnore
    public org.springframework.hateoas.Link getId() {
        return getLink(org.springframework.hateoas.Link.REL_SELF);
    }

    /**
     * Adds the given link to the resource.
     *
     * @param link
     */
    public void add(org.springframework.hateoas.Link link) {
        this.links.add(link);
    }

    /**
     * Adds all given {@link org.springframework.hateoas.Link}s to the resource.
     *
     * @param links
     */
    public void add(Iterable<org.springframework.hateoas.Link> links) {
        for (org.springframework.hateoas.Link candidate : links) {
            add(candidate);
        }
    }

    /**
     * Returns whether the resource contains {@link org.springframework.hateoas.Link}s at all.
     *
     * @return
     */
    public boolean hasLinks() {
        return !this.links.isEmpty();
    }

    /**
     * Returns whether the resource contains a {@link org.springframework.hateoas.Link} with the given rel.
     *
     * @param rel
     * @return
     */
    public boolean hasLink(String rel) {
        return getLink(rel) != null;
    }

    /**
     * Returns all {@link org.springframework.hateoas.Link}s contained in this resource.
     *
     * @return
     */
    @JsonProperty("links")
    public List<org.springframework.hateoas.Link> getLinks() {
        return links;
    }

    /**
     * Removes all {@link org.springframework.hateoas.Link}s added to the resource so far.
     */
    public void removeLinks() {
        this.links.clear();
    }

    /**
     * Returns the link with the given rel.
     *
     * @param rel
     * @return the link with the given rel or {@literal null} if none found.
     */
    public org.springframework.hateoas.Link getLink(String rel) {
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("links: %s", links.toString());
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
        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }
        org.springframework.hateoas.ResourceSupport that = (org.springframework.hateoas.ResourceSupport) obj;
        return this.links.equals(that.links);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.links.hashCode();
    }
}
