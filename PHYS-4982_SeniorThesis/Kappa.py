################################
# Contributors: David Fantin, Cameron Walker
# Purpose: testing the kappa_velocity_3D() function from PlasmaPy
# TURC Summer 2022
################################

from operator import length_hint
from cdasws import CdasWs
import numpy as np
import astropy.units as u
import matplotlib.pyplot as plt
from plasmapy.particles import *
from plasmapy.formulary import *
import astropy as astropy
import numpy as np
from datetime import date

from astropy import units as u
from scipy.special import gamma
import scipy.integrate as integrate

from plasmapy.formulary import parameters

## SOURCE: https://docs.plasmapy.org/en/stable/api/plasmapy.formulary.distribution.kappa_velocity_3D.html
## Directly gathered from the "distribution.py" class
def _v_drift_units(v_drift):
    # Helper method to assign units to  v_drift if it takes a default value
    if v_drift == 0 and not isinstance(v_drift, astropy.units.quantity.Quantity):
        v_drift = v_drift * u.m / u.s
    else:
        v_drift = v_drift.to(u.m / u.s)
    return v_drift

# SOURCE: https://docs.plasmapy.org/en/stable/api/plasmapy.formulary.distribution.kappa_velocity_3D.html
# I changed the name from kappa_velocity_3D to kappa_distribution in case we want to modify it
def kappa_distribution(
    vx,
    vy,
    vz,
    T,
    kappa,
    particle="e",
    vx_drift=0,
    vy_drift=0,
    vz_drift=0,
    vTh=np.nan,
    units="units",
):
    r"""
    Return the probability density function for finding a particle with
    velocity components ``v_x``, ``v_y``, and ``v_z``in m/s in a suprathermal
    plasma of temperature ``T`` and parameter ``kappa`` which follows the
    3D Kappa distribution function. This function assumes Cartesian
    coordinates.

    Parameters
    ----------
    vx : `~astropy.units.Quantity`
        The velocity in x-direction units convertible to m/s.

    vy : `~astropy.units.Quantity`
        The velocity in y-direction units convertible to m/s.

    vz : `~astropy.units.Quantity`
        The velocity in z-direction units convertible to m/s.

    T : `~astropy.units.Quantity`
        The temperature, preferably in kelvin.

    kappa : `~astropy.units.Quantity`
        The kappa parameter is a dimensionless number which sets the slope
        of the energy spectrum of suprathermal particles forming the tail
        of the Kappa velocity distribution function. ``kappa`` must be greater
        than :math:`3/2`.

    particle : `str`, optional
        Representation of the particle species(e.g., 'p' for protons, 'D+'
        for deuterium, or 'He-4 +1' for :math:`He_4^{+1}` : singly ionized
        helium-4)), which defaults to electrons.

    vx_drift : `~astropy.units.Quantity`, optional
        The drift velocity in x-direction units convertible to m/s.

    vy_drift : `~astropy.units.Quantity`, optional
        The drift velocity in y-direction units convertible to m/s.

    vz_drift : `~astropy.units.Quantity`, optional
        The drift velocity in z-direction units convertible to m/s.

    vTh : `~astropy.units.Quantity`, optional
        Thermal velocity (most probable) in m/s. This is used for
        optimization purposes to avoid re-calculating ``vTh``, for example
        when integrating over velocity-space.

    units : `str`, optional
        Selects whether to run function with units and unit checks (when
        equal to "units") or to run as unitless (when equal to "unitless").
        The unitless version is substantially faster for intensive
        computations.

    Returns
    -------
    f : `~astropy.units.Quantity`
        Probability density in units of inverse velocity, normalized so that:
        :math:`\iiint_{0}^∞ f(\vec{v}) d\vec{v} = 1`

    Raises
    ------
    `TypeError`
        If any of the parameters is not a `~astropy.units.Quantity` and
        cannot be converted into one.

    `~astropy.units.UnitConversionError`
        If the parameters is not in appropriate units.

    `ValueError`
        If the temperature is negative, or the particle mass or charge state
        cannot be found.

    Notes
    -----
    In three dimensions, the Kappa velocity distribution function describing
    the distribution of particles with speed :math:`v` in a plasma with
    temperature :math:`T` and suprathermal parameter :math:`κ` is given by:

    .. math::

       f = A_κ \left(1 + \frac{(\vec{v} -
       \vec{V_{drift}})^2}{κ v_{Th},κ^2}\right)^{-(κ + 1)}

    where :math:`v_{Th},κ` is the kappa thermal speed
    and :math:`A_κ = \frac{1}{2 π (κ v_{Th},κ^2)^{3/2}}
    \frac{Γ(κ + 1)}{Γ(κ - 1/2) Γ(3/2)}` is the
    normalization constant.

    As :math:`κ → ∞`, the kappa distribution function converges to the
    Maxwellian distribution function.

    See Also
    --------
    kappa_velocity_1D
    kappa_thermal_speed

    Examples
    --------
    >>> from astropy import units as u
    >>> v=1 * u.m / u.s
    >>> kappa_velocity_3D(vx=v,
    ... vy=v,
    ... vz=v,
    ... T=30000 * u.K,
    ... kappa=4,
    ... particle='e',
    ... vx_drift=0 * u.m / u.s,
    ... vy_drift=0 * u.m / u.s,
    ... vz_drift=0 * u.m / u.s)
    <Quantity 3.7833...e-19 s3 / m3>
    """
    # must have kappa > 3/2 for distribution function to be valid
    if kappa <= 3 / 2:
        raise ValueError(f"Must have kappa > 3/2, instead of {kappa}.")
    if units == "units":
        # unit checks and conversions
        # checking velocity units
        vx = vx.to(u.m / u.s)
        vy = vy.to(u.m / u.s)
        vz = vz.to(u.m / u.s)
        # Catching case where drift velocities have default values, they
        # need to be assigned units
        vx_drift = _v_drift_units(vx_drift)
        vy_drift = _v_drift_units(vy_drift)
        vz_drift = _v_drift_units(vz_drift)
        # convert temperature to kelvin
        T = T.to(u.K, equivalencies=u.temperature_energy())
        if np.isnan(vTh):
            # get thermal velocity and thermal velocity squared
            vTh = parameters.kappa_thermal_speed(T, kappa, particle=particle)
        elif not np.isnan(vTh):
            # check units of thermal velocity
            vTh = vTh.to(u.m / u.s)
    elif np.isnan(vTh) and units == "unitless":
        # assuming unitless temperature is in kelvin
        vTh = parameters.kappa_thermal_speed(T * u.K, kappa, particle=particle).si.value
    
    # getting square of thermal velocity
    vThSq = vTh ** 2

    # Get square of relative particle velocity
    vSq = (vx - vx_drift) ** 2 + (vy - vy_drift) ** 2 + (vz - vz_drift) ** 2

    ### ******I added this ********
    n = 1 # number density of the particle in question


    # calculating distribution function
    expTerm = (1 + vSq / (kappa * vThSq)) ** (-(kappa + 1))
    coeff1 = n / (2 * np.pi * (kappa * vThSq) ** (3 / 2))
    coeff2 = gamma(kappa + 1) / (gamma(kappa - 1 / 2) * gamma(3 / 2))
    distFunc = coeff1 * coeff2 * expTerm
    if units == "units":
        return distFunc.to((u.s / u.m) ** 3)
    elif units == "unitless":
        return distFunc

