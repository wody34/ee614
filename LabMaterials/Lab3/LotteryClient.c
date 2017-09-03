#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define GEN 0
#define LIST 1
#define END 2

void error(char* msg) {
	        perror(msg);
		        exit(1);
}

int main(int argc, char* argv[]) {
	int sockfd;
	struct sockaddr_in servaddr;

	//socket()

	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = inet_addr(argv[1]);
	servaddr.sin_port = htons(atoi(argv[2]));

	//connect();

	while(1) {
		int send_msg, len;
		char recv_msg[256];
		printf("Command (0:GEN 1:LIST 2:END): ");
		scanf("%d", &send_msg);
		//write()
		if(send_msg == END)
			break;			
		//read()
		recv_msg[len] = 0;
		printf("%s\n", recv_msg);
	}
	close(sockfd);
	return 0;
}
