package de.opendatalab.kastanien;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

public class TreeFeedback {

	@Id
	private String treeFeedbackId;

	@CreatedDate
	private long timestamp;

	private String comment;
	private int state;


}
