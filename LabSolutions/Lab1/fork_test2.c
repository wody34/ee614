#include <stdio.h>
#include <stdlib.h>
#include  <sys/types.h>
#include  <unistd.h>
int global_var = 0; /* declare the global variable */ 
int main(void)   {
	pid_t pid;
	int local_var = 0; /* declare the local variable */ 
	if((pid = fork()) <  0)   {
		printf("fork error\n");
		exit(0); /* child process */ 
	} else if (pid == 0) {   
		global_var++;
		local_var++;
		printf("CHILD - my pid is %d and parent's pid is %d\n", getpid(), getppid()); 
	} else {   
		/* parent process */
		sleep(2);/* wait for 2 seconds */
		global_var += 5;
		local_var += 5;
		printf("PARENT - my pid is %d, child's pid is %d\n", getpid(), pid);  
	}		
	printf("\t global var : %d\n", global_var);
	printf("\t local var : %d\n", local_var);
}
