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

	//socket()
	
	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(atoi(argv[1]));

	//bind()

	//listen()

	//accept()

	//read file path from client
	recv_msg[len] = 0;
	printf("%s\n", recv_msg);
	if((filefd = open(recv_msg, O_RDONLY)) == -1) {
		close(connfd);
		close(listenfd);
		error("file open() error");
	}

	while(/*read data from file*/) {
		segment.len = len;
		//write segment to client
	}

	close(filefd);
	close(connfd);
	close(listenfd);
	return 0;
}
