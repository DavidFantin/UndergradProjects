#!/usr/bin/env bash
#SBATCH --job-name=3.8
#SBATCH --error=job.%J.err
#SBATCH --output=job.%J.out
#SBATCH --ntasks=8


module load gcc
module load openmpi
mpirun ./mergeSort
