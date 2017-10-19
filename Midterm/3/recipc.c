#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <sys/msg.h>
#include <sys/errno.h>

#define BUFSIZE 128
#define FIFOS "/tmp/midterm/fifos.3105"
#define FIFOC "/tmp/midterm/fifoc.3105"
#define KEY 3105L
#define PERMS 0666
#define PSND 1
#define CSND 2

extern int errno;

typedef struct {
	long msg_type;
	char msg_data[BUFSIZE];
} Msg;

void Process2(int, int);
void Process3(int, int);
void Process4(int);

void error(char* msg) {
	fprintf(stderr, "%s error\n", msg);
	exit(1);
}

void sigchld_handler(int sig) {
	int t_status, status;

	//(a) Call wait function to get the status of child process and make it to readable using WEXITSTATUS function

	//exit((b) follow the requirement);
}

int main(void) {
	int pipefd1[2], pipefd2[2];

	//(c) Open two pipes with pipe function

	if(fork() != 0) {
		int len, c_readfd, c_writefd;
		char buf[BUFSIZE];
		close(pipefd1[0]);
		close(pipefd2[1]);
		c_readfd = pipefd2[0];
		c_writefd = pipefd1[1];
		while(1) {

			//(d) read the data from child process

			buf[len] = 0;
			printf("Process 1 read from Process 2: %s\n", buf);
			printf("Echo!\n");
			printf("Process 1 write to Process 2: %s\n", buf);

			//(e) write the data to child process
		}
	}
	else {
		close(pipefd1[1]);
		close(pipefd2[0]);
		Process2(pipefd1[0], pipefd2[1]);
	}
	return 0;
}

void Process2(int p_readfd, int p_writefd) {

	//(f) Create two FIFOS

	if(fork() != 0) {
		int len, c_readfd, c_writefd;
		char buf[BUFSIZE];

		//(g) Open FIFOs with connections between c_writefd, FIFOS and between c_read, FIFOC

		while(1) {
			len = read(c_readfd, buf, BUFSIZE);
			buf[len] = 0;
			printf("Process 2 read from Process 3: %s\n", buf);
			printf("Process 2 write to Process 1: %s\n", buf);
			write(p_writefd, buf, len);
			len = read(p_readfd, buf, BUFSIZE);
			buf[len] = 0;
			printf("Process 2 read from Process 1: %s\n", buf);
			printf("Process 2 write to Process 3: %s\n", buf);
			write(c_writefd, buf, len);
		}
	}
	else {

		//(h) Open FIFOs for child process. Think about the connection with {FIFOC, FIFOS} and {p_readfd, p_writefd}

		Process3(p_readfd, p_writefd);
	}
}

void Process3(int p_readfd, int p_writefd) {
	if(fork() != 0) {
		int len, msgid;
		Msg msg;

		//(i) get msgid with msgget

		while(1) {
			//(j) recv msg from child process with type CSND

			msg.msg_data[len] = 0;
			printf("Process 3 read from Process 4: %s\n", msg.msg_data);
			printf("Process 3 write to Process 2: %s\n", msg.msg_data);
			write(p_writefd, msg.msg_data, len);
			len = read(p_readfd, msg.msg_data, BUFSIZE);
			msg.msg_data[len] = 0;
			printf("Process 3 read from Process 2: %s\n", msg.msg_data);
			printf("Process 2 write to Pcoess 4: %s\n", msg.msg_data);

			//(k) send msg to child process with type PSND;
		}
	}
	else {
		int msgid;

		//(l) get msgid with msgget

		Process4(msgid);
	}
}

void Process4(int msgid) {
	int len;
	Msg msg;
	while(1) {
		printf("input: ");
		scanf("%s", msg.msg_data);
		if(strcmp(msg.msg_data, "exit") == 0) {

			//(m) exit

		}
		else {
			printf("Process 4 write to process 3: %s\n", msg.msg_data);

			//(n) send msg to parent process with type CSND

			//(o) recv msg from parent process with type PSND

			msg.msg_data[len] = 0;
			printf("Process 4 read from process 3: %s\n", msg.msg_data);
		}
	}
}

