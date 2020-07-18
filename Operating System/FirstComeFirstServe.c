#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include "linkedlist.h"
#include "coursework.h"

//Generate Process  
void generateP(struct element ** pHead, struct element ** pTail){
    for(int i = 0; i < NUMBER_OF_JOBS; i++){
        struct process * newprocess = generateProcess();
        addLast(newprocess, pHead, pTail);
    }
}

int main(int argc, char* argv[]){
    struct element * fcfsHead = NULL;
    struct element * fcfsTail = NULL;
    
    generateP(&fcfsHead, &fcfsTail);

    long int sumrestime=0, sumturntime=0, restime=0, turntime=0;
    
	//For each process, create a temp process for copying information
    for(int k = 0; k < NUMBER_OF_JOBS; k++){

        struct process * tempProcess;
        tempProcess=removeFirst(&fcfsHead, &fcfsTail);//Get the data from the first element     

        gettimeofday(&(tempProcess->oMostRecentTime), NULL);//Before running, record the time 

        restime = getDifferenceInMilliSeconds(tempProcess->oTimeCreated,tempProcess->oMostRecentTime);// Calculate response time
     
        runNonPreemptiveJob(tempProcess,&(tempProcess->oTimeCreated),&(tempProcess->oMostRecentTime));

        //Since the oTimeCreated has been modified by runProcess (in runNonPreemptiveJob), it now represents the time before running
        turntime = restime + getDifferenceInMilliSeconds(tempProcess->oTimeCreated,tempProcess->oMostRecentTime);

        sumrestime = sumrestime + restime;
        sumturntime = sumturntime + turntime;

        printf("Process Id = %d, Previous Burst Time = %d, New Burst Time = %d, Responce Time = %ld, Turn Around Time =%ld\n",
         tempProcess->iProcessId, tempProcess->iPreviousBurstTime,tempProcess->iRemainingBurstTime, restime, turntime);

        //free the process
        free(tempProcess);
    }
    
    printf("Average responce time = %.6f\n", (sumrestime/(float)NUMBER_OF_JOBS));
    printf("Average turn around time = %.6f\n", (sumturntime/(float)NUMBER_OF_JOBS));  
    free(fcfsHead);
    free(fcfsTail);

}
