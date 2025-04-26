// statistics.c
#include <stdio.h>
#include <stdlib.h>

int compare(const void *a, const void *b) {
    return (*(int *)a - *(int *)b);
}

double calculate_mean(int arr[], int size) {
    int sum = 0;
    for (int i = 0; i < size; i++) sum += arr[i];
    return (double)sum / size;
}

double calculate_median(int arr[], int size) {
    qsort(arr, size, sizeof(int), compare);
    if (size % 2 == 0)
        return (arr[size / 2 - 1] + arr[size / 2]) / 2.0;
    else
        return arr[size / 2];
}

void calculate_mode(int arr[], int size) {
    int max_count = 0;
    int mode[size], mode_count = 0;
    
    for (int i = 0; i < size; i++) {
        int count = 0;
        for (int j = 0; j < size; j++)
            if (arr[j] == arr[i]) count++;
        
        if (count > max_count) {
            max_count = count;
            mode[0] = arr[i];
            mode_count = 1;
        } else if (count == max_count && arr[i] != mode[mode_count - 1]) {
            mode[mode_count++] = arr[i];
        }
    }

    printf("Mode: ");
    for (int i = 0; i < mode_count; i++) {
        printf("%d ", mode[i]);
    }
    printf("\n");
}

int main() {
    int data[] = {1, 2, 2, 3, 4, 4, 5};
    int size = sizeof(data) / sizeof(data[0]);

    printf("Mean: %.2f\n", calculate_mean(data, size));
    printf("Median: %.2f\n", calculate_median(data, size));
    calculate_mode(data, size);

    return 0;
}
