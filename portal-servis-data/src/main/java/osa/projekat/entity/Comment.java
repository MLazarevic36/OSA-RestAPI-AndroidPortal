package osa.projekat.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment extends UserDateAudit {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="comment_id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="comm_title", unique=false, nullable=false)
	private String title;
	
	@Column(name="comm_desc", unique=false, nullable=false)
	private String description;
	
	@Column(name="comm_likes", unique=false, nullable=false)
	private Integer likes;
	
	@Column(name="comm_dislikes", unique=false, nullable=false)
	private Integer dislikes;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade=CascadeType.ALL)
	@JoinColumn(name="post_id", referencedColumnName="post_id", nullable=false)
	private Post post;
	
	

	public Comment() {
		
	}
	
	
	public Comment(String title, String description, Integer likes, Integer dislikes,
			 Post post) {
		super();
		this.title = title;
		this.description = description;
		
		this.likes = likes;
		this.dislikes = dislikes;
		this.post = post;
	}





	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	
	
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	public Integer getDislikes() {
		return dislikes;
	}
	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}
	
	
	@JsonIgnore
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
}
