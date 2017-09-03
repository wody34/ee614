#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define BUF_SIZE 1024
#define NUM_ARG 256

void parse_str(char* str, char* arg[NUM_ARG]) {
	int cnt = 0;
	char *p = strtok(str, " ");
	if((arg[cnt++] = p) == NULL)
		return;
	while((p = strtok(NULL, " ")) != NULL)
		arg[cnt++] = p;
	arg[cnt] = NULL;
}

int main(int argc, char* args[]) {
	char buf[BUF_SIZE];
	char* arg[NUM_ARG];
	int status;
	if(argc != 2)
		exit(1);
	while(1) {
		pid_t pid;
		printf("%s > ", args[1]);
		gets(buf);
		parse_str(buf, arg);
		if(strcmp(arg[0], "exit") == 0)
			exit(0);
		if((pid = fork()) == 0)
			execlp(arg[0], arg[1], NULL);
		else
			wait(&status);
	}
}

