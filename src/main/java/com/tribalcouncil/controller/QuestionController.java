package com.tribalcouncil.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;
import com.tribalcouncil.model.Response;

@Stateless
public class QuestionController {

	@PersistenceContext(unitName="primary")
	private EntityManager em;
		
	public Question createQuestion(String newQuestion, Date closeDate, List<String> answers) {
		Question question = new Question();
		question.setClosedate(closeDate);
		question.setQuestion(newQuestion);
		Set<Answer> answerSet = new HashSet<Answer>();
		for (String answerString : answers) {
			Answer answer = new Answer(question, answerString);
			answerSet.add(answer);
		}
		question.setAnswers(answerSet);
		em.persist(question);
		return question;
	}
	
	public void addQuestion(Question question) {
		em.persist(question);
	}
	
	public Question getQuestion(int questionId) {
		return em.find(Question.class, questionId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> getQuestions() {
		Query query = em.createQuery("SELECT q FROM Question q");
		return (List<Question>) query.getResultList();
	}
	
	public void removeEntity(Object o) {
		em.remove(em.merge(o));
	}

	public void saveResponse(String IP, Answer answer) {
		Response response = new Response(answer.getQuestion(), answer.getId(), IP);
		em.persist(response);
	}

}
