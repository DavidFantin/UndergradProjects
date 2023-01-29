//Note: I ran this on the linux VM and not hammer
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <inttypes.h>
int main(){
	long long int number_in_circle = 0;
	long long int number_of_tosses = 0;
	printf("Enter the number of tosses: ");
	scanf("%d", &number_of_tosses);
	
	clock_t start_time = clock();
	for(int toss = 0; toss<number_of_tosses; toss++){
		double x = (double) rand()/RAND_MAX*2.0-1.0;
		double y = (double) rand()/RAND_MAX*2.0-1.0;
		double distance_squared = x*x + y*y;
		if(distance_squared <= 1) number_in_circle +=1;
	}
	double pi_estimate = 4* number_in_circle/((double) number_of_tosses);
	clock_t end_time = clock();

	printf("pi estimate: %f\n", pi_estimate);
	printf("clock ticks: %d\n",end_time - start_time);
}
