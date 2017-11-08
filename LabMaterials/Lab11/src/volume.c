#include <stdio.h>
#include <math.h>
#include "mpi.h"

#define PI 3.14159265 

inline double f(double x);

int main(int argc, char* argv[]) {
	int size;
	int rank;
	double from;
	double to;
	double dx;
	double result;

	double start_time;
	double end_time;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	
	printf("rank %d size %d\n", rank, size);
	fflush(stdout);
	MPI_Barrier(MPI_COMM_WORLD);
	if(rank == 0) {
		printf("from: ");
		fflush(stdout);
		scanf("%lf", &from);
		printf("to: ");
		fflush(stdout);
		scanf("%lf", &to);
		printf("dx: ");
		fflush(stdout);
		scanf("%lf", &dx);
		printf("%lf %lf %lf\n", from, to, dx);
		fflush(stdout);
                start_time = MPI_Wtime();
	}
	MPI_Bcast(&from, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast(&to, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast(&dx, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);

	double portion = (to-from)/size;
	double start = from+portion*rank;
	double end = from+portion*(rank+1);
	double localResult = 0;
	
	double x;
	for(x = start; x < end; x += dx)
		localResult += ((f(x)>0)?f(x)*f(x):0);
	localResult *= PI*dx;
	
	MPI_Reduce(&localResult, &result, 1, MPI_DOUBLE, MPI_SUM,  0, MPI_COMM_WORLD);

	if(rank == 0) {
		end_time = MPI_Wtime();
		printf("from %lf to %lf dx %lf volume %lf\n", from, to, dx, result);
		fflush(stdout);
		printf("process time %lf\n", end_time-start_time);
		fflush(stdout);
	}

	MPI_Finalize();
	return 0;
}

inline double f(double x) {
	return 2+0.2*sin(0.2*PI*x+2.5)+0.5*cos(0.7*PI*x-0.5);
}
