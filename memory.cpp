#include <iostream>
using namespace std;

int main() {
    int* ptr = new int(10);
    delete ptr;  // Manually freeing memory
    cout << *ptr; // Error: Dangling pointer
    return 0;
}
