Object-Oriented Principles Summary
1. Encapsulation
•	C++: Limits access using private and restricted members. Safely exposing behavior is accomplished using methods like addRide() and rideDetails().
•	Smalltalk: Maintaining data security, follows convention by accessing instance variables only via methods (e.g., addRide, details).
2. Inheritance
•	C++: Inheriting from Ride, StandardRide and PremiumRide rework shared features and supersede fare computation.
•	Smalltalk: StandardRide and PremiumRide derive from Ride, adjusting fare and reusing information.
3. Polymorphism
•	C++: Uses virtual functions for fare() and rideDetails(); calls rely on real object type at runtime.
•	Smalltalk: Naturally polymorphic; object class determines dynamically distributed method calls like fare and details.
4. Abstraction
•	C++: Users of the Ride interface engage without regard to underlying fare logic.
•	Smalltalk: The basic Ride class hides internal workings behind fare and details, hence defining a shared interface.
Conclusion: Both languages show all four OOP ideas quite well:
•	C++ provides structure with explicit control—access specifiers, virtual methods.
•	For full OOP design and adaptability, Smalltalk depends on simplicity and dynamic messaging.
 ![image](https://github.com/user-attachments/assets/91b86f97-21c2-4714-a63b-daf127f8079e)
 ![image](https://github.com/user-attachments/assets/c91621c6-5292-487b-b9b3-88b802540ae0)
 ![image](https://github.com/user-attachments/assets/73a927bc-32d6-47a2-b48e-4fbf64cf8b44)
 ![image](https://github.com/user-attachments/assets/c0e78eb3-dc52-4563-a082-0db87b6b05a3)
 ![image](https://github.com/user-attachments/assets/00ecd550-4765-4b01-ac8b-c93fc5dcb379)
 ![image](https://github.com/user-attachments/assets/aa140744-e5a8-4824-ab82-c31284a8fd52)
 ![image](https://github.com/user-attachments/assets/b2408676-1f24-42ea-9a40-e17f102b74fb)
 
 Output
![image](https://github.com/user-attachments/assets/09c11e25-ff56-4678-b872-dddc5b7ac63d)

Smalltalk

![image](https://github.com/user-attachments/assets/1ee0d21f-1350-4968-8fe3-a8b0c8be6c09)
![image](https://github.com/user-attachments/assets/63097d9d-5a7c-45c9-be1f-2abbf2db140d)
![image](https://github.com/user-attachments/assets/2399f4e5-92f9-4197-b2f7-a2269bfc654f)
![image](https://github.com/user-attachments/assets/4b5f89f2-b38a-44c5-8938-2640f48c2ee6)

Output

![image](https://github.com/user-attachments/assets/8b966647-374e-4603-99c0-87fa0783aadb)


References
Brown, K. G. (2020, September 22). Design Patterns, Smalltalk, and the Lost Art of Reading Code. Medium; The Startup. https://medium.com/swlh/design-patterns-smalltalk-and-the-lost-art-of-reading-code-1727d93fd7fa
Eng, R. K. (2022, July 13). Comparison of Three Major Programming Languages with Smalltalk. Medium. https://richardeng.medium.com/comparison-of-three-major-programming-languages-with-smalltalk-ba9061008921





