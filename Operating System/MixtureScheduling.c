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
int dAverageResponseTime = 0;
int dAverageTurnAroundTime = 0;
sem_t synchronized; //critical section
sem_t synchronizedgetProcess; //critical section
sem_t sSleep; //binary semaphore
sem_t sFull; //counting semaphore
struct boundedbuffer *arraylist[MAX_PRIORITY];

struct boundedbuffer{
	struct element* bbHead;
	struct element* bbTail;
};

// return the first buffer in arraylist, which means find the first buffer that has jobs 
struct boundedbuffer * returnFirstBuffer(){
    struct boundedbuffer *temp;
	temp = NULL;
    for(int i = 0; i < MAX_PRIORITY; i++){
        if((arraylist[i]->bbHead)!=NULL){
            temp = arraylist[i];
			return temp;
        }
    }
    return temp;
}

// used to check if the arraylist is empty or not
int isArrayEmpty(){
    int isempty = 1;

    for(int i = 0; i < MAX_PRIORITY; i++){
        if((arraylist[i]->bbHead)!=NULL){
            isempty = 0;
        }
    }
    return isempty;
}

struct process * processJob(int iConsumerId, struct process * pProcess, struct timeval oStartTime, struct timeval oEndTime)
{
	int iResponseTime;
	int iTurnAroundTime;
	
	if(pProcess->iPreviousBurstTime == pProcess->iInitialBurstTime && pProcess->iRemainingBurstTime > 0)
	{
		iResponseTime = getDifferenceInMilliSeconds(pProcess->oTimeCreated, oStartTime);	
		dAverageResponseTime += iResponseTime;
		printf("Consumer %d, Process Id = %d (%s), Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Response Time = %d\n", iConsumerId, pProcess->iProcessId, pProcess->iPriority < MAX_PRIORITY / 2	 ? "FCFS" : "RR",pProcess->iPriority, pProcess->iPreviousBurstTime, pProcess->iRemainingBurstTime, iResponseTime);
		return pProcess;
	} else if(pProcess->iPreviousBurstTime == pProcess->iInitialBurstTime && pProcess->iRemainingBurstTime == 0)
	{
		iResponseTime = getDifferenceInMilliSeconds(pProcess->oTimeCreated, oStartTime);	
		dAverageResponseTime += iResponseTime;
		iTurnAroundTime = getDifferenceInMilliSeconds(pProcess->oTimeCreated, oEndTime);
		dAverageTurnAroundTime += iTurnAroundTime;
		printf("Consumer %d, Process Id = %d (%s), Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Response Time = %d, Turnaround Time = %d\n", iConsumerId, pProcess->iProcessId, pProcess->iPriority < MAX_PRIORITY / 2 ? "FCFS" : "RR", pProcess->iPriority, pProcess->iPreviousBurstTime, pProcess->iRemainingBurstTime, iResponseTime, iTurnAroundTime);
		free(pProcess);
		return NULL;
	} else if(pProcess->iPreviousBurstTime != pProcess->iInitialBurstTime && pProcess->iRemainingBurstTime > 0)
	{
		printf("Consumer %d, Process Id = %d (%s), Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d\n", iConsumerId, pProcess->iProcessId, pProcess->iPriority < MAX_PRIORITY / 2 ? "FCFS" : "RR", pProcess->iPriority, pProcess->iPreviousBurstTime, pProcess->iRemainingBurstTime);
		return pProcess;
	} else if(pProcess->iPreviousBurstTime != pProcess->iInitialBurstTime && pProcess->iRemainingBurstTime == 0)
	{
		iTurnAroundTime = getDifferenceInMilliSeconds(pProcess->oTimeCreated, oEndTime);
		dAverageTurnAroundTime += iTurnAroundTime;
		printf("Consumer %d, Process Id = %d (%s), Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Turnaround Time = %d\n", iConsumerId, pProcess->iProcessId, pProcess->iPriority < MAX_PRIORITY / 2 ? "FCFS" : "RR", pProcess->iPriority, pProcess->iPreviousBurstTime, pProcess->iRemainingBurstTime, iTurnAroundTime);
		free(pProcess);
		return NULL;
	}
}

// this function is used to print the number of produced jobs and consumed jobs
void printIndex(int numofproduced, int numofconsumed){
	printf("Produced = %d, Consumed = %d: \n", numofproduced, numofconsumed);
}

