import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{
        CopyPizza pizzeria = new CopyPizza();
        Order order = new Order("Kalle Ilves", "Koskelantie 100", Arrays.asList("kinkku", "jauheliha", "pekoni", "kebab", "salami"));
        pizzeria.takeOrder(order);
        pizzeria.markMaking(order.getId());
        pizzeria.markDelivered(order.getId());

        Order order2 = new Order("Riikka Korolainen", "Kumpulankaari 55", Arrays.asList("kinkku", "ananas", "aurajuusto"));
        pizzeria.takeOrder(order2);

        for( Order o: pizzeria.listDelivered()) {
            System.out.println(o);
        }

        pizzeria.export("xml", "delivered");

        pizzeria.save();
    }
}
