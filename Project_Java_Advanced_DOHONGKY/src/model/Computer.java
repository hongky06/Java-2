package model;

public class Computer {
    private int id;
    private String pcName;
    private String area;
    private double pricePerHour;
    private String status;

    public Computer() {}

    public Computer(int id, String pcName, String area, double pricePerHour, String status) {
        this.id = id;
        this.pcName = pcName;
        this.area = area;
        this.pricePerHour = pricePerHour;
        this.status = status;
    }

    public int getId() { return id; }
    public String getPcName() { return pcName; }
    public String getArea() { return area; }
    public double getPricePerHour() { return pricePerHour; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setPcName(String pcName) { this.pcName = pcName; }
    public void setArea(String area) { this.area = area; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
    public void setStatus(String status) { this.status = status; }
}