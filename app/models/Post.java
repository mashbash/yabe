package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Post extends Model {
	
	public String title;
	public Date postedAt;
	
	@Lob // tells JPA to use large txt db to store post content
	public String content;
	
	@ManyToOne
	public User author;
	
	public Post(User author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}
}