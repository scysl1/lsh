#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/types.h>
#define NUMBER_OF_JOBS 20
int index = 0;
int NUMBER_OF_JOBS_HAS_BEEN_PRODUCED = 0;
int NUMBER_OF_CONSUMED = 0;
sem_t synchronized;
sem_t delay_consumer;

// print *
void printstar(int index){
	for(int i = 0; i < index; i++){
		printf("*");
	}
	printf("\n");
}

// print the information of the number of produced jobs and consumed jobs
void printIndex(int numofproduced, int numofconsumed){
	printf("Produced = %d, Consumed = %d: ", numofproduced, numofconsumed);
}

void * producer(void * p){
	while(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED < NUMBER_OF_JOBS){
		sem_wait(&synchronized);
		NUMBER_OF_JOBS_HAS_BEEN_PRODUCED++;
		index++;
		printf("Producer, ");
		printIndex(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED, NUMBER_OF_CONSUMED);
		printstar(index);
		if(index==1){
			sem_post(&delay_consumer);
		}
		sem_post(&synchronized);
	}
	return ((void *) 0); 
}

void * consumer(void * p){
	sem_wait(&delay_consumer);
	while(1){	
		sem_wait(&synchronized);
		index--;
		int temp = index;
		NUMBER_OF_CONSUMED++;
		printf("Consumer, ");
		printIndex(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED, NUMBER_OF_CONSUMED);
		printstar(index);
		sem_post(&synchronized);

		// if the number of jobs has reached the expectation and all jobs have been consumed, terminate
		if(index == 0 && NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS){
			return ((void *)0);
		}
		
		// if no jobs could be consumed, then consumer sleeps
		if(temp==0){
			sem_wait(&delay_consumer);
		}
	}
}

int main(int argc, char * argv[]){
	pthread_t tid1, tid2;
	int check1, check2;	
	check1 = sem_init(&synchronized, 0, 1);
	check2 = sem_init(&delay_consumer, 0, 0);

	pthread_create(&tid2, NULL, consumer, NULL);
	pthread_create(&tid1, NULL, producer, NULL);
	pthread_join(tid1, NULL);
	pthread_join(tid2, NULL);

	int a,b;
	sem_getvalue(&synchronized, &a);
	sem_getvalue(&delay_consumer, &b);
	printf("sSync = %d, sDelayConsumer = %d\n", a, b);

}
