// Class 1: Defines the blueprint for a Car object
class Car {
    private String model;
    private int year;

    // Constructor to initialize the object
    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }

    // Getters
    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
}

// Class 2: Contains the main entry point and controls the program
public class Main {
    public static void main(String[] args) {
        // Create an instance of the Car class
        Car myCar = new Car("Electric Sedan", 2026);

        // Pass the Car object to another method in this class
        displayVehicleDetails(myCar);
    }

    // Method that accepts a Car object as a parameter
    public static void displayVehicleDetails(Car car) {
        System.out.println("Vehicle Model: " + car.getModel());
        System.out.println("Vehicle Year: " + car.getYear());
    }
}
