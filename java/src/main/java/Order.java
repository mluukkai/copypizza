import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private String status;
    private List<String> ingredients;
    private Map<String, String> customer;
    private int id;

    public Order(String name, String address, List<String> ingredients) {
        this.status = "ordered";
        this.ingredients = ingredients;
        this.customer = new HashMap<>();
        this.customer.put("name", name);
        this.customer.put("address", address);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMaking() {
        this.status = "making";
    }

    public void setDelivered() {
        this.status = "delivered";
    }

    public String getStatus() {
        return status;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public Map<String, String> getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + " " + this.status + " " + this.customer.get("name") + " " + String.join(" ",this.getIngredients());
    }
}
