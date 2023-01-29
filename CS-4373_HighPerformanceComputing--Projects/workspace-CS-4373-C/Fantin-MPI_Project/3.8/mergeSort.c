#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
#include <time.h>

void print(int arr[], int size)
{
    for(int i = 0; i < size; i++)
	{
        printf("%d ", arr[i]);
    }
    printf("\n");
}

void MergeLocal(int arr[], int l, int m, int r){
	int i; int j; int k;
	int n1 = m-l + 1;
	int n2 = r - m;
	int L[n1], R[n2];
	
	for(i = 0; i < n1; i++)
	{
		L[i] = arr[l+i];
	}
	
	for(i = 0; i <n2; i ++)
	{
		R[i] = arr[m + 1 + i];
	}
	
	k = l;
	i = 0;
	j = 0;
	
	while (i < n1 && j < n2)
	{
		if(L[i] <= R[j])
		{
			arr[k] = L[i];
			i++;
		}
		else
		{
			arr[k] = R[j];
			j++;
		}
		k++;
	}
	
	while(i<n1)
	{
		arr[k] = L[i];
		i++; k++;
	}
	
	while(j < n2)
	{
		arr[k] = R[j];
		j++; k++;
	}
}
void MSLocal(int arr[], int l, int r)
{
	if(l<r)
	{
		int m = l+(r-l)/2;
		MSLocal(arr,l,m);
		MSLocal(arr,m + 1, r);
		MergeLocal(arr, l, m, r);
	}
}

int main(int args, int * arg[])
{
	int comm_sz;
	int my_rank;
	
	MPI_Init(NULL, NULL);
	MPI_Comm_size(MPI_COMM_WORLD, &comm_sz);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	clock_t start_time = clock();
	long long int n = 64;
	long long int size = n/comm_sz;
	int local_array[size];
	srand(my_rank + 256);
	
	for(int i = 0; i<size; i ++)
	{
		local_array[i] = rand();
	}
	MSLocal(local_array, 0, size - 1);
	int recv[size];
	if(my_rank == 0)
	{	
		printf("Core 0:  ");	
		print(local_array, size);
		for(int i = 1; i < comm_sz; i++)
		{
			MPI_Recv(&recv, size, MPI_INT, i, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			printf("Core %d:  ",i);
			print(recv, size);
		}
	}
	else
	{
		MPI_Send(&local_array, size, MPI_INT, 0, 1, MPI_COMM_WORLD);
	}
	MPI_Barrier(MPI_COMM_WORLD);

	int newArray[size];
	for(int i = 0; i < size; i ++)
	{
		newArray[i] = local_array[i];
	}
	for(int c = 2; c <= comm_sz; c = c*2)
	{
		int recvd[size];
		if (my_rank % c == 0)
		{
			int tmpArray[size*2];
			MPI_Recv(&recvd, size, MPI_INT, my_rank+(c/2), 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
				int i=0;
				int j=0;
				int index = 0;
				
				while(j < size && i < size)
				{
					if(newArray[i] < recv[j])
					{
						tmpArray[index] = newArray[i];
						i++;
					}
					else
					{
						tmpArray[index] = recvd[j];
						j++;
					}
					index++;
				}
				while(j < size)
				{
					tmpArray[index] = recvd[j];
					j++;
					index++;
				}
				while(i<size)
				{
					tmpArray[index] = newArray[i];
					i++;
					index++;
				}
				size = size * 2;
			newArray[size];
			for(int p = 0; p < size; p ++)
			{
				newArray[p] = tmpArray[p];
			}
		}
		else
		{
			if(my_rank % c == c/2)
			{
				MPI_Send(&newArray, size, MPI_INT, my_rank-(my_rank%c), 1, MPI_COMM_WORLD);
			}
		}
		MPI_Barrier(MPI_COMM_WORLD);
	}
	if(my_rank == 0){
		clock_t end_time = clock();
		printf("Final sorted list:  ");
		print(newArray, size);
		printf("The program took %d clock cycles to complete\n", end_time - start_time);
	}
	MPI_Finalize();
	return 0;
}

