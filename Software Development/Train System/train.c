//20029946 scysl1 Shihong LIU
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#define BIG 65535                                //a big number used in determine the shortest path
#define MAX 200                                  //max size of matrix
#define buffer 1000                              //space for fgets
#define LENGTH(a)   (sizeof(a)/sizeof(a[0]))     //use to calculate the length of a matrix

//I use this prompt function from lecture with modifying. It will exit with code 3 if it's out of bound of memory allocation.
//The reason using this prompt function is getting the name of start/end station and put them into a string respectively.
char *prompt(const char *mesg)
{
    char *name;
    int size = 10;
    name = malloc(sizeof(char) * size);
    if(name == NULL)
    {
        perror("Cannot allocate memory.");
        exit(3);
        return NULL;
    }
    printf("%s", mesg);
    int pos = 0;
    char ch;
    do
    {
        scanf("%c", &ch);
        if(ch != '\n')
        {
            name[pos] = ch;
            pos++;
            if(pos > size - 1)
            {
                char *tmp = malloc(sizeof(char) * (size * 2));
                if(tmp == NULL)
                {
                    free(name);  // free the current buffer before giving up.
                    return NULL;
                }
                for(int i = 0; i < size; i++)
                {
                    tmp[i] = name[i];
                }
                free(name);
                name = tmp;
                size = size * 2;
            }
        }
    } while(ch != '\n');
    name[pos] = '\0';
    return name;
}

//This printdc(dc count for distance and cost) function receives two arguments:the number of intermidiate station(pos) and the distance(dis).
//Then it will print the cost and distance respectively.
void printdc(int pos, int dis)
{
    int cost;
    cost=(pos-1)*25+1.2*dis;
    printf("Distance: %d KM\n",dis);
    printf("Cost: %d RMB\n", cost);
}

//This pp(abbr for printpath) function recieves several arguments.
//It can call itself which is similar to the sort we learned in IPA last year.
//If pos==1, it means it is a direct path. Otherwise, it has several intermidiate station.
int pos=0;
int pos2=0;
void pp(int flag[], int m,char mtx[MAX][MAX],int pos,int dis,int se)
{
    if(flag[m] == - 1)
    {
        if(pos==1)
        {
            printf("Direct\nTo ");
            pos2=pos;                         //cause pos will be destroyed when the function has
        }                                     //been already gone through, so i creat pos2 to get
        if(pos >1)                            //the final pos.
        {
            printf("via\n");
            pos2=pos;
        }
        return;
    }
    pos++;                                    //calculate how many intermidiate stations.
    pp(flag, flag[m],mtx,pos,dis,se);
    printf("%s\n", mtx[m]);                   //print the station name from mtx.
    if(pos==2)
    {
        printf("To ");
    }
    if(m==se)
    {
        printdc(pos2,dis);                     //find the end station, print cost and distance.
    }
}

//This ps function will print
void ps(int distance[],int ss, int flag[], int se, char mtx[MAX][MAX])
{
    int pos=0;
    printf("From %s\n", mtx[ss]);
    pp(flag, se, mtx,pos,distance[se],se);    //distance==dist[se]
}

//This dis(distance)function will add the distance once a row
int dis(int row,int distance[], int isin[])
{
    int min = BIG;
    int id;
    for (int i = 0; i < row; i++)
    {
        if (isin[i] == 0 && distance[i] <= min)                //If the station has not been the shortest path and the distance is not BIG
        {
            min = distance[i];                                 //update the dis[] and replace BIG with data in mtx1
            id = i;                                            //find the position of station.
        }
    }
    return id;
}
//I looked up some basic ideas of dijkstra algorithm and write following dijkstra algorithm myself.
//The basic idea of the following algorithm is to add each row of mtx1(data of distance) and find the shortest.
//In order not to get a path which through one station twice, i creat isin[] to determine whether one point has been walked through.
//Moreover, i creat flag[] to determine the start and end station.
//The algorithm will encounter one significant problem: if there are more than one path, how to confirm that the path you choose is the shortest one.
//To achieve this problem, i compare different distances. The direct path and not direct one. 'The direct path' means that next station is the end station, and we have a distance. And 'not direct one means that we may have several stations lasting, and we also can get a distance.
//i compare this two distance, if the direct distance is greater than the undirect distance, it will go to the next station of undirect path and have an another comparation; otherwise, it means we have found the shortest path.
void dijkstra(int mtx1[MAX][MAX], int ss, int se, char mtx[MAX][MAX],int row)
{
    
    int distance[MAX];                                         //use to calculate the distance
    int isin[MAX];                                             //use to determine whether the station has been travelled through
    int flag[MAX];                                             //use to determine the start and end station.
    for (int i = 0; i < row; i++)
    {
        isin[i] = 0;                                           //initialize
        distance[i] = BIG;
        flag[ss] = -1;
    }
    distance[ss] = 0;
    
    for (int num = 0; num < row-1; num++)                       //for every node in the graph algorithm, find shortest paths
    {
        int s = dis(row,distance, isin);
        isin[s] = 1;
        for (int i = 0; i < row; i++)
        {
            if ( distance[s] + mtx1[s][i] < distance[i])        //compare the distance.
            {
                if(mtx1[s][i])
                {
                    if(!isin[i])                                 //The new station must not have been in the path already.
                    {
                        flag[i] = s;
                        distance[i] = distance[s] + mtx1[s][i];  //distance+=
                    }
                }
            }
        }
    }
    ps(distance, ss, flag, se, mtx);                    //The shortest path has been found. Just need to be printed out.
}

