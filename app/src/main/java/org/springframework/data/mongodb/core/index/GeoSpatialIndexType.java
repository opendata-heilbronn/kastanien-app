package org.springframework.data.mongodb.core.index;

public enum GeoSpatialIndexType {

    /**
     * Simple 2-Dimensional index for legacy-format points.
     */
    GEO_2D,

    /**
     * 2D Index for GeoJSON-formatted data over a sphere. Only available in Mongo 2.4.
     */
    GEO_2DSPHERE,

    /**
     * An haystack index for grouping results over small results.
     */
    GEO_HAYSTACK
}
