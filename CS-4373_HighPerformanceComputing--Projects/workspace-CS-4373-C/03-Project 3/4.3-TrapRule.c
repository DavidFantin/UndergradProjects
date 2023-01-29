#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <semaphore.h>
#include <time.h>

int Thread_Count, Barrier, n;
double a, b, Global_Sum;
long FLAG;
pthread_mutex_t mutex;
sem_t semaphore;
double f(double x){
	return x*x;
}

double Trapezoid(
        double left_endpoint,
        double right_endpoint,
        int trap_count,
        double base_len){

        double estimate, x;
        estimate = (f(left_endpoint) + f(right_endpoint))/2.0;
        for(int i = 1; i <= trap_count - 1; i++){
                x = left_endpoint + i*base_len;
                estimate += f(x);
        }
        return estimate*base_len;
}

void * Busy_Waiting(void * rank){

	long my_rank = (long) rank;
	double h = (b-a)/n;
	int local_n = n/Thread_Count;
	double local_a = a + my_rank * local_n * h;
	double local_b = local_a + local_n * h;

	double local_int = Trapezoid(local_a, local_b, local_n, h);
	while(FLAG != my_rank);
	Global_Sum += local_int;
	Barrier++;
	FLAG = (FLAG + 1) % Thread_Count;
	return 0;
}

void * Mutex(void * rank){
	long my_rank = (long) rank;
        double h = (b-a)/n;
        int local_n = n/Thread_Count;
        double local_a = a + my_rank * local_n * h;
        double local_b = local_a + local_n * h;
        double local_int = Trapezoid(local_a, local_b, local_n, h);
	
	pthread_mutex_lock(&mutex);
	Global_Sum += local_int;
        Barrier++;
	pthread_mutex_unlock(&mutex);
}

void * Semaphore(void * rank){

        long my_rank = (long) rank;
        double h = (b-a)/n;
        int local_n = n/Thread_Count;
        double local_a = a + my_rank * local_n * h;
        double local_b = local_a + local_n * h;
        double local_int = Trapezoid(local_a, local_b, local_n, h);

	sem_wait(&semaphore);
	Global_Sum += local_int;
        Barrier++;
	sem_post(&semaphore);
	
}
int main(int argc, char* argv[]){
	int method;
	clock_t start, end;
	double cpu_time_used;
	sscanf(argv[1],"%d", &Thread_Count);
	sscanf(argv[2],"%d", &method);
	n = 99999999;
	a = 0.0;
	b = 20.0;

	pthread_t* thread_handles = malloc(Thread_Count * sizeof(pthread_t));
	start = clock();
	FLAG = 0;
	if(method == 1){
		printf("Busy Waiting with %d Threads\n", Thread_Count);
		for(int i = 0; i < Thread_Count; i ++){
			pthread_create(&thread_handles[i], NULL, Busy_Waiting,(void*) i);
		}
	}
	else if(method == 2){
		pthread_mutex_init(&mutex, NULL);

		printf("Mutex with %d Threads\n", Thread_Count);
		for(int i = 0; i < Thread_Count; i ++){
			pthread_create(&thread_handles[i], NULL, Mutex,(void*) i);
		}
	}
	else{
		
		sem_init(&semaphore, 0, 1);
                printf("Sempahores with %d Threads\n", Thread_Count);
                for(int i = 0; i < Thread_Count; i ++){
                        pthread_create(&thread_handles[i], NULL, Mutex,(void*) i);
                }
		
	}
	while(Thread_Count != Barrier);
	if(method == 2){
		pthread_mutex_destroy(&mutex);
	}
	else if(method == 3){
		sem_destroy(&semaphore);
	}
	end = clock();
	cpu_time_used =(double) end - start;
	printf("Sum: %f \n", Global_Sum); 
	for(int i = 0; i < Thread_Count; i++){
		pthread_join(thread_handles[i], NULL);
	}
	free(thread_handles);
	end = clock();
	cpu_time_used = (double) end - start;
	printf("Time elapsed: %f\n", cpu_time_used);
}