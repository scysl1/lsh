#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/time.h>
#include "linkedlist.h"
#include "coursework.h"
#include <unistd.h>

int flag[16] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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
struct element * runHead = NULL;
struct element * runTail = NULL;

struct boundedbuffer{
	struct element* bbHead;
	struct element* bbTail;
};
/**
 * This function will return the buffer which has highest pririty currently. 
 **/
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
/**
 * This function will return true if there is nothing in the buffer
 **/
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

void addProcess(){
	struct process * newprocess = generateProcess();
    int p = newprocess->iPriority;
	
	//Check if the new process is first come and first serve
	//If it is, compare its priority with old processes' priority.
	if(p > 15){
		addLast(newprocess, &(arraylist[p]->bbHead), &(arraylist[p]->bbTail));
	}
	if(p >= 0 && p <= 15){
        int fcfsmax = -1;
        int fcfsmaxID = 100;
        struct element * tempArrayfindMax = runHead;
        while(tempArrayfindMax != NULL){
        
            struct process * tempProcessfindMax = tempArrayfindMax->pData;
            int tempfindMaxPriority = tempProcessfindMax->iPriority;
            if((tempfindMaxPriority >= 0 && tempfindMaxPriority <= 15) && (tempfindMaxPriority > fcfsmax)){
                fcfsmax = tempfindMaxPriority;
                fcfsmaxID = tempProcessfindMax->iProcessId;
            }
            tempArrayfindMax = tempArrayfindMax->pNext;
			
        }
		if(fcfsmax < p){
			addLast(newprocess, &(arraylist[p]->bbHead), &(arraylist[p]->bbTail));
		}
	//If the new process comes out, add to the running list and stop the process with the lowest priority
        struct element * tempArrayfindMax2 = runHead;
		if(fcfsmax > p){
			int flag = 0;
			while(tempArrayfindMax2 != NULL){
				struct process * tempProcessfindMax = tempArrayfindMax2->pData;

				if(tempProcessfindMax->iProcessId == fcfsmaxID){
					preemptJob(tempProcessfindMax);
					/**
					 * Remove the process with lowest priority
					 * */
                    int temp = fcfsmaxID;
					
                    if(runHead == NULL){
                    }
                    if(runHead != NULL){
                        struct element * testHead = runHead;
                        struct process * process1 = testHead->pData;
                        struct element * tempHead = testHead;
                        if(process1->iProcessId==temp){
                            removeFirst(&runHead, &runTail);
                        }else{
                            struct element * previousHead;
                            previousHead = tempHead;
                            tempHead = tempHead->pNext;
                            while(tempHead != NULL){
                                struct process * process2 = tempHead->pData;
                                if(process2->iProcessId == temp){
                                    previousHead->pNext = tempHead->pNext;
                                    tempHead->pNext = NULL;
                                    break;
                                }
                                previousHead = tempHead;
                                tempHead = tempHead->pNext;
                            }
                        }
                    }
					addLast(newprocess, &(arraylist[p]->bbHead), &(arraylist[p]->bbTail));
					sem_post(&sFull);
					addLast(tempProcessfindMax, &(arraylist[fcfsmax]->bbHead), &(arraylist[fcfsmax]->bbTail));
					
					index++;
					printf("Pre-empty job: Pre-empted Process Id = %d, Pre-empted Priority %d, New Process Id %d, New Priority %d\n",fcfsmaxID, fcfsmax, newprocess->iProcessId,p);
					
					break;
				}
				tempArrayfindMax2 = tempArrayfindMax2->pNext;
			}
		}
	
	
    }
    /**
	 * Print the info of the process
	 * */
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
		// add process in to buffer
		addProcess(); 
		
		sem_post(&synchronized);

        sem_post(&sFull);

		if(temp >= MAX_BUFFER_SIZE){
			sem_wait(&sSleep);
		}

		
	
	}
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
			//printf("Consumer%d has exit\n", *((int *)p));
			return ((void *)0);
		}
		sem_wait(&synchronized);
		int currentID;
		sem_post(&synchronized);
		sem_wait(&synchronized);
		
		int temp1 = index;
		index--;
		int temp2 = index;
		sleep(0.3);
		struct boundedbuffer * tempArray = returnFirstBuffer();
	
		struct process * tempProcess = removeFirst(&(tempArray->bbHead), &(tempArray->bbTail));
		
		addLast(tempProcess, &runHead, &runTail);
		
	
        currentID = tempProcess -> iProcessId;
		sem_post(&synchronized);

        runJob(tempProcess,startTime,endTime);

        sem_wait(&synchronized);


		/**
		 * Remove the process with lowest priority
		 * */
        int temp3 = currentID;
        if(runHead != NULL){
            struct element * testHead = runHead;
            struct process * process3 = testHead->pData;
            struct element * tempHead = testHead;
            if(process3->iProcessId==temp3){
                removeFirst(&runHead, &runTail);
            }else{
                struct element * previousHead;
                previousHead = tempHead;
                tempHead = tempHead->pNext;
                while(tempHead != NULL){
                    struct process * process4 = tempHead->pData;
                    if(process4->iProcessId == temp3){
                        previousHead->pNext = tempHead->pNext;
                        tempHead->pNext = NULL;
                        break;
                    }
                    previousHead = tempHead;
                    tempHead = tempHead->pNext;
                }
            }
        }

		if(!((tempProcess->iPreviousBurstTime==0)&&(tempProcess->iRemainingBurstTime==0))){
			tempProcess=processJob(*((int *)p), tempProcess, *startTime, *endTime);
		}
		
		sem_post(&synchronized);
		

		if(tempProcess != NULL){
            if(tempProcess->iPriority >=16){
                sem_wait(&synchronized);
                addLast(tempProcess,&(tempArray->bbHead),&(tempArray->bbTail));
                sem_post(&synchronized);
            }    
			
			sem_post(&sFull);
			
		}
		// if the number of current jobs is from 10 to 9 then producer could be waken up
		if((temp1 == MAX_BUFFER_SIZE) && (temp2 == MAX_BUFFER_SIZE - 1)){
			sem_post(&sSleep);
		}

		// the first exited consumer need to wake up other sleeping consumer
		if(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS && isArrayEmpty()){
			sem_post(&sFull); 
			free(startTime);
			free(endTime);
			//printf("Consumer%d has exit\n", *((int *)p));
			return ((void *)0);
		}


		
	}
	free(startTime);
	free(endTime);
	//printf("Consumer%d has exit\n", *((int *)p));
	return ((void *)0);
}

