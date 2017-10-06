package edu.caelum.estudos.java8napratica.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Grupo {

    private Set<User> users = new HashSet<>();

    public void add(User user){
        users.add(user);
    }

    public Set<User> getUsers(){
        return Collections.unmodifiableSet(this.users);
    }
}
