package com.tribalcouncil.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.tribalcouncil.controller.QuestionController;
import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;

@ManagedBean(eager = false)
@RequestScoped
public class AnswerSessionBean implements Serializable {

	private static final long serialVersionUID = -4952088820077388691L;

	String questionId;
	String answer;
	public static final String EMPTY_QUESTION_ERROR = "emptyQError";
	public static final String PAST_DATE_ERROR = "pastDateError";
	public static final String EMPTY_DATE_ERROR = "emptyDateError";
	public static final String NO_ANSWER_ERROR = "noAnswersError";
	public static final String BLANK_ANSWER_ERROR = "blankAnsError";
	public static final String DUPLICATE_QUESTION_ERROR = "qDuplicate";
	public static final String DUPLICATE_ANSWER_ERROR = "aDuplicate";

	@Inject
	QuestionController qc;

	@Inject
	MessageView msgBean;
	
	private static final ArrayList<Answer> questionList = new ArrayList<Answer>();
	private static final ArrayList<String> currentList = new ArrayList<String>();

	public ArrayList<Answer> getQuestionList() {
		return questionList;
	}

	public String submit(String questionName, Date date) {		
		if ("".equals(questionName)) {
			msgBean.setError(EMPTY_QUESTION_ERROR);
			return null;
		} else if (date == null) {
			msgBean.setError(EMPTY_DATE_ERROR);
			return null;
		} else if (questionList.size() == 0) {
			msgBean.setError(NO_ANSWER_ERROR);
			return null;
		} else if (pastDate(date)) {
			msgBean.setError(PAST_DATE_ERROR);
			return null;
		} else {
			List<String> answerList = new ArrayList<String>();
			for (Answer answer : questionList) {
				answerList.add(answer.getAnswer());
			}
			Question question = qc.createQuestion(questionName, date,
					answerList);
			if (question.getId() == null) {
				msgBean.setError(DUPLICATE_QUESTION_ERROR);
				clearLists();
				return null;
			} else {
				clearLists();
				return "result.xhtml?faces-redirect=true&questionId="
						+ question.getId();
			}
		}
	}

	private void clearLists() {
		questionList.clear();
		currentList.clear();
	}

	public String addAction() {
		if (currentList.contains(this.answer)) {
			msgBean.setError(DUPLICATE_ANSWER_ERROR);
		} else if ("".equals(this.answer)) {
			msgBean.setError(BLANK_ANSWER_ERROR);
		} else {
			currentList.add(this.answer);
			Answer answer = new Answer();
			answer.setAnswer(this.answer);
			answer.setQuestion(null);
			questionList.add(answer);
			this.answer = "";
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

	public boolean pastDate(Date date) {
		Date startDate = new Date();
		Date comparableDate = date;

		long duration = comparableDate.getTime() - startDate.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

		if (diffInSeconds <= 0) {
			return true;
		} else {
			return false;
		}
	}

}
