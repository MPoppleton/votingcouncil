package com.tribalcouncil.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.TransactionManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tribalcouncil.model.Answer;
import com.tribalcouncil.model.Question;
import com.tribalcouncil.model.Response;
import com.tribalcouncil.util.HibernateUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class QuestionController {

	private static SessionFactory factory;

	public QuestionController() {
		factory = HibernateUtil.getSessionFactory();
	}
	
	public Question createQuestion(String newQuestion, Date closeDate, List<String> answers) {
		Session session = factory.openSession();
		Transaction tx = null;
		Question question = new Question();
		try {
			tx = session.beginTransaction();
			question.setClosedate(closeDate);
			question.setQuestion(newQuestion);
			Set<Answer> answerSet = new HashSet<Answer>();
			for (String answerString : answers) {
				Answer answer = new Answer(question, answerString);
				answerSet.add(answer);
			}
			question.setAnswers(answerSet);
			session.persist(question);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return question;
	}
	
	public void addQuestion(Question question) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(question);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Question getQuestion(int questionId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Question question = null;
		try {
			tx = session.beginTransaction();
			question = (Question) session.createQuery("FROM Question WHERE id=" + questionId).uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return question;
	}
	
	public List<Question> getQuestions() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Question> questionList = null;
		try {
			tx = session.beginTransaction();
			questionList = session.createQuery("FROM Question").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return questionList;
	}
	
	public Question getCurrentQuestion() {
		return null;
	}
	
	public void storeVote() {
		
	}

	public String getResult() {
		return "";
	}

	public List<String> getHistory() {
		return null;
	}

	public double getWinnerPercentage() {
		return 0;
	}


	public List<Answer> getAnswers(Question question) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Answer> answerList = null;
		try {
			tx = session.beginTransaction();
			answerList = session
					.createQuery("FROM Answer").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return answerList;
	}

	public void saveResponse(String IP, Answer answer) {
		Session session = factory.openSession();
		Transaction tx = null;
		Response response = new Response(answer.getQuestion(), answer.getId(), IP);
		try {
			tx = session.beginTransaction();
			session.save(response);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean hasVoted(Question question, String ip) {

		return true;
	}
}
