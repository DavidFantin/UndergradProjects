import pandas as pd
import numpy as np
import scipy.optimize as sci
import matplotlib.pyplot as plt

# Reading in the x and y data from Excel
fields = ['Frequency', 'Magnitude','PhaseAngle']
df = pd.read_excel (r'C:\Users\dfant\Documents\00-CodingProjects\workspace-main\UndergradProjects\PHYS-3072_SeniorPhysicsLab\Data.xlsx', nrows=16, usecols=fields)

# Convering the pandas data to arrays
FreqData = df['Frequency'].to_numpy()
MagData = df['Magnitude'].to_numpy()
PAData = df['PhaseAngle'].to_numpy()

# Setting our known temperature and resistance values (we used room temperature as given by the calibration table)
f_0 = 6.103

# Defining the B Parameter Equation
def M(f,g):
    return 1/(np.sqrt((1-(f/f_0))**2 + (2*g*f / f_0) ** 2))

# Initial guesses for the value of B
# We are given that B should be close to 3900 Kelven
initialGuess = [0.01]

# Fitting the B Parameter Equation to our datapoints
# popt = the array of optimal values for the parameters which minimizes the sum of squares of residuals
# pcov = 2d array which contains the estimated covariance of popt
# B_opt = the optimal value of B to best fit the data
popt,pcov = sci.curve_fit(M, FreqData, MagData, initialGuess)
B_opt = popt[0]
x_model = np.linspace(min(FreqData), max(FreqData),1000)
y_model = M(x_model,B_opt)

# Output
print("********************************\n")
print("Our B parameter =",B_opt,"K\n")
print((B_opt - 0.0112)/0.0112*100)
print("********************************")

# Customizing font size of the plot
plt.rc('axes', labelsize=20, titlesize=26)
plt.rc('xtick', labelsize=13)
plt.rc('ytick', labelsize=13)

# Plotting both our data and the fitted curve
plt.scatter(FreqData,MagData,color='black',label='Calibration data for the NTC thermistor')
plt.plot(x_model,y_model,color='r',label='B Parameter Equation curve fit')
plt.title("NTC Thermistor Calibration Data:\nTemperature [K] vs. Resistance [\u03A9]\n10/17/2022")
plt.xlabel("Temperature [K]")
plt.ylabel("Resistance [\u03A9]")
plt.legend(fontsize=17)
plt.grid()
plt.show()