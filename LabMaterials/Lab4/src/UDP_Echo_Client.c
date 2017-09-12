#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define BUFFERSIZE 256

void error_handling(char* msg) {
    perror(msg);
    exit(1);
}

int main(int argc, char **argv)
{
    int sockfd;
    char message[BUFFERSIZE];
    char message2[BUFFERSIZE];
    int str_len;
    struct sockaddr_in serv_addr;
    socklen_t serverlen;

    if(argc != 3)
    {
        printf("Usage : %s <IP> <port> \n", argv[0]);
        exit(1);
    }

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockfd == -1)
        error_handling("socket() error");

    memset(&serv_addr,0,sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr=inet_addr(argv[1]);
    serv_addr.sin_port=htons(atoi(argv[2]));

    while(1){
        serverlen = sizeof(serv_addr);
        fgets(message,BUFFERSIZE,stdin);
        sendto(sockfd, message, strlen(message), 0, (struct sockaddr*)&serv_addr, serverlen);

        str_len = recvfrom(sockfd, message2, BUFFERSIZE-1, 0, (struct sockaddr*)&serv_addr, &serverlen);

        message2[str_len] = '\0';
        
        printf("Received a message from the server : %s\n",message2);
    }
    close(sockfd);
    return 0;
}