#### END KAPPA DISTRIBUTION FUNCTION ####


# Without this the code wont run, it suppresses various warnings
import warnings
warnings.simplefilter(action='ignore')

# Enable plotting support for astropy.units
from astropy.visualization import quantity_support
quantity_support()

## Getting data from WI_H0_SWE
params = ['Te','Te_anisotropy','average_energy','pa_press_tensor','pa_dot_B','heat_flux_magn','heat_flux_el','heat_flux_az','Q_dot_B','sc_position','el_bulk_vel_magn','el_bulk_vel_el','el_bulk_vel_az','el_density','sc_pot','flag','major_fr_rec','major_fr_spin_number']

# get_data([__], [__], [_start-time_], [_end-time_])
# start/end-time format: [_Year_]-[_month_]-[_day_]T[_hour_]:[_minute_]:[_second_].[_millisecond_]Z
stat, data = CdasWs().get_data('WI_H0_SWE', params, '1999-07-12T00:00:00.000Z', '2000-07-12T00:00:00.000Z')

## Get number of datapoints available 
datalength = len(data['el_bulk_vel_magn'])

# electron_Vel_Elevation_Z contains the elevation vector of incoming electrons
electron_Vel_Elevation_Z = list(data['el_bulk_vel_el'] * u.m / u.s)