/**
 * This booster function will check if there is RR job waiting for a long time and compare its waiting time with pre-defined interval
 * If it is more waiting then boost its priority to 16
 * */
void * booster(void * p){
	while(1){
		for(int i=17; i<32; i++){
			sem_wait(&synchronized);
			struct element * tempHead = arraylist[i]->bbHead;
			if (tempHead != NULL){
				
				struct process * tempProcess = tempHead->pData;
				struct timeval * nowTime = malloc(sizeof(struct timeval));
				gettimeofday(nowTime, NULL);
				long int waittime = getDifferenceInMilliSeconds(tempProcess->oTimeCreated, *nowTime);
				if(waittime >= BOOST_INTERVAL){
					
					struct process * tempProcess2 = removeFirst(&(arraylist[i]->bbHead), &(arraylist[i]->bbTail));
					addLast(tempProcess2, &(arraylist[16]->bbHead), &(arraylist[16]->bbTail));
					printf("Boost priority: Process Id = %d, Priority = %d, New Priority = 16\n", tempProcess2->iProcessId, tempProcess2->iPriority);
					
				}
				
				free(nowTime);
			}
			sem_post(&synchronized);
			
		}

		if(NUMBER_OF_JOBS_HAS_BEEN_PRODUCED == NUMBER_OF_JOBS && isArrayEmpty()){
			//printf("Booster has exit\n");
			return ((void *)0);
		}
	}
}




int main(int argc, char *agrv[]){

    pthread_t tid1;
	pthread_t tid_b;
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
    
    int consumer_id;struct timeval * startTime = malloc(sizeof(struct timeval));
    for(consumer_id = 0; consumer_id < NUMBER_OF_CONSUMERS; consumer_id++){
		data[consumer_id] = consumer_id;
		pthread_create(&consumers[consumer_id], NULL, consumer, (void *)&data[consumer_id]);
	}
    
    
	pthread_create(&tid1, NULL, producer, NULL);
	pthread_create(&tid_b, NULL, booster, NULL);
	pthread_join(tid1, NULL);
	pthread_join(tid_b, NULL);
	
	for(int i = 0; i < NUMBER_OF_CONSUMERS; i++){
		pthread_join(consumers[i], NULL);
	}
	for(int i =0; i<MAX_PRIORITY;i++){
		free(arraylist[i]);
	}
	

	printf("Average Response Time = %.6f\nAverage Turn Around Time = %.6f\n", (dAverageResponseTime/(float)NUMBER_OF_JOBS) , (dAverageTurnAroundTime)/(float)NUMBER_OF_JOBS);

}
