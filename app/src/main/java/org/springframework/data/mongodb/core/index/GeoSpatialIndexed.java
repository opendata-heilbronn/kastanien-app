package org.springframework.data.mongodb.core.index;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeoSpatialIndexed {

    /**
     * Name of the property in the document that contains the [x, y] or radial coordinates to index.
     *
     * @return
     */
    String name() default "";

    /**
     * If set to {@literal true} then MongoDB will ignore the given index name and instead generate a new name. Defaults
     * to {@literal false}.
     *
     * @return
     * @since 1.5
     */
    boolean useGeneratedName() default false;

    /**
     * Name of the collection in which to create the index.
     *
     * @return
     */
    String collection() default "";

    /**
     * Minimum value for indexed values.
     *
     * @return
     */
    int min() default -180;

    /**
     * Maximum value for indexed values.
     *
     * @return
     */
    int max() default 180;

    /**
     * Bits of precision for boundary calculations.
     *
     * @return
     */
    int bits() default 26;

    /**
     * The type of the geospatial index. Default is {@link org.springframework.data.mongodb.core.index.GeoSpatialIndexType#GEO_2D}
     *
     * @return
     * @since 1.4
     */
    org.springframework.data.mongodb.core.index.GeoSpatialIndexType type() default org.springframework.data.mongodb.core.index.GeoSpatialIndexType.GEO_2D;

    /**
     * The bucket size for {@link org.springframework.data.mongodb.core.index.GeoSpatialIndexType#GEO_HAYSTACK} indexes, in coordinate units.
     *
     * @return
     * @since 1.4
     */
    double bucketSize() default 1.0;

    /**
     * The name of the additional field to use for {@link org.springframework.data.mongodb.core.index.GeoSpatialIndexType#GEO_HAYSTACK} indexes
     *
     * @return
     * @since 1.4
     */
    String additionalField() default "";
}
