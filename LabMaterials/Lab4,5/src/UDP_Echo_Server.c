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
    int str_len;
    struct sockaddr_in serv_addr;
    struct sockaddr_in client_addr;
    socklen_t clientlen;
    
    if(argc != 2) {
        printf("Usage : %s <port> \n", argv[0]);
        exit(1);
    }

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if(sockfd == -1)
        error_handling("socket() error");

    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(atoi(argv[1]));

    if(bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) == -1)
        error_handling("bind() error");

    for( ; ; ){
        clientlen = sizeof(client_addr);

        str_len = recvfrom(sockfd, message, BUFFERSIZE-1, 0, (struct sockaddr*)&client_addr, &clientlen);

        message[str_len] = '\0';
        
        sendto(sockfd, message, str_len, 0, (struct sockaddr*)&client_addr, clientlen);

        printf("Received packet from %s:%d\nData: %s\n\n", inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port), message);
    }
    return 0;
}
