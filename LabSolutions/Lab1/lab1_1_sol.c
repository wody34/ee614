
#include <stdio.h>
#include <stdlib.h>
int main(void) {
	pid_t pid;
	if((pid = fork()) <  0)   {
		printf("fork error\n");
		exit(0);
	} else if (pid == 0) {
		printf("CHILD pid=%d, ppid = %d\n", getpid(), getppid());
	} else {
		sleep(2);
		printf("PARENT pid=%d cpid= %d\n", getpid(), pid);
	}
	sleep(10);
	return 0;
}

