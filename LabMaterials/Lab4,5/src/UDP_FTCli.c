#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <fcntl.h>

#define SERV_PORT 49312
#define MAXLINE   1024

char *END_FLAG = "================END";

int main(int argc, char **argv)
{
    int sockfd, n, fd;
    struct sockaddr_in servaddr;
    char buf[MAXLINE];
    char *target, *path;
    
    bzero(&servaddr, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(SERV_PORT);
    inet_pton(AF_INET, argv[1], &servaddr.sin_addr);
    
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    
    path = argv[2];
    target = argv[3];
    sendto(sockfd, target, strlen(target), 0, (struct sockaddr *) &servaddr, sizeof(servaddr));
    n = recvfrom(sockfd, buf, MAXLINE, 0, NULL, NULL);
    if (!strncmp(buf, "ok", 2)) {
        printf("Filename sent.\n");
    }
    
    fd = open(path, O_RDONLY);
    while ((n = read(fd, buf, MAXLINE)) > 0) {
        sendto(sockfd, buf, n, 0, (struct sockaddr *) &servaddr, sizeof(servaddr));
    }
    sendto(sockfd, END_FLAG, strlen(END_FLAG), 0, (struct sockaddr *) &servaddr, sizeof(servaddr));
    
    close(sockfd);
    return 0;
}
