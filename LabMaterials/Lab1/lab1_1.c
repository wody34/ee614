#include <stdio.h>
#include <stdlib.h>
int main(void) {
	pid_t pid;
	if((pid = fork()) <  0)   {
		printf("fork error\n");
		exit(0);
	} else if (pid == 0) {
		printf("CHILD\n");
	} else {
		sleep(2);
		printf("PARENT \n");
	}
	sleep(10);
	return 0;
}

