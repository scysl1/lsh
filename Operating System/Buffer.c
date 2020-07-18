#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include "linkedlist.h"
#include "coursework.h"

int NUMBER_OF_JOBS_HAS_BEEN_PRODUCED = 0;
int NUMBER_OF_CONSUMED = 0;
int index = 0;
char * a = "*";
struct element * fcfsHead = NULL;
struct element * fcfsTail = NULL;
sem_t synchronized;
sem_t sSleep;
sem_t sFull;

// this function is used to print the number of produced jobs and consumed jobs
void printIndex(int numofproduced, int numofconsumed){
	printf("Produced = %d, Consumed = %d: ", numofproduced, numofconsumed);
}

// print * stored in the linked list
void print_ele_of_buffer(struct element ** bufferHead){

	struct element * tempbuffer = (*bufferHead);

	while(tempbuffer){
		printf("%s", tempbuffer->pData);
		tempbuffer = tempbuffer->pNext;
	}
	printf("\n");

	free(tempbuffer);
}

void * producer(void * p){
    
	while(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED < NUMBER_OF_JOBS){

		sem_wait(&synchronized);
		NUMBER_OF_JOBS_HAS_BEEN_PRODUCED++;
		index++;

		// temp is used to avoid race condition
		int temp = index;
		printf("Producer 1, ");
		printIndex(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED, NUMBER_OF_CONSUMED);
		addLast(a, &fcfsHead, &fcfsTail);
		print_ele_of_buffer(&fcfsHead);
		sem_post(&synchronized);

		// if the current jobs is greater than or equal to the buffer size, then producer go to sleep
		if(temp >= MAX_BUFFER_SIZE){
			sem_wait(&sSleep);
		}

		sem_post(&sFull);
	
	}
	return ((void *) 0); 
}

void * consumer(void * p){
	while(1){	

		sem_wait(&sFull);		
		sem_wait(&synchronized);

		// temp1 and temp2 is used to avoid race condition
		int temp1 = index;
		index--;
		int temp2 = index;
		removeFirst(&fcfsHead, &fcfsTail);
		NUMBER_OF_CONSUMED++;
		printf("Consumer 1, ");
		printIndex(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED, NUMBER_OF_CONSUMED);
		print_ele_of_buffer(&fcfsHead);
		sem_post(&synchronized);

		// if all jobs have beed consumed and the number of produced jobs has reached the expectation, terminate
		if(temp2 == 0 && NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS){
			return ((void *)0);
		}

		// if the number of current jobs is from 10 to 9 then producer could be waken up
		if((temp1 == MAX_BUFFER_SIZE) && (temp2 == MAX_BUFFER_SIZE - 1)){
			sem_post(&sSleep);

		}
		
	}
}
int main(int argc, char *agrv[]){

    pthread_t tid1, tid2;
    sem_init(&synchronized, 0, 1);
    sem_init(&sSleep, 0, 0);
    sem_init(&sFull, 0, 0);
    pthread_create(&tid2, NULL, consumer, NULL);
	pthread_create(&tid1, NULL, producer, NULL);
	pthread_join(tid1, NULL);
	pthread_join(tid2, NULL);


}
