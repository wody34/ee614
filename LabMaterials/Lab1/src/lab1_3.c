#include <stdio.h>
#include <unistd.h>
#include <signal.h>
void sigint_handler(int sig);
int main()
{
	int state;
	int num=0;
	signal(/*please fill the blank*/);
	while(1)
	{
		printf("%d : waiting\n", num++);
		sleep(2);
		if(num>5)
			break;
	}
	return 0;
}

/* Signal handling function */
void sigint_handler(int sig)
{
	signal(/*please fill the blank*/);
	printf("delivered signal : %d \n", sig); 
}

