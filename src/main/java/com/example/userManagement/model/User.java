package com.example.userManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
@Document(collection = "users")

public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    
    @Indexed(unique = true)
    private String email;

    private String password;

    private Boolean blocked = false;

    private Set<Role> roles = new HashSet<>();

    public User(){}

    public User(String id, String firstName, String lastName,String email,String password){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        if(roles != null)

            this.roles.addAll(roles);
        

    }


    // id - getter setter
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    // firstName - getter setter

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    // lastName - getter setter
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    // email - getter setter

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    // password - getter setter

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    // blocked - getter setter
    public Boolean getBlocked(){
        return blocked;
    }
    public void setBlocked(Boolean blocked){
        this.blocked = blocked;
    }

    // roles - getter setter

    public Set<Role> getRoles(){
        return roles;
    }
    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }

    
}


