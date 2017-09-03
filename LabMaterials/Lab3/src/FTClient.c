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
	int sockfd, filefd, len;
	struct sockaddr_in servaddr;
	file_segment segment;

	//socket()

	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = inet_addr(argv[1]);
	servaddr.sin_port = htons(atoi(argv[2]));

	//connect()

	if((filefd = open(argv[4], O_CREAT|O_WRONLY, S_IRWXU|S_IRGRP|S_IXGRP|S_IROTH|S_IXOTH)) == -1)
		error("file open() error");
	
	//write file path(argv[3]) to server

	while(/*read segment from server*/)
		//write data to file

	close(filefd);
	close(sockfd);
	return 0;
}

