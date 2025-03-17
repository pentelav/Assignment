fn main() {
    let s1 = String::from("Hello");
    let s2 = s1;  // Error: Ownership moved
    println!("{}", s1); // Will not compile
}
