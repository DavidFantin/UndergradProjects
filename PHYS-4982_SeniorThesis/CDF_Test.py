import spacepy.pycdf as cdf
import spacepy.plot as splot
import numpy as np

# open the CDF file
filename = 'wi_ehpd_3dp_19941220_v02.cdf'
cdf_data = cdf.CDF(filename)

# get the required data
energy = cdf_data['ENERGY'][:]
pangle = cdf_data['PANGLE'][:]

# calculate velocity components
v_x = energy * np.sin(np.radians(pangle))
v_y = energy * np.cos(np.radians(pangle))

# create a 2D histogram of the velocity components
bins = 50
hist, x_edges, y_edges = np.histogram2d(v_x, v_y, bins=bins)

# plot the histogram
fig, ax = splot.subplots()
splot.pcolormesh(ax, x_edges, y_edges, hist.T)
ax.set_xlabel('Vx [km/s]')
ax.set_ylabel('Vy [km/s]')
splot.colorbar(ax=ax)
splot.show()