#include <stdio.h>
#include <mpi.h>
#include <time.h>
#include <stdlib.h>

int main()
{
	char *eptr;	
	int comm_sz;
	int my_rank;
	
	MPI_Init(NULL,NULL);
	MPI_Comm_size(MPI_COMM_WORLD, &comm_sz);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	
	srand(my_rank + 256);
	
	long long int local_number_in_circle = 0;
	long long int numTosses = 100000000;
	long long int numIter = numTosses/comm_sz;
	long long int numEx = numTosses % comm_sz;
	
	if(my_rank < numEx)
	{
		numIter += 1;
	}
	
	long long int global_number_in_circle = 0;
	MPI_Barrier(MPI_COMM_WORLD);
	clock_t start = clock();

	for(long long int toss = 0; toss< numIter; toss++)
	{
		double x = (double) rand()/RAND_MAX*2.0-1.0;
        double y = (double) rand()/RAND_MAX*2.0-1.0;
        double distance_squared = x*x + y*y;

        if(distance_squared <= 1)
		{
			local_number_in_circle += 1;
		}
	}

	MPI_Reduce(&local_number_in_circle, &global_number_in_circle, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
	
	MPI_Barrier(MPI_COMM_WORLD);
	clock_t end = clock();
	if(my_rank == 0)
	{
		double piEst = 4 * global_number_in_circle/((double) numTosses);
		printf("The estimated pi is %f and the number of tosses is %f\n", piEst, (double) numTosses);
		printf("The program took %d clock cycles to complete\n", end - start);
	}
	MPI_Finalize();
	return 0;
}