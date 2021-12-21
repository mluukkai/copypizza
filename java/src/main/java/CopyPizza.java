import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.PrintWriter;

public class CopyPizza {
    private List<Order> orders;

    public CopyPizza() throws Exception {
        this.orders = new ArrayList<>();
        Scanner scanner = new Scanner(Paths.get("tilaukset.csv"));
        while (scanner.hasNextLine()) {
            String row = scanner.nextLine();
            String[] parts = row.split(";");
            List<String> ingredients = new ArrayList<String>(Arrays.asList(parts[4].split(",")));
            Order order = new Order(parts[2], parts[3], ingredients);
            order.setStatus(parts[1]);
            order.setId(Integer.parseInt(parts[0]));
            this.orders.add(order);
        }
    }

    public void save() throws Exception {
        PrintWriter writer = new PrintWriter("tilaukset.csv");
        for (Order order: this.orders) {
            String ingredients = String.join(",", order.getIngredients());
            writer.println(order.getId()+";"+order.getStatus()+";"+order.getCustomer().get("name")+";"+order.getCustomer().get("address")+";"+ingredients);
        }
        writer.close();
    }

    public List<Order> listDelivered() {
        List <Order> matches = new ArrayList<>();
        for (Order order: this.orders) {
            if (order.getStatus().equals("delivered")) {
                matches.add(order);
            }
        }
        return matches;
    }

    public List<Order> listNew() {
        List <Order> matches = new ArrayList<>();
        for (Order order: this.orders) {
            if (order.getStatus().equals("ordered")) {
                matches.add(order);
            }
        }
        return matches;
    }

    public List<Order> listCustomer(String customerName) {
        List <Order> matches = new ArrayList<>();
        for (Order order: this.orders) {
            if (order.getCustomer().get("name").equals(customerName)) {
                matches.add(order);
            }
        }
        return matches;
    }

    public List<Order> listIngredient(String ingredient) {
        List <Order> matches = new ArrayList<>();
        for (Order order: this.orders) {
            if (order.getIngredients().contains(ingredient)) {
                matches.add(order);
            }
        }
        return matches;
    }

    public void markDelivered(int id) {
        for (Order order: this.orders) {
            if (order.getId() == id) {
                order.setDelivered();
            }
        }
    }

    public void markMaking(int id) {
        for (Order order: this.orders) {
            if (order.getId() == id) {
                order.setMaking();
            }
        }
    }

    public void takeOrder(Order order) {
        order.setId((int)(Math.random()*1000000));
        this.orders.add(order);
    }

    public void export(String format, String status) throws Exception {
        if (format == "xml") {
            System.out.println("<orders>");
        }

        for (Order order: this.orders) {
            if (format.equals("xml") ) {
                if ( order.getStatus().equals(status)) {
                    System.out.println("  <order>");
                    System.out.println("    <customer>");
                    System.out.println("      <name>" + order.getCustomer().get("name") + "</name>");
                    System.out.println("      <address>" + order.getCustomer().get("address") + "</address>");
                    System.out.println("    </customer>");
                    System.out.println("    <numberOfIngredients>");
                    System.out.println("      " + order.getIngredients().size());
                    System.out.println("    </numberOfIngredients>");
                    System.out.println("  </order>");
                }
            } else if ( format.equals("tsv")) {
                if ( order.getStatus().equals(status)) {
                    System.out.println(order.getId()+"\t"+order.getCustomer().get("name")+"\t"+order.getCustomer().get("address")+"\t"+order.getIngredients().size());
                }
            } else {
                throw new Exception("unsupported format");
            }
        }

        if (format == "xml") {
            System.out.println("</orders>");
        }
    }
}
