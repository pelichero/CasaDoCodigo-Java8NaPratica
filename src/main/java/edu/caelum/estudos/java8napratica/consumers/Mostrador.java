package edu.caelum.estudos.java8napratica.consumers;

import edu.caelum.estudos.java8napratica.model.User;

import java.util.function.Consumer;

public class Mostrador implements Consumer<User> {

    @Override
    public Consumer<User> andThen(Consumer<? super User> after) {
        return null;
    }

    @Override
    public void accept(User user) {
        System.out.println(user);
    }
}
