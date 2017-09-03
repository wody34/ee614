#include <stdio.h>
#include <unistd.h>

int main(int argc, char* argv[]) {
	int x = 3;
	int err = execl(argv[1], argv[1], argv[2], NULL);	//WARN: Actually, second argument should not include the path.
	printf("error = %d, x = %d\n", err, x);
}
