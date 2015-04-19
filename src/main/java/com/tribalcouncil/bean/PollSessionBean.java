package com.tribalcouncil.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.tribalcouncil.controller.QuestionController;
import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;
import com.tribalcouncil.model.Response;

@ManagedBean
@ViewScoped
public class PollSessionBean implements Serializable {
	
	private static final long serialVersionUID = -7410329334345627887L;

	@Inject
	QuestionController qc;
	
	private static final ArrayList<Question> questionsList = new ArrayList<Question>();

	@PostConstruct
	public void init() {
		for (Question quest : qc.getQuestions()) {
			questionsList.add(quest);
		}
	}
	
	public String getWinner(Question question) {
		if (question.getResponses().size() == 0 || !question.isClosed()) {
			return "";
		} else { 
			int currentHighest = 0;
			String currentWinner = "";
			Set<Answer> answerList = question.getAnswers();
			for (Answer answer : answerList) {
				if (answer.getResponses().size() > currentHighest) {
					currentHighest = answer.getResponses().size();
					currentWinner = answer.getAnswer();
				} else if (answer.getResponses().size() == currentHighest
						&& currentHighest != 0) {
					currentWinner += " and " + answer.getAnswer();
				}
			}
			return currentWinner;
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
	
	public boolean hasExpired(Question question) {
		Date startDate = new Date();
		Date endDate = question.getClosedate();

		long duration = endDate.getTime() - startDate.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

		if (diffInSeconds <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Question> getQuestionlist() {
		if (questionsList.size() > 0) {
			questionsList.clear();
			init();
		}
		
		Collections.sort(questionsList, new Comparator<Question>() {
	        @Override
	        public int compare(Question q1, Question q2)
	        {
	        	if (hasExpired(q1) && hasExpired(q2)) {
	        		return 0;
	        	} else if (!hasExpired(q1) && !hasExpired(q2)) {
	        		return 0;
	        	} else if (hasExpired(q1) && !hasExpired(q2)) {
	        		return  1;
	        	} else {
	        		return -1;
	        	}
	        }
	    });
		return questionsList;
	}
	
	
	
	

	
	
	
	
	
	
}
