package osa.projekat.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import osa.projekat.entity.User;

public class PostRequest {

	@NotBlank
	@Size(max = 40)
	private String title;
	
	@NotBlank
	@Size(max=40)
	private String description;
	
	@NotBlank
	private String photo;
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
	
	
	
	
	
	
	
}