// add process according on their priorities and put them to the corresponding priority buffer
void addProcess(){
	struct process * newprocess = generateProcess();
    int p = newprocess->iPriority;
	addLast(newprocess, &(arraylist[p]->bbHead), &(arraylist[p]->bbTail));
	if (p < MAX_PRIORITY / 2){
		printf("Process Id = %d (FCFS), Priority = %d, Initial Burst Time = %d\n",
            newprocess->iProcessId, newprocess->iPriority, newprocess->iInitialBurstTime);
	}else{
		printf("Process Id = %d (RR), Priority = %d, Initial Burst Time = %d\n",
            newprocess->iProcessId, newprocess->iPriority, newprocess->iInitialBurstTime);
	}
	
}


void * producer(void * p){
    
	while(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED < NUMBER_OF_JOBS){

		sem_wait(&synchronized);
		NUMBER_OF_JOBS_HAS_BEEN_PRODUCED++;
		index++;

		int temp = index;
		printf("Producer 0, ");
		addProcess(); // add process in to buffer
		
		sem_post(&synchronized);

        sem_post(&sFull);

		if(temp >= MAX_BUFFER_SIZE){
			sem_wait(&sSleep);
		}

		
	
	}
	//printf("Producer has exit");
	return ((void *) 0); 
}

void * consumer(void * p){
	
    struct timeval * startTime = malloc(sizeof(struct timeval));
	struct timeval * endTime = malloc(sizeof(struct timeval));

	while(!(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS && isArrayEmpty()) ){	
		int restime = 0, turntime = 0;

		sem_wait(&sFull); // last sleep local temp
		if(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS && isArrayEmpty()){
			free(startTime);
			free(endTime);
			sem_post(&sFull);
			return ((void *)0);
		}
		
		
		sem_wait(&synchronized);
		int temp1 = index;
		index--;
		int temp2 = index;
		struct boundedbuffer * tempArray = returnFirstBuffer();
		struct process * tempProcess = removeFirst(&(tempArray->bbHead), &(tempArray->bbTail));
		
		sem_post(&synchronized);

        runJob(tempProcess,startTime,endTime);

        sem_wait(&synchronized);
		tempProcess=processJob(*((int *)p), tempProcess, *startTime, *endTime);
		sem_post(&synchronized);
		

		if(tempProcess != NULL){

			sem_wait(&synchronized);
			addLast(tempProcess,&(tempArray->bbHead),&(tempArray->bbTail));
			sem_post(&synchronized);
			sem_post(&sFull);
			
		}
		// if the number of current jobs is from 10 to 9 then producer could be waken up
		if((temp1 == MAX_BUFFER_SIZE) && (temp2 == MAX_BUFFER_SIZE - 1)){
			sem_post(&sSleep);
		}

		if(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS && isArrayEmpty()){
			sem_post(&sFull); // the first exited consumer need to wake up other sleeping consumer
			free(startTime);
			free(endTime);
			
			return ((void *)0);
		}


		
	}
	free(startTime);
	free(endTime);
	return ((void *)0);
}
int main(int argc, char *agrv[]){

    pthread_t tid1;
    pthread_t consumers[NUMBER_OF_CONSUMERS];
    int data[NUMBER_OF_CONSUMERS];

    struct boundedbuffer * temp;
    for(int i = 0;i<MAX_PRIORITY;i++){
        temp = (struct boundedbuffer *)malloc(sizeof(struct boundedbuffer));
        temp->bbHead = NULL;
        temp->bbTail = NULL;
        arraylist[i] = temp;
    }
    
    sem_init(&synchronized, 0, 1);
	sem_init(&synchronizedgetProcess, 0, 1);
    sem_init(&sSleep, 0, 0);
    sem_init(&sFull, 0, 0);
    
    int consumer_id;
    for(consumer_id = 0; consumer_id < NUMBER_OF_CONSUMERS; consumer_id++){
		data[consumer_id] = consumer_id;
		pthread_create(&consumers[consumer_id], NULL, consumer, (void *)&data[consumer_id]);
	}
    
    
	pthread_create(&tid1, NULL, producer, NULL);
	pthread_join(tid1, NULL);
	
	
	for(int i = 0; i < NUMBER_OF_CONSUMERS; i++){
		pthread_join(consumers[i], NULL);
	}
	for(int i =0; i<MAX_PRIORITY;i++){
		free(arraylist[i]);
	}
	

	printf("Average Response Time = %.6f\nAverage Turn Around Time = %.6f\n", (dAverageResponseTime/(float)NUMBER_OF_JOBS) , (dAverageTurnAroundTime)/(float)NUMBER_OF_JOBS);

}
