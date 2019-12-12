package co.edu.unal.dinnerqradmin.clases;

public class Bill {

    String id;
    double total;

    public Bill(String id, double total) {
        this.id = id;
        this.total = total;
    }


    public String getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
