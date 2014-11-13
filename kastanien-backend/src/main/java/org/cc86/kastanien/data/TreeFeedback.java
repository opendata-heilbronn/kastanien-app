package org.cc86.kastanien.data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

/**
 * Created by adrian on 23.10.14.
 */
public class TreeFeedback {

	@Id
	private String treeFeedbackId;

	@CreatedDate
	private long timestamp;

	private String comment;
	private int state;


}
