package controllers;

import play.*;
import play.mvc.*;

import play.Play;

import java.util.*;

import models.*;

import play.cache.Cache;

import play.data.validation.*;
import play.libs.*;
import play.cache.*;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}
	
    public static void index() {
       Post frontPost = Post.find("order by postedAt desc").first();
       List<Post> olderPosts = Post.find(
    		   "order by postedAt desc"
    		   ).from(1).fetch(10);
    	render(frontPost, olderPosts);
    }
    
    public static void show(Long id) {
    	Post post = Post.findById(id);
    	String randomID = Codec.UUID();
    	render(post, randomID);
    	
    }
    
    public static void postComment(Long postId, String author, String content) {
    	Post post = Post.findById(postId);
    	if (validation.hasErrors()) {
    		render("Application/show.html", post);
    	}
    	post.addComment(author, content);
    	flash.success("Thanks for posting %s", author);
    	show(postId);
    }
    
    
    public static void captcha(String id) {
    	Images.Captcha captcha = Images.captcha();
    	String code = captcha.getText("#E4EAFD");
    	Cache.set(id,  code, "10mn");
    	renderBinary(captcha);
    }

}