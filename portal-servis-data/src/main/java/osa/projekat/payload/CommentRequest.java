package osa.projekat.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CommentRequest {
	
	
	private Long post_id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String desc;

	public Long getPostId() {
		return post_id;
	}

	public void setPostId(Long postId) {
		this.post_id = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
