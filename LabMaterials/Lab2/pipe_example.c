#include <stdio.h>
#include <stdlib.h>

int main(void) {
	int pipefd[2], n;
	char buf[100];
	if(pipe(pipefd) < 0)
		exit(1);
	printf("read fd = %d, write fd = %d\n", pipefd[0], pipefd[1]);
	
	if(write(pipefd[1], "hello world\n", 12) != 12)
		exit(1);

	if((n = read(pipefd[0], buf, sizeof(buf))) <= 0)
		exit(1);
	write(1, buf, n);

	return 0;
}
