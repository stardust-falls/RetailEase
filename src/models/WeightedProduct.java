package models;

public class WeightedProduct extends Product {
    private double weight;
    private double pricePerKG;

    public WeightedProduct() {
        super();
        this.weight = 0.0;
        this.pricePerKG = 0.0;
    }

    public WeightedProduct(int productID, String name, int stockQuantity, double weight, double pricePerKG) {
        super(productID, name, 0.0, stockQuantity);
        this.weight = weight;
        this.pricePerKG = pricePerKG;
    }

    public double getWeight() { return weight; }
    public double getPricePerKG() { return pricePerKG; }

    public void setWeight(double weight) { this.weight = weight; }
    public void setPricePerKG(double pricePerKG) { this.pricePerKG = pricePerKG; }

    @Override
    public double calcPrice(int quantity) {
        return (this.weight * this.pricePerKG) * quantity;
    }

    @Override
    public String toString() {
        return "WeightedProduct{" + "productID=" + productID + ", name='" + name + '\'' + ", weight=" + weight + ", pricePerKG=" + pricePerKG + '}';
    }
}