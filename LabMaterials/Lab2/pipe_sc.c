#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXBUF 1024

void client(int readfd, int writefd) {
	char buf[MAXBUF];
	int n;
	if(fgets(buf, MAXBUF, stdin) == NULL)
		exit(1);
	n = strlen(buf);
	if(buf[n-1] == '\n')
		--n;
	if(write(writefd, buf, n) != n)
		exit(1);
	while((n = read(readfd, buf, MAXBUF)) > 0) {
		if(write(1, buf, n) != n)
			exit(1);
	}
	if(n < 0)
		exit(1);
}

void server(int readfd, int writefd) {
	char buf[MAXBUF];
	int n, fd;

	if((n = read(readfd, buf, MAXBUF)) <= 0)
		exit(1);
	buf[n] = '\0';
	if((fd = open(buf, 0)) < 0) {
		sprintf(buf, "can't open file\n");
		n = strlen(buf);
		if(write(writefd, buf, n) != n)
			exit(1);
	}
	else {
		while((n = read(fd, buf, MAXBUF)) > 0) {
			if(write(writefd, buf, n) != n)
				exit(1);
		}
		if(n < 0)
			exit(1);
	}
}

int main(void) {
	int childpid, pipe1[2], pipe2[2];

	if(pipe(pipe1) < 0 || pipe(pipe2) < 0)
		exit(1);

	if((childpid = fork()) < 0)
		exit(1);
	else if(childpid > 0) {
		close(pipe1[0]);
		close(pipe2[1]);
		client(pipe2[0], pipe1[1]);
		while(wait((int*)0) != childpid);
		close(pipe1[1]);
		close(pipe2[0]);
	}
	else {
		close(pipe1[1]);
		close(pipe2[0]);
		server(pipe1[0], pipe2[1]);
		close(pipe1[0]);
		close(pipe2[1]);
	}

	return 0;
}
