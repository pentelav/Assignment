# statistics.py

from collections import Counter

class StatisticsCalculator:
    def __init__(self, data):
        self.data = sorted(data)

    def mean(self):
        return sum(self.data) / len(self.data)

    def median(self):
        n = len(self.data)
        mid = n // 2
        if n % 2 == 0:
            return (self.data[mid - 1] + self.data[mid]) / 2
        else:
            return self.data[mid]

    def mode(self):
        counts = Counter(self.data)
        max_freq = max(counts.values())
        return [k for k, v in counts.items() if v == max_freq]

if __name__ == "__main__":
    data = [1, 2, 2, 3, 4, 4, 5]
    calc = StatisticsCalculator(data)

    print("Mean:", round(calc.mean(), 2))
    print("Median:", round(calc.median(), 2))
    print("Mode:", calc.mode())
