#include<stdio.h>
#include<stdlib.h>
#include<omp.h>
#include<string.h>

char * Queue;
int Q_size;
int Producers;
int Consumers;
int Stop;

void producer(){
	int rank = omp_get_thread_num();
	char file[12];
	snprintf(file, 12, "Thread%d.txt", rank);

	FILE * fp;
	char * line = NULL;
	size_t len = 0;
	ssize_t read;
	
	fp = fopen(file, "r");
	
	if(fp == NULL){ exit(EXIT_FAILURE);}
	
	while((read = getline(&line, &len, fp)) != -1){
		char newLine [len + 1];
				
		
		strcpy(newLine, " ");
		strcat(newLine, line);
#		pragma omp critical
		Queue = realloc(Queue, Q_size + len);
		Q_size = Q_size + len;
		strcat(Queue, newLine);
		
	}
#	pragma omp critical
	Stop += 1;

}

void consumer(){
	int rank = omp_get_thread_num();
	while(*Queue != '\0' || Stop < Producers){
		#pragma omp critical
		if(*Queue == ' '){
			Queue += 1;
			printf("Thread %d says: ", rank);
			while(*Queue != ' ' && *Queue != '\0'){
				printf("%c", *Queue);
				Q_size -= 1;
				Queue += 1;
			}
			printf("\n");
			
		}	
	}	
}

int main(int argc, char* argv[]){

	sscanf(argv[1], "%d", &Producers);
	sscanf(argv[2], "%d", &Consumers);
	Stop = 0;
	Q_size = 0;
#	pragma omp parallel num_threads(Producers)
	producer();
#	pragma omp parallel num_threads(Consumers)
	consumer();
	

	return 1;
}

