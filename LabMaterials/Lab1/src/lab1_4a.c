#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
int main()
{
	pid_t pid;
	int data=10;
	pid=fork();
	if(pid<0)
		printf("fork fail. (process id : %d) \n", pid);
	printf("fork success. (process id : %d) \n", pid);
	if(pid==0) /* child process */
	{
		data += 10;
		printf("child data : %d \n", data);
	}
	else      /* parent process */ 
	{
		data-=10;
		sleep(20); /* waiting 20 seconds */
		printf("parent data : %d \n", data);
	}

	return 0;
}

