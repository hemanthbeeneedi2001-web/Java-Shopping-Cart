import java.util.*;

//  Encapsulation: Product class
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

//  Polymorphism via Discount classes
abstract class Discount {
    public abstract double applyDiscount(double total, List<Product> products);
}

class FestiveDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        return total * 0.90; // 10% off
    }
}

class BulkDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        // Apply 20% discount if ANY product quantity > 5
        for (Product p : products) {
            if (p.getQuantity() > 5) {
                return total * 0.80;
            }
        }
        return total;
    }
}

//  Payment interface
interface Payment {
    void pay(double amount);
}

class OnlinePayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Total Amount Payable: ₹" + amount);
    }
}

//  Main Class
public class ShoppingCartApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> cart = new ArrayList<>();

        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            double price = sc.nextDouble();
            int qty = sc.nextInt();
            cart.add(new Product(name, price, qty));
        }

        String discountType = sc.next().trim().toLowerCase();

        // Calculate total
        double total = 0;
        for (Product p : cart) {
            total += p.getTotalPrice();
        }

        // Choose discount
        Discount discount;
        switch (discountType) {
            case "festive":
                discount = new FestiveDiscount();
                break;
            case "bulk":
                discount = new BulkDiscount();
                break;
            default:
                System.out.println("Invalid discount type!");
                sc.close();
                return;
        }

        // Apply discount
        double finalAmount = discount.applyDiscount(total, cart);

        // Display products
        for (Product p : cart) {
            System.out.println("Product: " + p.getName() + ", Price: " + p.getPrice() + ", Quantity: " + p.getQuantity());
        }

        // Payment
        Payment payment = new OnlinePayment();
        payment.pay(finalAmount);

        sc.close();
    }
}

