package de.opendatalab.kastanien;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class GeoResultsWithBoundingBox {

    private List<NearTree> trees;
    private BoundingBox boundingBox;

    public GeoResultsWithBoundingBox() {
    }


    public GeoResultsWithBoundingBox(List<NearTree> trees) {
        this.trees = trees;
        this.boundingBox = new BoundingBox();
        for (NearTree tree : trees) {
            boundingBox.addPosition(tree.getContent().getContent().getLocation());
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return trees.isEmpty();
    }

    public List<NearTree> getTrees() {
        return trees;
    }

    public void setTrees(List<NearTree> trees) {
        this.trees = trees;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }
}
