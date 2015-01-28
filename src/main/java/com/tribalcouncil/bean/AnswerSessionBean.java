package com.tribalcouncil.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.tribalcouncil.controller.QuestionController;
import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;

@SessionScoped
@ManagedBean
public class AnswerSessionBean implements Serializable {

	private static final long serialVersionUID = -4952088820077388691L;
	
	String questionId;
	String answer;
	String error;
	String submitError;
	
	public String getSubmitError() {
		return submitError;
	}

	public void setSubmitError(String submitError) {
		this.submitError = submitError;
	}

	@Inject
	QuestionController qc;
	
	private static final ArrayList<Answer> questionList = new ArrayList<Answer>();
	private static final ArrayList<String> currentList = new ArrayList<String>();
	
	public ArrayList<Answer> getQuestionList() {
		return questionList;
	}
	
	public String submit(String questionName, Date date) {
		if ("".equals(questionName) || date.equals(null)) {
			
		}
		List<String> answerList = new ArrayList<String>();
		for (Answer answer : questionList) {
			answerList.add(answer.getAnswer());
		}
		Question question = qc.createQuestion(questionName, date, answerList);
		if (question.getId() == null) {
			submitError = "That question already exists";
			return null;
		}
		return "result.xhtml?faces-redirect=true&questionId=" + question.getId();
	}

	public String addAction() {
		if (currentList.contains(this.answer)) {
			error = "Answer already present";
		} else {
			currentList.add(this.answer);
			Answer answer = new Answer();
			answer.setAnswer(this.answer);
			answer.setQuestion(null);
			questionList.add(answer);
		}
		return null;
	}
	
	public String deleteAction(Answer answer) {
		currentList.remove(answer.getAnswer());
		questionList.remove(answer);
		return null;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

}
