# Python: Calculate the sum of an array with syntax errors
def calculate_sum(arr) :
    total = o  # Error: 'o' is a typo, should be '0'
    for num in arr:
        total += num
    return total

numbers = [1, 2, 3, 4, 5]
result = calculate_sum (numbers)
print("Sumin Python :", result)
