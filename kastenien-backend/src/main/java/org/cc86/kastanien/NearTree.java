package org.cc86.kastanien;

import org.cc86.kastanien.data.Tree;
import org.springframework.hateoas.Resource;

/**
 * Created by adrian on 23.10.14.
 */
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
