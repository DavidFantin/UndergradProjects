# Load the CDF file
import urllib.request
import spacepy.pycdf
import numpy as np
import matplotlib.pyplot as plt
import warnings

filename = 'wi_ehpd_3dp_20080124_v02.cdf'
data = spacepy.pycdf.CDF(filename)

# Extract the necessary data
energy = data['ENERGY'][...]
pangle = data['PANGLE'][...]
flux = data['FLUX'][...]

print("Energy:", energy.shape, energy)
print("Pangle:", pangle.shape, pangle)
print("Flux:", flux.shape, flux)

# Convert energy and pitch angle to velocity
me = 9.10938356e-31  # electron mass (kg)
c = 299792458  # speed of light (m/s)
vel = c# * np.sqrt(1 - (me**2 * c**4) / (energy * (energy + 2 * me * c**2))) * np.sin(np.radians(pangle))

# # Plot the 2D histogram of the velocity distribution
# plt.hist2d(vel.flatten(), flux.flatten(), bins=(100, 100), cmap='jet')
# plt.xlabel('Electron Velocity (m/s)')
# plt.ylabel('Electron Flux')
# plt.colorbar()
# plt.show()

# Close the CDF file
data.close()