# electron_Vel_Elevation_X contains the magnitude vector of incoming electrons
electron_Vel_Magnitude_X = list(data['el_bulk_vel_magn'] * u.m / u.s)

# electron_Vel_Azimuth_Y contains the azimuth vector of incoming electrons
electron_Vel_Azimuth_Y = list(data['el_bulk_vel_az'] * u.m / u.s)

## el_density is NASA's calculation of electron density of the solar wind... This is the data we will compare out calculation against
true_electron_density = list(data['el_density'])

# Te contains the temperatures recorded for the electrons at each timestamp
# Note: the initial data from NASA are in eV units
electron_Temp = list(data['Te'] * u.K)
#print(electron_Temp[1])

# Converting eV to Kelvin (I used list comprehention to keep it simple, but it is just converting each element using the plasmapy.units package)
#electron_Temp = [x.to("K", equivalencies=u.temperature_energy()) for x in electron_Temp]
#print(electron_Temp[1])

#*********** Note: the kappa_velocity_3D() function takes in cartesian coordinates, so this may affect our output, since NASA's data is in GSE

# Kappa parameter:
# I found this from table 1 of this paper: https://agupubs.onlinelibrary.wiley.com/doi/full/10.1002/2014JA020825
## kappa value from WIND spacecraft (QUIET solar wind)  ** 3.0 ± 0.5 **
## kappa value from WIND spacecraft (FAST solar wind)   ** 5.0 ± 1.5 **
kappa = 5

# Note: The kappa distribution function outputs a scalar with units: s^3 / m^3
## So to utilize this to give us electron density, we must integrate each value over all of the velocities
for i in range(0,datalength): # to iterate through all timestamps
    #kappa_VDF = kappa_distribution(vx=electron_Vel_Magnitude_X[i], vy=electron_Vel_Elevation_Z[i], vz=electron_Vel_Azimuth_Y[i], T=electron_Temp[i], kappa=kappa, particle='e')
    
    # this is where we integrate kappa with respect to vx, vy and vz to get the electron density
    eDensity = integrate.tplquad(kappa_distribution(vx, vy, vz, T=electron_Temp[i], kappa=kappa, particle='e'), -100, 100)


#######################################

## Plots:

# size for the plot
f = plt.figure()
f.set_figwidth(10)
f.set_figheight(10)

# plotting NASA density data
timeInt = 86400
yedata = data['el_density']
xietmp = list(range(1, data.shape[0]+1))
xie=[round(float(i)*(timeInt/data.shape[0])) for i in xietmp]

plt.scatter(xie, yedata, marker='o', color='#001a72', s=.1, label='$n_{e}$')

plt.title('Electron Density ($n_{e}$) of Solar Wind \n', size=17)
plt.xlabel('Time [s]', size=15)
plt.xlim(min(xie),max(xie))
plt.ylabel('$n_{e}$ [$cm^{-3}$]', size=15)
plt.ylim(0,23)

plt.legend(loc="upper right",fontsize=10)
plt.grid()

plt.savefig('-e-Density_Plot.pdf')
plt.show()

# plotting our calculated data

## I removed what I had because it was confusing me
## I think I am going about this the wrong way.
