package com.tribalcouncil.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.tribalcouncil.controller.QuestionController;
import com.tribalcouncil.model.Question;

@SessionScoped
@ManagedBean
public class PollSessionBean implements Serializable {
	
	private static final long serialVersionUID = -7410329334345627887L;

	@Inject
	QuestionController qc;
	
	private int index = 0;
	
	private static final ArrayList<Question> questionsList = new ArrayList<Question>();

	@PostConstruct
	public void init() {
		for (Question quest : qc.getQuestions()) {
			questionsList.add(quest);
		}
	}
	
	public String isExpired(Question question) {
		Date startDate = new Date();
		Date endDate = question.getClosedate();

		long duration = endDate.getTime() - startDate.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

		if (diffInSeconds <= 0) {
			return "Closed";
		} else {
			return "Active";
		}
	}
	
	public ArrayList<Question> getQuestionlist() {
		if (questionsList.size() > 0) {
			questionsList.clear();
			init();
		}
		return questionsList;
	}

	
	
	
	
	
	
}
