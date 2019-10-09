package osa.projekat.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name="posts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"comments"}, allowGetters = true)
public class Post extends UserDateAudit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="post_id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="post_title", unique=false, nullable=false)
	private String title;
	
	@Column(name="post_desc", unique=false, nullable=false)
	private String description;
	
	@Column(name="post_photo", unique=false, nullable=false, columnDefinition = "LONGBLOB")
	@Lob
	private String photo;
	
	
	@Column(name="post_location", unique=false, nullable=false)
	private String location;
	
	@Column(name="post_tags", unique=false, nullable=false)
	private String tags;
	
	
	@Column(name="post_likes", unique=false, nullable=false)
	private Integer likes;
	
	@Column(name="post_dislikes", unique=false, nullable=false)
	private Integer dislikes;
	
	@OneToMany(cascade={ALL}, fetch=LAZY, mappedBy="post")
	private Set<Comment> comments = new HashSet<Comment> ();


	public Set<Comment> getComments() {
		return comments;
	}



	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}



	public Post() {
		
		
	}
	
	
	
	public Post(Long id, String title, String description, String photo, String location,
			String tags, Integer likes, Integer dislikes) {
		super();
	
		this.id=id;
		this.title = title;
		this.description = description;
		this.photo = photo;
		this.location = location;
		this.tags = tags;
		this.likes = likes;
		this.dislikes = dislikes;
		
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
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
	
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPost(this);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setPost(null);
	}
}
