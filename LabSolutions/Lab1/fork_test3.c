#include <stdio.h>
#include <sys/types.h>

int main(void) {
	pid_t pid;
	if((pid = fork()) == 0)
		printf("[Child] pid: %d, ppid: %d\n", getpid(), getppid());
	else
		printf("[Parent] pid: %d, cpid: %d\n", getpid(), pid);

	while(1)
		sleep(100);
}

