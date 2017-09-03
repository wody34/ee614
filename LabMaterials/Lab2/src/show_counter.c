// show_counter.c   
#include <stdio.h>      // printf()
#include <unistd.h>     // sleep()
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#define  KEY_NUM     9527
#define  MEM_SIZE    1024

int main( void){
   int   shm_id;
   void *shm_addr;

   if ( -1 == ( shm_id = shmget( (key_t)KEY_NUM, MEM_SIZE, IPC_CREAT|0666)))
   {
      printf( "shmget Error\n");
      return -1;
   }

   while( 1 ) {
      if ( ( void *)-1 == ( shm_addr = shmat( shm_id, ( void *)0, 0))) {
         printf( "shmat Error\n");
         return -1;
      }
      else {
         printf( "Success (shmat)\n");
      }

      printf( "%s\n", (char *)shm_addr);
      if ( -1 == shmdt( shm_addr))  {
         printf( "shmdt Error\n");
         return -1;
      }
      else  {
         printf( "Success (shmdt)\n");
      }
      sleep( 1);
   }
   return 0;
}

