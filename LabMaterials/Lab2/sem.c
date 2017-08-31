#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>

int main(void) {
	struct sembuf up = {0, 1, 0};
	struct sembuf down = {0, -1, 0};
	int my_sem, v1, v2, v3, v4;

	my_sem = semget(IPC_PRIVATE, 1, 0600);
	v1= semctl(my_sem, 0, GETVAL);
	semop(my_sem, &up, 1);
	v2 = semctl(my_sem, 0, GETVAL);
	semop(my_sem, &down, 1);
	v3 = semctl(my_sem, 0, GETVAL);
	semctl(my_sem, 0, IPC_RMID);
	v4 = semctl(my_sem, 0, GETVAL);
	printf("Semaphore values: %d %d %d %d\n", v1, v2, v3, v4);
}
