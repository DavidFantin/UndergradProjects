#include<stdio.h>
#include<stdlib.h>
#include<omp.h>
#include<string.h>

char * Queue;
int Q_size;
int producers;
int consumers;
int stop;

void producer(){
	int rank = omp_get_thread_num();
	char file[12];
	snprintf(file, 12, "Thrd%d.txt", rank);

	FILE * fp;
	char * line = NULL;
	size_t len = 0;
	ssize_t read;
	
	fp = fopen(file, "r");
	
	if(fp == NULL){ 
		exit(EXIT_FAILURE);
	}
	
	while((read = getline(&line, &len, fp)) != -1){
		char newLine [len + 1];
		strcpy(newLine, " ");
		strcat(newLine, line);
		Queue = realloc(Queue, Q_size + len);
		Q_size = Q_size + len;
		strcat(Queue, newLine);
	}
	stop += 1;
}

void consumer(){
	int rank = omp_get_thread_num();
	while(*Queue != '\0' || stop < producers){
		if(*Queue == ''){
			Queue += 1;
			printf("Thread %d: ", rank);
			printf("\n");
		}
	}
}

int main(int argc, char* argv[]){

	sscanf(argv[1], "%d", &producers);
	sscanf(argv[2], "%d", &consumers);
	stop = 0;
	Q_size = 0;
#	pragma omp parallel num_threads(producers)
	producer();
#	pragma omp parallel num_threads(consumers)
	consumer();
	return 0;
}

