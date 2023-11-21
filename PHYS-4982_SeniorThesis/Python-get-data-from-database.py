# Before running install the following pakages from the command line:
## spacepy -> Recommended but not needed for this code
## cdasws -> Required

#### If you are using pip:
## 1 ## pip install -U spacepy
## 2 ## pip install -U cdasws

#### If you are using conda and do not have pip: (untested but in theory should work)
## 1 ## Open conda terminal as an administrator
## 2 ## conda install pip
## 3 ## pip install -U spacepy
## 4 ## pip install -U cdasws

from cdasws import CdasWs

## Note: If you want to get data from another instument, 
## you must replace these params with the params from the instument you want
## Find the right perameter names from this link: https://cdaweb.gsfc.nasa.gov/misc/NotesW.html#WI_K0_SWE
## From this you can also see sample python programs for querying data
params = ['Te','Te_anisotropy','average_energy','pa_press_tensor','pa_dot_B','heat_flux_magn','heat_flux_el','heat_flux_az','Q_dot_B','sc_position','el_bulk_vel_magn','el_bulk_vel_el','el_bulk_vel_az','el_density','sc_pot','flag','major_fr_rec','major_fr_spin_number']

# get_data([__], [__], [_start-time_], [_end-time_])
# start/end-time format: [_Year_]-[_month_]-[_day_]T[_hour_]:[_minute_]:[_second_].[_millisecond_]Z
stat, data = CdasWs().get_data('WI_H0_SWE', params, '1998-04-11T12:00:00.000Z', '1998-04-12T12:00:09.000Z')

# Replace 'el_density' with the desired parameter, e.g. 'Te' for temperature
print(data['el_density'])

# Yields various attributes of the given dataset
# print(data['el_density'].attrs)