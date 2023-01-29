#!/usr/bin/env bash
#SBATCH --job-name=Prob3Part2
#SBATCH --error=job.%J.err
#SBATCH --output=job.%J.out
#SBATCH --ntasks=128


module load gcc
module load openmpi
mpirun /home/djf3095/MPI_Project/3.2/part2/prob3.2.2
