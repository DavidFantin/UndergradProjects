#!/usr/bin/env bash
#SBATCH --job-name=Problem3Part1
#SBATCH --error=job.%J.err
#SBATCH --output=job.%J.out
#SBATCH --ntasks=1

module load gcc
module load openmpi

/home/djf3095/MPI_Project/3.2/part1
