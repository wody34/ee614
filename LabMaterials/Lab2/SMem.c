#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>
#include <time.h>

#define SHARED_MEMORY_KEY 1234
#define INIT_VALUE 5
#define MAX_VALUE 10
#define MIN_VALUE 0
#define RAND_DISTANCE 5

int main()
{
    int shmid;
    int pid;
    int *cal_num;
    int sleep_time=0;
    int status;
    int seed1, seed2;

    srand(time(NULL));
    seed1 = rand();
    seed2 = rand();

    // make space that shared-memory
    shmid = shmget((key_t)SHARED_MEMORY_KEY, sizeof(int), 0666 | IPC_CREAT);
    if(shmid == -1){
        perror("shmget failed : ");
        exit(0);
    }

    cal_num = (int *)shmat(shmid, NULL, 0);
    if(cal_num ==(int *)-1){
        perror("shmat failed : ");
        exit(0);
    }
    *cal_num = INIT_VALUE;

    // make child process
    pid = fork();

    //child process
    if(pid == 0){
        srand(seed2);
        while(1){
            if((*cal_num) >= MAX_VALUE || (*cal_num)<= MIN_VALUE){
                break;
            }
            *cal_num = *cal_num + 1;
            printf("child : I'm %d sec sleep. plus 1. current value %d\n", sleep_time, *cal_num);
            sleep_time = (rand()%RAND_DISTANCE) +1;
            sleep(sleep_time);
        }
        if((*cal_num) >= MAX_VALUE){
            puts("child : I'm winner\n");
        }
        else{
            puts("child : I'm looser\n");
        }
        shmdt(cal_num);
    }
    // parent process
    else{
        while(1){
            srand(seed1);
            if((*cal_num) >= MAX_VALUE || (*cal_num)<= MIN_VALUE){
                break;
            }
            *cal_num = *cal_num - 1;
            printf("parent :  I'm %d sec sleep. minus 1. current value: %d\n", sleep_time, *cal_num);
            sleep_time = (rand()%RAND_DISTANCE) +1;
            sleep(sleep_time);
        }
        if((*cal_num) >= MAX_VALUE){
            puts("parent : I'm looser\n");
        }
        else{
            puts("parent : I'm winner\n");
        }
        shmdt(cal_num);
        shmctl(shmid, IPC_RMID, NULL);
        waitpid(pid, &status, 0);
    }
    return 0;
}
