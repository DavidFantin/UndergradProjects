EXECS=mergeSort
MPICC?=mpicc

all: ${EXECS}

mergeSort: mergeSort.c
	${MPICC} -o mergeSort mergeSort.c

clean:
	rm ${EXECS}
