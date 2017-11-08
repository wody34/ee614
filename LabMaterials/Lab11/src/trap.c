#include <stdio.h>
#include <time.h>
#include "mpi.h"

main(int argc, char** argv) {
	int         my_rank;   /* My process rank           */
	int         p;         /* The number of processes   */
	double       a = 0.0;   /* Left endpoint             */
	double       b = 1.0;   /* Right endpoint            */
	int         n = 1024;  /* Number of trapezoids      */
	double       h;         /* Trapezoid base length     */
	double       local_a;   /* Left endpoint my process  */
	double       local_b;   /* Right endpoint my process */
	int         local_n;   /* Number of trapezoids for  */
	/* my calculation            */
	double       integral;  /* Integral over my interval */
	double       total = 0.f;     /* Total integral            */
	int         source;    /* Process sending integral  */
	int         dest = 0;  /* All messages go to 0      */
	int         tag = 0;
	MPI_Status  status;

	double start, finish, totalwalltime, traperror;
	double Trap(double local_a, double local_b, int local_n, double h);
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	start = MPI_Wtime();
	h = (b-a)/n;    /* h is the same for all processes */
	local_n = n/p;  /* So is the number of trapezoids */

	local_a = a + my_rank*local_n*h;
	local_b = local_a + local_n*h;
	integral = Trap(local_a, local_b, local_n, h);

	if(my_rank == 0)
	{
		for(source = 1; source < p; ++source)
		{
			MPI_Recv(&integral, 1, MPI_DOUBLE, source, tag, MPI_COMM_WORLD, &status);
			total += integral;
		}
	}
	else
	{
		MPI_Send(&integral, 1, MPI_DOUBLE, dest, tag, MPI_COMM_WORLD);
	}
	finish = MPI_Wtime();
	if (my_rank == 0) {
		printf("With n = %4d trapezoids, our estimate\n", n);
		fflush(stdout);
		traperror = total - 1.0e0;
		printf("of the integral on (%6.2f,%6.2f) = %11.5f; Error = %12.4e\n", a, b, total, traperror);
		fflush(stdout);
		totalwalltime=finish - start;

		printf("Integration Wall Time =%9.6f Seconds on %4d Processors\n", totalwalltime, p);
		fflush(stdout);
	}

	/* Shut down MPI */
	MPI_Finalize();
} /* end of main  */

double Trap(
		double  local_a   /* in */,
		double  local_b   /* in */,
		int    local_n   /* in */,
		double  h         /* in */) {
	double integral;   /* Store result in integral  */
	double x;
	int i;
	double f(double x); /* function we're integrating */

	integral = (f(local_a) + f(local_b))/2.0;
	x = local_a;

	for (i = 1; i <= local_n-1; i++) {
		x = x + h;
		integral = integral + f(x);
	}

	integral = integral*h;
	return integral;
} /*  Trap  */

double f(double x) {
	double return_val;
	int i;
	return_val = 5*x*x*x*x;
	for (i=1;i<100000;i++);
	return return_val;
} /* f */
