package main

import (
	"errors"
	"fmt"
	"math/rand"
	"os"
	"sync"
	"time"
)

type Task struct {
	ID   string
	Data string
}

type Result struct {
	TaskID        string
	ProcessedData string
}

type DataProcessingSystem struct {
	taskQueue   chan Task
	results     []Result
	resultsLock sync.Mutex
	workers     int
	outputFile  string
	wg          sync.WaitGroup
	stopChan    chan struct{}
}

func NewDataProcessingSystem(workers int, outputFile string) *DataProcessingSystem {
	rand.Seed(time.Now().UnixNano())
	return &DataProcessingSystem{
		taskQueue:  make(chan Task, 100),
		workers:    workers,
		outputFile: outputFile,
		stopChan:   make(chan struct{}),
	}
}

func (dps *DataProcessingSystem) Start() {
	for i := 0; i < dps.workers; i++ {
		dps.wg.Add(1)
		go dps.worker(i)
	}
}

func (dps *DataProcessingSystem) AddTask(task Task) error {
	select {
	case dps.taskQueue <- task:
		dps.log(fmt.Sprintf("Added task: %s", task.ID))
		return nil
	case <-dps.stopChan:
		return errors.New("system is shutting down")
	}
}

func (dps *DataProcessingSystem) Shutdown() {
	close(dps.stopChan)
	dps.wg.Wait()
	err := dps.saveResultsToFile()
	if err != nil {
		dps.logError(fmt.Sprintf("Error saving results: %v", err))
	}
}

func (dps *DataProcessingSystem) worker(id int) {
	defer dps.wg.Done()
	dps.log(fmt.Sprintf("Worker %d started", id))

	for {
		select {
		case task, ok := <-dps.taskQueue:
			if !ok {
				dps.log(fmt.Sprintf("Worker %d shutting down", id))
				return
			}
			dps.processTask(id, task)
		case <-dps.stopChan:
			dps.log(fmt.Sprintf("Worker %d shutting down", id))
			return
		}
	}
}

func (dps *DataProcessingSystem) processTask(workerID int, task Task) {
	dps.log(fmt.Sprintf("Worker %d processing task %s", workerID, task.ID))

	// Simulate processing delay
	time.Sleep(time.Duration(rand.Intn(900)+100) * time.Millisecond)

	result := Result{
		TaskID:        task.ID,
		ProcessedData: fmt.Sprintf("Processed by worker %d", workerID),
	}

	dps.resultsLock.Lock()
	dps.results = append(dps.results, result)
	dps.resultsLock.Unlock()

	dps.log(fmt.Sprintf("Worker %d completed task %s", workerID, task.ID))
}

func (dps *DataProcessingSystem) saveResultsToFile() error {
	file, err := os.Create(dps.outputFile)
	if err != nil {
		return fmt.Errorf("error creating file: %w", err)
	}
	defer file.Close()

	dps.resultsLock.Lock()
	defer dps.resultsLock.Unlock()

	for _, result := range dps.results {
		_, err := fmt.Fprintf(file, "Task %s: %s\n", result.TaskID, result.ProcessedData)
		if err != nil {
			return fmt.Errorf("error writing to file: %w", err)
		}
	}

	dps.log(fmt.Sprintf("Results saved to %s", dps.outputFile))
	return nil
}

func (dps *DataProcessingSystem) log(message string) {
	fmt.Printf("[System] %s\n", message)
}

func (dps *DataProcessingSystem) logError(errorMsg string) {
	fmt.Printf("[ERROR] %s\n", errorMsg)
}

func main() {
	// Create a new processing system with 4 workers
	dps := NewDataProcessingSystem(4, "results.txt")
	dps.Start()

	// Add sample tasks
	for i := 0; i < 10; i++ {
		task := Task{
			ID:   fmt.Sprintf("T%d", i),
			Data: fmt.Sprintf("Data %d", i),
		}
		if err := dps.AddTask(task); err != nil {
			dps.logError(fmt.Sprintf("Error adding task: %v", err))
		}
	}

	// Allow time for processing
	time.Sleep(5 * time.Second)

	// Shutdown the system
	dps.Shutdown()
}