//In main function, there are THREE main parts, which are RECEIVING STATION NAMES, RECEIVING DATA MATRIX and USER INPUT.
//For station names part, i creat a two dimentional matrix(mtx[][]) and put one name per row. Thus, each station name can be represented as mtx[]
//for data part, i creat a two dimentional matrix(mtx1[][]) as well. Because all the number should be integer, i use math calculation(pow) to change them from characters.
//for the final part, i use a while loop for user to input information and print the cost and distance.
int main(int argc, const char * argv[]) {
    
    FILE *fp;
    fp = fopen(argv[1], "r");
    if(fp==NULL)
    {
        perror("Cannot open file:");          //For situation that program cannot open file, it will exit with code 1.
        exit(1);
    }
    
    //station name part
    char z[buffer];
    fgets(z,buffer,fp);
    int row=0;
    int column=0;                             //declaration of 2D matrix for station name(mtx[][]).
    int d1=0;
    char mychar[MAX];                         //This array is for passing the character to 2D matrix.
    char mtx[MAX][MAX];
    for(int i=0; i<MAX; i++)
    {
        if(z[i]==',')                         //because there must have a ',' in the first line, thus we can ignore it for the first time.
        {                                     //if goes here again, it means the formal name e.g.Ningbo has been put into mychar[].
            if(d1==0)
            {
                d1++;
            }
            else
            {
                for(int j=0;j<column;j++)
                {
                    mtx[row][j]=mychar[j+1];  //copy character in mychar[] one by one into mtx.
                }
                row++;                        //start storing the next station name in next line of mtx.
                column=0;                     //initialize colomn of mtx.
            }
        }
        else if(i==strlen(z)-1)
        {
            for(int j=0;j<column;j++)
            {
                mtx[row][j]=mychar[j+1];      //for last name, it does not have ',' behind it so i add this situation(i==strlen-1).
            }
            row++;
            column=0;
        }
        else
        {
            column++;
            mychar[column]=z[i];              //copy character from file to mychar[].
        }
    }
    
    // data matrix part
    int  mtx1[MAX][MAX];                      //initialize 2D matrix for storing numerical data
    int row1=0;
    int column1=0;
    for(int k=0; k<row+1; k++)
    {
        int y;
        y = strlen(mtx[k]);
        char z1[buffer];
        fgets(z1,buffer,fp);                  //for each line, put character into z1[], using dynamically fgets.
        char mynum[MAX];
        int l=0;
        for(int m=y; m<MAX; m++)
        {
            if(z1[m]==',')                    //Similar with the formal part, if meeting the first ',', it will cintinue.
            {
                if(m==y)
                {
                    continue;
                }
                if(z1[m-1]==',')              //if there are ',' next to each other, it will put a zero in mtx1
                {
                    mtx1[row1][l]=0;
                    l++;
                }
                else
                {
                    int at=0;
                    for(int u=column1-1; u>=0; u--)
                    {
                        at = at+(mynum[u]-'0')*pow(10,column1-1-u); //change character into integer. e.g. For [1,5,5], at=('1'-'0')*pos(10,2)+
                    }                                               //('5'-'0')*pow(10,1)+('5'-'0')*pow(10,0)==155.
                    mtx1[row1][l]=at;        //put the number into 2D matrix:mtx1
                    l++;
                    column1=0;
                }
            }
            else if(z1[m]=='\n')             //if meeting '\n', it means we have already put all data in this line and we just need to move to
            {                                //another and store the formal number if there exist.
                int at=0;
                for(int u=column1-1; u>=0; u--)
                {
                    at = at+(mynum[u]-'0')*pow(10,column1-1-u);
                }
                mtx1[row1][l]=at;
                column1=0;
                row1++;                      //start storing other data next line.
                l=0;                         //initialize the column of mtx1
                break;
            }
            else
            {
                mynum[column1]=z1[m];
                column1++;
            }
        }
    }
    for(int m=0; m<row1-1;m++)
    {
        for(int n=0;n<row1-1;n++)
        {
            if(m!=n && mtx1[m][n]==0 )       //change 0 into a big number cause we dont have direct way between two stations except themselves
            {
                mtx1[m][n]=BIG;
            }
        }
    }
    fclose(fp);                              //we have already get all information from files.
    
    //user input part
    int pro=1;
    while(pro)
    {
        int ss=0;                            //use for check whether there is a station user want.
        int se=0;                            //use for check whether there is a station user want.
        int dd=1;
        char*name1=prompt("Start station: ");//put user input'start station' into name1.
        if(name1[0]=='\0')
        {
            exit(4);                         //once user press enter, it will exit.
        }
        for(int m=0; m<row;m++)
        {
            if(strcmp(mtx[m],name1)==0)      //search if there is a station which name is the same as user input.
            {
                dd=0;                        //it means that it has found the end station wanted
                ss=m;
            }
        }
        if(dd==1)
        {
            printf("No such station!\n");    //if there isnt a station, continue.
            continue;
        }
        int ddd=1;
        char*name2=prompt("End station: ");  //put user input 'end station' into name2.
        for(int m=0; m<row;m++)
        {
            if(strcmp(mtx[m],name2)==0)
            {
                ddd=0;                       //it means that it has found the end station wanted
                se=m;
            }
        }
        if(ddd==1)
        {
            printf("No such station!\n");    //same as formal. If there isnt a station, continue.
            continue;
            
        }
        if(strcmp(name1,name2)==0)           //if user input same station name, it will print 'same station'.
        {
            printf("No journey, same start and end station\n");
            continue;
        }
        dijkstra(mtx1, ss, se, mtx,row);     //use dijkstra graph algorithm to solve the problem.
    }
}




