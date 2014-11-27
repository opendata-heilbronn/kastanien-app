package de.opendatalab.kastanien;

import org.geojson.Feature;

import java.io.Serializable;

/**
 * Created by tgoerner on 27.11.2014.
 */
public class TreeMeta {
    private String treePlantYear;
    private String treeTopSize;
    private String treeTrunkSize;
    private Feature tree;

    public TreeMeta(String treePlantYear, String treeTopSize, String treeTrunkSize, Feature tree) {
        this.treePlantYear = treePlantYear;
        this.treeTopSize = treeTopSize;
        this.treeTrunkSize = treeTrunkSize;
        this.tree = tree;
    }

    public String getTreePlantYear() {
        return treePlantYear;
    }

    public String getTreeTopSize() {
        return treeTopSize;
    }

    public String getTreeTrunkSize() {
        return treeTrunkSize;
    }

    public Feature getTree() {
        return tree;
    }
}