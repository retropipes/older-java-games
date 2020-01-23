# Define raw data
raw_data = '0020-007E;00A1-00A7;00A9-00AC;00AE-024F;0391-03A1;03A3-03FF;1E00-1EFF;2012-2026;2030-205E;20A0-20BF;2100-218B;2190-23FF;2460-27FF;2900-2B73;2B76-2B95;2B98-2BC8;2BCA-2BFE;3248-325F;32B1-32BF'
# Define converted data
data_ranges = list()
formatted_values = list()
# Convert raw data
raw_ranges = raw_data.split(';')
for raw_range in raw_ranges:
	raw_bounds = raw_range.split('-')
	lbound = int(raw_bounds[0], base=16)
	ubound = int(raw_bounds[1], base=16) + 1
	data_ranges.append(range(lbound, ubound))
# Process converted data
for data_range in data_ranges:
	for value in data_range:
		formatted_values.append('{:0=4X}'.format(value))
# Write the final results
with open('safe.master.properties', 'w', encoding='UTF-8') as outfile:
	counter = 0
	max = len(formatted_values) - 1
	for value in formatted_values:
		if counter == max:
			outfile.write('\\u' + value + '=')
		else:
			outfile.write('\\u' + value + '=\n')
		counter += 1