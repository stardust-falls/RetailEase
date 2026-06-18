package models;

public class MemberCustomer extends Customer {
    private String membershipStatus;
    private double discountRate;

    public MemberCustomer(String customerName, int customerID, String phoneNumber, int age, String membershipStatus) {
        super(customerName, customerID, phoneNumber, age);
        this.membershipStatus = membershipStatus;
        this.discountRate = 0.05;
    }

    // Setters
    public void setMembershipStatus(String membershipStatus) { this.membershipStatus = membershipStatus; }
    public void setDiscountRate(double discountRate) { this.discountRate = discountRate; }

    // Getters
    public String getMembershipStatus() { return membershipStatus; }
    public double getDiscountRate() { return discountRate; }

    public double calcDiscount(double totalAmount) { return totalAmount * discountRate; }

    @Override
    public String toString() {
        return "MemberCustomer{customerName='" + getCustomerName() + "', membershipStatus='" + membershipStatus + "', discountRate=" + discountRate + "}";
    }
}