package de.opendatalab.kastanien.near;

import de.opendatalab.kastanien.Tree;
import org.springframework.hateoas.Resource;

public class NearTree {

	private Resource<Tree> content;
	private double distance;

	public NearTree(Resource<Tree> content, double distance) {
		this.content = content;
		this.distance = distance;
	}

	public Resource<Tree> getContent() {
		return content;
	}

	public void setContent(Resource<Tree> content) {
		this.content = content;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
