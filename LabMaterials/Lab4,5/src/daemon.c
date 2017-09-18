#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>

/* Detach a daemon process from login session context */
void daemon_start() {
	pid_t pid, sid;

	/* If we were started by init (process 1) from the /etc/inittab file there's no need to detach. This test is unreliable due to an unavoidable ambiguity if the proess is started by some other process and orphaned (i.e., if the parent process terminates before we are started). */
	if(getppid() != 1) {
		/* Ifnore the terminal stop signals */
#ifdef SIGTTOU
		signal(SIGTTOU, SIG_IGN);
#endif
#ifdef SIGTTIN
		signal(SIGTTIN, SIG_IGN);
#endif
#ifdef SIGTSTP
		signal(SIGTSTP, SIG_IGN);
#endif
		/* If we were not started in the background, fork and let the parent exit. This also guarantees the first child is not a process group leader */
		if((pid = fork()) > 0)
			exit(0);

		/* Disassociate from controlling terminal and process group. Ensure the process can't reacquire a new controlling terminal */
		if((sid = setsid()) < 0)
			exit(1);
	}

	/* Clear any inherited file mode creation mask */
	umask(0);

	/* Move the current directory to root, too make sure we aren't on a mounted file system. */
	if(chdir("/") < 0)
		exit(1);

	/* Close any open file descriptors */
	close(stdin);
	close(stdout);
	close(stderr);
}

int main(void) {
	int fd;
	char buf[1024];
	int buf_size;
	daemon_start();
	fd = open("/tmp/daemon.log", "w+");
	sprintf(buf, "daemon working pid: %d ppid: %d\n", getpid(), getppid());
	buf_size = strlen(buf);
	while(1) {
		write(fd, buf, buf_size);
		sleep(10);
	}

	return 0;
}

