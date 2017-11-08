#include <stdio.h>
#include "mpi.h"

int main(int argc, char **argv )
{
    int rank, size;
    int    namelen;
    char   hostname[50];

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Get_processor_name(hostname, &namelen);
    printf( "Hello world from process  %d (%s) of %d\n", rank, hostname,size);
  
    MPI_Finalize(); 
    return 0;
}
