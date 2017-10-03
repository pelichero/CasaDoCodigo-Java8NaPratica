package edu.caelum.estudos.java8napratica;

import edu.caelum.estudos.java8napratica.consumers.Mostrador;
import edu.caelum.estudos.java8napratica.functionalInterfaces.Validator;
import edu.caelum.estudos.java8napratica.model.DataPreLoaded;
import edu.caelum.estudos.java8napratica.model.User;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Testes {

    @Test
    public void loadListOldScenario(){
        for (User u: DataPreLoaded.users.stream().collect(Collectors.toList()))
            System.out.println(u);
    }

    @Test
    public void testForEach(){

        Mostrador mostrador = new Mostrador();
        DataPreLoaded.users.stream().forEach(mostrador);

        System.out.println(">>>>>>>>>> Segunda forma");
        DataPreLoaded.users.stream().forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getNome());
            }
        });
    }

    @Test
    public void testSimpleLambda(){
        System.out.println(">>>>>>>>>> Inclusao do lambda");
        DataPreLoaded.users.stream().forEach(u -> System.out.println(u.getNome()));

        System.out.println(">>>>>>>>>> lambdas em threads - Interface funcional Runnable");

        new Thread(() -> {for (int i = 0; i < 10; i++){System.out.println(i);}}).start();
    }


    @Test
    public void testSimpleLambdaValidator() {
        Validator<String> validator = valor -> {
            return valor.equals("Teste");
        };

        System.out.println(validator.valida("Teste"));

        Validator<Integer> validatorNumber = number -> {
            return number > 10;
        };

        System.out.println(validatorNumber.valida(21));
    }


    @Test
    public void testConsumer() {
        System.out.println(">>>>>>>>>> Testando consumers");

        Consumer<User> mostraSufixo = c -> System.out.println("Testando o lambda");

        Consumer<User> mostraNome = c -> System.out.println(c.getNome());

        DataPreLoaded.users.stream().forEach(mostraSufixo.andThen(mostraNome));
    }

}
