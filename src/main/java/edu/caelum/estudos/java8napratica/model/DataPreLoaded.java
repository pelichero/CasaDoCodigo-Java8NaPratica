package edu.caelum.estudos.java8napratica.model;

import java.util.Arrays;
import java.util.List;

public class DataPreLoaded {

    public static List<User> users = Arrays.asList(
            new User("Felipe", 10, true),
            new User("Karina", 1, false),
            new User("Mel", 2, false),
            new User("Latiff", 4, false)
    );

}
