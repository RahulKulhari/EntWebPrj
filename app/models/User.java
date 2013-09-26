package models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.SqlRow;

import play.data.validation.*;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;




@Entity
public class User extends Model {

	@Id
	public Long id;
	
	
	@Transient
	public String uuid=UUID.randomUUID().toString();
    
    
	



	@Required
	public String name;
	
	
	@Transient
	@Required
	@MinLength(value=6)
	private String password;
	
	@Column(length = 256, unique = true, nullable = false)
    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
	private String email;
	
	@Column(length = 64, nullable = false)
    private byte[] shaPassword;
	
	
	
	


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
		shaPassword=getSha512(password);
	}
	


	public String getEmail() {
		return email;
	}

	

	public void setEmail(String email) {
		this.email = email;
	}



	public User(String name,String password,String email)
	{
		setEmail(email);
		setPassword(password);
		
		this.name=name;
		
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}



	public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        	//return MessageDigest.getInstance(algorithm)
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	
	public static User findById(Long id) {
       User u=find.where().eq("id",id).findUnique();
       
       return u;
    }
	
	public static User authenticate(String email, String password) {

        
        User user = find.where().eq("email", email).eq("shaPassword", getSha512(password)).findUnique();
        if (user != null) {
            // get the hash password from the salt + clear password
            
              return user;
            
        }
        return null;
    }
	
	
	
	
	public static void Delete(Long id){
		
		final User user = User.findById(id);
		user.delete();
	}
	
	
	
	
	public static User findByEmail(String email){
		
		return find.where().eq("email", email).findUnique();
		
	}
	
	public static Boolean check(String email){
		User u=find.where().eq("email", email).findUnique();
		if(u!=null){
			
			return false;
			
		}
		else{
			return true;
		}
	}
	public static User findByusernameAndPassword(String email, String password) {
        // todo: verify this query is correct.  Does it need an "and" statement?
        return find.where().eq("email", email).eq("shaPassword", getSha512(password)).findUnique();
    }
	
	
	public static Page<User> page(int page, int pageSize, String sortBy, String order, String filter) {
        
		
		return find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }
	
}
