#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <fcntl.h>

#define BUFSIZE 256

typedef struct file_segment {
	int len;
	char buf[BUFSIZE];
} file_segment;

void error(char* msg) {
	perror(msg);
	exit(1);
}

int main(int argc, char* argv[]) {
	int listenfd, connfd, filefd, len;
	struct sockaddr_in cliaddr, servaddr;
	socklen_t clilen;
	char recv_msg[BUFSIZE];
	file_segment segment;

	if((listenfd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
		error("socket() error");

	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(atoi(argv[1]));

	if(bind(listenfd, (struct sockaddr*)&servaddr, sizeof(servaddr)) == -1)
		error("bind() error");

	if(listen(listenfd, 5) == -1)
		error("listen() error");

	if((connfd = accept(listenfd, (struct sockadder*)&cliaddr, &clilen)) == -1)
		error("accept() error");

	while(1) {
		len = read(connfd, recv_msg, sizeof(recv_msg));

		printf("Data Received: %s\n", recv_msg);
		recv_msg[len] = '\0';
		write(connfd, recv_msg, len);
	}
	close(connfd);
	close(listenfd);
	return 0;
}
