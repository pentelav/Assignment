#include <iostream>
#include <vector>
#include <string>
using namespace std;

// Base Ride class
class Ride {
protected:
    string rideID;
    string pickupLocation;
    string dropoffLocation;
    double distance;

public:
    Ride(string id, string pickup, string dropoff, double dist)
        : rideID(id), pickupLocation(pickup), dropoffLocation(dropoff), distance(dist) {}

    virtual double fare() const {
        return distance * 1.0; // base fare per mile
    }

    virtual void rideDetails() const {
        cout << "Ride ID: " << rideID
             << "\nPickup: " << pickupLocation
             << "\nDropoff: " << dropoffLocation
             << "\nDistance: " << distance << " miles"
             << "\nFare: $" << fare() << "\n";
    }

    virtual ~Ride() {}
};

// Derived class: StandardRide
class StandardRide : public Ride {
public:
    StandardRide(string id, string pickup, string dropoff, double dist)
        : Ride(id, pickup, dropoff, dist) {}

    double fare() const override {
        return distance * 1.5; // standard fare
    }

    void rideDetails() const override {
        cout << "[Standard Ride]\n";
        Ride::rideDetails();
    }
};

// Derived class: PremiumRide
class PremiumRide : public Ride {
public:
    PremiumRide(string id, string pickup, string dropoff, double dist)
        : Ride(id, pickup, dropoff, dist) {}

    double fare() const override {
        return distance * 3.0; // premium fare
    }

    void rideDetails() const override {
        cout << "[Premium Ride]\n";
        Ride::rideDetails();
    }
};

// Driver class
class Driver {
private:
    string driverID;
    string name;
    double rating;
    vector<Ride*> assignedRides;

public:
    Driver(string id, string n, double r) : driverID(id), name(n), rating(r) {}

    void addRide(Ride* ride) {
        assignedRides.push_back(ride);
    }

    void getDriverInfo() const {
        cout << "\nDriver ID: " << driverID
             << "\nName: " << name
             << "\nRating: " << rating
             << "\nCompleted Rides: " << assignedRides.size() << "\n";
    }
};

// Rider class
class Rider {
private:
    string riderID;
    string name;
    vector<Ride*> requestedRides;

public:
    Rider(string id, string n) : riderID(id), name(n) {}

    void requestRide(Ride* ride) {
        requestedRides.push_back(ride);
    }

    void viewRides() const {
        cout << "\nRider ID: " << riderID << "\nName: " << name << "\nRide History:\n";
        for (Ride* ride : requestedRides) {
            ride->rideDetails();
            cout << "-------------------\n";
        }
    }
};

// Main function to demonstrate system
int main() {
    // Create some rides
    Ride* r1 = new StandardRide("R001", "Downtown", "Airport", 10.0);
    Ride* r2 = new PremiumRide("R002", "Mall", "Hotel", 5.0);

    // Create driver and rider
    Driver d1("D100", "Alice", 4.8);
    Rider u1("U200", "Bob");

    // Assign rides
    d1.addRide(r1);
    d1.addRide(r2);

    u1.requestRide(r1);
    u1.requestRide(r2);

    // Display data
    d1.getDriverInfo();
    u1.viewRides();

    // Clean up
    delete r1;
    delete r2;

    return 0;
}
