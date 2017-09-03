#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
int main()
{
	pid_t pid, child;
	int data=10;
	int state;
	pid=fork();
	if(pid<0)
		printf("fork fail. (process id : %d) ", pid);
	printf("fork success. (process id : %d) \n", pid);
	if(pid==0)  /* child process */ {
		data+=10;
		printf("child data : %d \n", data);
	}
	else      /* parent process */ {
		data-=10;
		child = wait(&state); /* waiting for child process to terminate*/
		printf("child process id = %d \n", child);
		printf("return value = %d \n", WEXITSTATUS(state));
		sleep(20); /* waiting 20 seconds */
		printf("parent data : %d \n", data);
	}
	return 0;
}

