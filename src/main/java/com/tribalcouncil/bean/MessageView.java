/**
 * 
 */
package com.tribalcouncil.bean;

/**
 * @author Dylan
 *
 */
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class MessageView {

	public void setError(String qErrorMsg) {

		if (AnswerSessionBean.DUPLICATE_QUESTION_ERROR.equals(qErrorMsg)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"That question already exists."));
		}

		else if (AnswerSessionBean.DUPLICATE_ANSWER_ERROR.equals(qErrorMsg)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"That answer already exists."));
		
		}
			else if (AnswerSessionBean.PAST_DATE_ERROR.equals(qErrorMsg)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
								"Please enter a date/time in the future."));
			
			}
		
		else if (AnswerSessionBean.BLANK_ANSWER_ERROR.equals(qErrorMsg)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"Answer field is blank."));
		}
		
		else if (AnswerSessionBean.NO_ANSWER_ERROR.equals(qErrorMsg)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"Please add at least one answer."));
		}
		
		else if (AnswerSessionBean.EMPTY_QUESTION_ERROR.equals(qErrorMsg)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"Empty question field."));
		}
	
	else if (AnswerSessionBean.EMPTY_DATE_ERROR.equals(qErrorMsg)) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
						"Empty date field."));
	}
}
	
	

	
}