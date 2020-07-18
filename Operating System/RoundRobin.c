#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include "linkedlist.h"
#include "coursework.h"

// generate processes and store
void generateP(struct element ** pHead, struct element ** pTail){
    printf("PROCESS LIST:\n");
    for(int i = 0; i < NUMBER_OF_JOBS; i++){
        struct process * newprocess = generateProcess();
        addLast(newprocess, pHead, pTail);
        printf("        Process Id = %d, Priority = %d, Initial Burst Time = %d, Remaining Burst Time = %d\n",
            newprocess->iProcessId, newprocess->iPriority, newprocess->iInitialBurstTime, newprocess->iRemainingBurstTime);
    }
    printf("END\n\n");

}

int main(int argc, char* argv[]){
	struct element * rrHead = NULL;
    struct element * rrTail = NULL;

    generateP(&rrHead,&rrTail);

    long int sumrestime=0, sumturntime=0, restime=0, turntime=0;
    
    // create a tempProcess to access the current process's data
	struct process * tempProcess;
	
    while((rrHead->pNext) != NULL){
    	
    	tempProcess = removeFirst(&rrHead,&rrTail);
		gettimeofday(&(tempProcess->oMostRecentTime), NULL);
       
        runPreemptiveJob(tempProcess, &(tempProcess->oTimeCreated),&(tempProcess->oMostRecentTime));
        
        turntime = turntime + getDifferenceInMilliSeconds(tempProcess->oTimeCreated,tempProcess->oMostRecentTime);

        // if it is first run, then record the responce time and add it into the sum of responce time and print information
        // if it is finished, print the corresponding information
        // else add the remaining process to the end of the list and print the corresponding information
        if((tempProcess->iRemainingBurstTime+TIME_SLICE) == tempProcess->iInitialBurstTime){
        	printf("Process Id = %d, Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Responce Time = %ld\n",
         		tempProcess->iProcessId, tempProcess->iPriority, tempProcess->iPreviousBurstTime,tempProcess->iRemainingBurstTime, restime);
         		
        	sumrestime = sumrestime + restime;
        	restime = turntime;
        }
        else if(tempProcess->iRemainingBurstTime == 0){
        	printf("Process Id = %d, Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Trun Around Time = %ld\n",
        		tempProcess->iProcessId, tempProcess->iPriority, tempProcess->iPreviousBurstTime, tempProcess->iRemainingBurstTime, turntime);
        		sumturntime = sumturntime + turntime;
        		free(tempProcess);
        		continue;
        }
        else{
        	printf("Process Id = %d, Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d\n",
        		tempProcess->iProcessId, tempProcess->iPriority, tempProcess->iPreviousBurstTime, tempProcess->iRemainingBurstTime);
        }

        // if the process still needs to run, then add it to the end of the linked list
        if(tempProcess->iRemainingBurstTime != 0){
			addLast(tempProcess, &rrHead, &rrTail);
        }
   	
    }

    // get the last process
	tempProcess = removeFirst(&rrHead,&rrTail);
    
    // if the process remaining burst is bigger than the time slice
    while(tempProcess->iRemainingBurstTime > TIME_SLICE){
    	
        runPreemptiveJob(tempProcess, &(tempProcess->oTimeCreated),&(tempProcess->oMostRecentTime));
             
        turntime = turntime + getDifferenceInMilliSeconds(tempProcess->oTimeCreated,tempProcess->oMostRecentTime);

        printf("Process Id = %d, Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d\n",
        		tempProcess->iProcessId, tempProcess->iPriority, tempProcess->iPreviousBurstTime, tempProcess->iRemainingBurstTime);
        free(tempProcess);
		tempProcess = removeFirst(&rrHead,&rrTail);
    }
    
    // the remaining burst time is less than or equal to the time slice
    runPreemptiveJob(tempProcess, &(tempProcess->oTimeCreated),&(tempProcess->oMostRecentTime));
    turntime = turntime + getDifferenceInMilliSeconds(tempProcess->oTimeCreated,tempProcess->oMostRecentTime);
    
	printf("Process Id = %d, Priority = %d, Previous Burst Time = %d, Remaining Burst Time = %d, Trun Around Time = %ld\n",
			tempProcess->iProcessId, tempProcess->iPriority, tempProcess->iPreviousBurstTime, tempProcess->iRemainingBurstTime, turntime);
	sumturntime = sumturntime + turntime;

    printf("Average responce time = %.6f\nAverage turn around time = %.6f\n", (sumrestime/(float)NUMBER_OF_JOBS), (sumturntime/(float)NUMBER_OF_JOBS));
    free(tempProcess);
    
}
