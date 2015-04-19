package com.tribalcouncil.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.tribalcouncil.controller.QuestionController;
import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;
import com.tribalcouncil.model.Response;

@ManagedBean(eager = false)
@RequestScoped
public class ResultSessionBean {

	@Inject
	QuestionController qc;

	private static int questionId;
	private static Question currentQuestion;
	private static final ArrayList<Answer> answerList = new ArrayList<Answer>();
	private String message;
	private String deleteMessage;
	private boolean voted = false;

	public String getDeleteMessage() {
		return deleteMessage;
	}

	public void setDeleteMessage(String deleteMessage) {
		this.deleteMessage = deleteMessage;
	}

	public boolean isVoted() {
		String userIP = getUserIp();
		if ("Question expired".equals(getTimeLeft())) {
			voted = true;
			message = "Winner: " + getMostVotes();
			return voted;
		}
		for (Response response : currentQuestion.getResponses()) {
			Answer theAnswerWeWant = null;
			for (Answer a : answerList) {
				if (a.getResponses().contains(response)) {
					theAnswerWeWant = a;
				}
			}
			if (response.getIp().equals(userIP)) {
				voted = true;
				message = "Your vote for " + theAnswerWeWant.getAnswer()
						+ " has been counted.";
				break;
			}
		}
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

	public String getMessage() {
		return message;
	}

	public String removeQuestion(String password) {
		if ("password123".equals(password)) {
			qc.removeEntity(currentQuestion);
			deleteMessage = "";
			return "polls.xhtml?faces-redirect=true";
		}
		deleteMessage = " Incorrect password.";
		return null;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeLeft() {
		if (currentQuestion == null) {
			return "";
		}
		Date startDate = new Date();
		Date endDate = currentQuestion.getClosedate();

		long duration = endDate.getTime() - startDate.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);

		if (diffInSeconds <= 0) {
			return "Question expired";
		} else {
			int day = (int) TimeUnit.SECONDS.toDays(diffInSeconds);
			long hours = TimeUnit.SECONDS.toHours(diffInSeconds) - (day * 24);
			long minute = TimeUnit.SECONDS.toMinutes(diffInSeconds)
					- (TimeUnit.SECONDS.toHours(diffInSeconds) * 60);
			long second = TimeUnit.SECONDS.toSeconds(diffInSeconds)
					- (TimeUnit.SECONDS.toMinutes(diffInSeconds) * 60);
			String returnString = day + " days, " + hours + " hours, " + minute
					+ " minutes and " + second
					+ " seconds left to vote on this question";
			return returnString;
		}
	}

	public String getVoteString(int i) {
		if (!"Question expired".equals(getTimeLeft())) {
			return "";
		} else {
			if (i == 1) {
				return i + " vote";
			} else {
				return i + " votes";
			}
		}
	}

	public String getMostVotes() {
		String currentHighest = "";
		int highestCurrent = 0;
		if (answerList.size() == 0) {
			return "This question has no answers";
		} else {
			for (Answer a : answerList) {
				if (a.getResponses().size() > highestCurrent) {
					currentHighest = a.getAnswer();
					highestCurrent = a.getResponses().size();
				} else if (a.getResponses().size() == highestCurrent
						&& highestCurrent != 0) {
					currentHighest += " and " + a.getAnswer();
				}
			}
		}
		if ("".equals(currentHighest)) {
			return "No votes on this question";
		} else {
			if (highestCurrent == 1) {
				return currentHighest + " with " + highestCurrent + " vote.";
			}
			return currentHighest + " with " + highestCurrent + " votes.";
		}
	}

	// Comment
	public ArrayList<Answer> getAnswerList() {
		Collections.sort(answerList);
		return answerList;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		currentQuestion = qc.getQuestion(questionId);
		if (answerList.size() > 0) {
			answerList.clear();
		}
		for (Answer ans : currentQuestion.getAnswers()) {
			answerList.add(ans);
		}
		return currentQuestion.getQuestion();
	}

	public void buttonAction(ActionEvent actionEvent) {
		System.out.println("Button pushed ");
	}

	public void doVote(Answer answer) {
		qc.saveResponse(getUserIp(), answer);
	}

	public String getUserIp() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

}
