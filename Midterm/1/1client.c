#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define SERVERIP "127.0.0.1"
#define PORT 8000

int main(){
	int     sockfd;
	struct sockaddr_in servaddr;
	int     strlen;
	char    message[1024];
	char    same[]          = "3S\n";

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if(sockfd == -1){
		printf("clientSocket create fail\n");
		return 0;
	}

	memset(&servaddr, 0, sizeof(servaddr));
	servaddr.sin_addr.s_addr	= inet_addr(SERVERIP);
	servaddr.sin_family		= AF_INET;
	servaddr.sin_port		= htons(PORT);

	if(connect(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr)) < 0)
	{
		printf("connect error\n");
		return 0;
	}
	strlen = read(sockfd, message, sizeof(message));
	printf("%s", &message);
	char sendline[1024] = {0};

	// "Imput Number (1 ~ 100) : ";
	while(fgets(sendline, 1024, stdin) != NULL)
	{
		int clientNumber = atoi(sendline);
		if(write(sockfd, &clientNumber, sizeof(int)) < 0)
			printf("write error\n");

		bzero(message, sizeof(message));
		strlen = read(sockfd, message, sizeof(message));
		printf("%s", &message);
		if(bcmp(message, same, sizeof(same)) == 0) {
			printf("the end\n");
			break;
		}
	}
	close(sockfd);

	return 0;
}
