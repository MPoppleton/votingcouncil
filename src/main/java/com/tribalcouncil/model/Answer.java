package com.tribalcouncil.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "answer", catalog = "tribalcouncil")
public class Answer implements java.io.Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6471902308561151194L;
	private Integer id;
	private Question question;
	private String answer;
	private Set<Response> responses = new HashSet<Response>(0);

	public Answer() {
	}

	public Answer(Question question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public Answer(Question question, String answer, Set<Response> responses) {
		this.question = question;
		this.answer = answer;
		this.responses = responses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "questionid", nullable = false)
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Column(name = "answer", nullable = false, length = 45)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "answer")
	public Set<Response> getResponses() {
		return this.responses;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}

	@Override
	public int compareTo(Object o) {
		int compareResult = ((Answer) o).getId();
		return this.getId()-compareResult;
	}

}
