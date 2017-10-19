#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <arpa/inet.h>

typedef enum {false, true} bool;

int makeUniqueNumber();
bool number_included(int, int);
bool validate_input(int);
void scoring(int*, int*, int, int);

int main(int argc, char **argv){
	int     listenfd, connfd;
	struct  sockaddr_in cliaddr, servaddr;
	socklen_t clilen;
	int     serverNumber;
	char   intro[] = "Input Number 3 unique digits: ";
	char s[] = "%dS\n";
	char b[] = "%dB\n";
	char sb[] = "%dS%dB\n";
	char o[] = "Out\n";
	char input_error[] = "Input Error!\n";
	if(argc != 2)  {
		printf("Usage : %s <port> \n", argv[0]);
		exit(1);
	}
	// Fill in this blank
	if(listenfd == -1)  {
		printf("socket create Fail\n");
		return 0;
	}
	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family           = AF_INET;
	servaddr.sin_addr.s_addr     = htonl(INADDR_ANY);
	servaddr.sin_port              = htons(atoi(argv[1]));

	if(//Fill in this blank){
		printf("bind error\n");
		return 0;
	}
	if(listen(listenfd, 5) < 0){
		printf("listen error\n");
		return 0;
	}
	printf("ready game...\n");
	connfd = accept(listenfd, (struct sockaddr *) &cliaddr, &clilen);

	write(connfd, intro, sizeof(intro));
	srand((unsigned)time(NULL));
	serverNumber = //Fill in this blank
	printf("server set number : %d\n", serverNumber);
	while(1){
		char buffer[1024];
		bzero(buffer, sizeof(buffer));
		int clientNumber = 0;
		int count_S, count_B;
		//Fill in this blank
		printf("client input number : %d\n", clientNumber);
		if(!validate_input(clientNumber)) {
			sprintf(buffer, "%s%s", input_error, intro);
			//Fill in this blank
		}
		else {
			scoring(&count_S, &count_B, serverNumber, clientNumber);
			if(count_S && count_B)
				//Fill in this blank
			else if(count_S)
				sprintf(buffer, s, count_S);
			else if(count_B)
				sprintf(buffer, b, count_B);
			else
				strcpy(buffer, o);

			//Fill in this blank
			if(count_S == 3)
				break;
		}
	}
	printf(" Client answers the number.\n The the end\n");
	close(connfd);
	close(listenfd);

	return 0;
}

int makeUniqueNumber() {
	int return_value = 0;
	for(int i = 0; i < 3; ++i) {
		int select = rand() % 10;
		while(number_included(return_value, select))
			select = rand() % 10;
		return_value = return_value * 10 + select;
	}
	return return_value;
}

bool number_included(int origin, int check) {
	while(origin) {
		int digit = origin % 10;
		origin /= 10;
		if(check == digit)
			return true;
	}
	return false;
}

bool validate_input(int input) {
	if(input > 999 || input < 100)
		return false;

	while(input) {
		if(number_included(input/10, input%10))
			return false;
		input /= 10;
	}
	return true;
}

void scoring(int* count_S, int* count_B, int answer, int input) {
	int digit_answer[] = {answer/100%10, answer/10%10, answer%10};
	int digit_input[] = {input/100%10, input/10%10, input%10};
	*count_S = 0;
	*count_B = 0;

	// Implement the logic for scoring (calculate how many S and B)
}
