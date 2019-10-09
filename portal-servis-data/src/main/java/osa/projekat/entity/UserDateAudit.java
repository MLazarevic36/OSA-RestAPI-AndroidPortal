package osa.projekat.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(
		value = {"createdBy"},
		allowGetters=true
		)
public class UserDateAudit extends DateAudit {
	
	@CreatedBy
	@Column(updatable = false, nullable = false)
	private Long createdBy;
	
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	
	
	

}
