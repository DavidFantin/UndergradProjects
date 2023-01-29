import pandas as pd
from matplotlib import pyplot as plt
import numpy as np
from sympy import solve, exp, Symbol, Eq
from scipy.optimize import curve_fit
from scipy.interpolate import interp1d

# Reading in the x and y data from Excel
fields = ['Pressure', 'Volume']
df = pd.read_excel (r'C:\Users\dfant\Documents\00-CodingProjects\workspace-main\UndergradProjects\PHYS-3112_InstrumentationLab\FinalProject\Pressure_v_Volume.xlsx', nrows=25, usecols=fields)

# Convering the pandas data to arrays
pressure = df['Pressure'].to_numpy()
volume = df['Volume'].to_numpy()

# Defining the equation to fit
def inverseExponentialEq(t, initialV, fianlV, tau):
    return (initialV - fianlV) * np.exp(-t / tau) + fianlV

# perform the fit
p0 = (15, 6, 2) # start with values near those we expect
params, cv = curve_fit(inverseExponentialEq, pressure, volume, p0)
initialV, fianlV, tau = params

# determine quality of the fit
squaredDiffs = np.square(volume - inverseExponentialEq(pressure, initialV, fianlV, tau))
squaredDiffsFromMean = np.square(volume - np.mean(volume))
rSquared = 1 - np.sum(squaredDiffs) / np.sum(squaredDiffsFromMean)

# print the results
print("initialV:", initialV)
print("fianlV:", fianlV)
print("initialV-fianlV:", initialV-fianlV)
print("tau:", tau)
print("rSquared:",rSquared)
print(f"\nP(V) = ({round(initialV-fianlV,3)}) * exp(-V/{round(tau,3)}) + {round(fianlV,3)}" )