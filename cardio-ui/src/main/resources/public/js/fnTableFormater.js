function valueFormatter(value, row) {
	content = '';
	if (value != -1) {
		content = value;
	}
	return content;
}

function statusFormatter(value, row) {
	content = '<div class="form-group-sm">';
	content += '<select data-id="' + row.id
			+ '" class="form-control change-status">';
	if (value == 'DRAFT') {
		content += '<option selected>DRAFT</option>';
	} else {
		content += '<option>DRAFT</option>';
	}
	if (value == 'READY') {
		content += '<option selected>READY</option>';
	} else {
		content += '<option>READY</option>';
	}
	if (value == 'PENDING') {
		content += '<option selected>PENDING</option>';
	} else {
		content += '<option>PENDING</option>';
	}
	if (value == 'DONE') {
		content += '<option selected>DONE</option>';
	} else {
		content += '<option>DONE</option>';
	}
	content += '</select></div>';
	return content;
}

function complexityFormatter(value, row) {
	content = '<div class="form-group-sm">';
	content += '<select data-id="' + row.id + '" class="form-control change-complexity">';
	var values = [0, 1, 2, 3, 5, 8, 13, 21];
	if (value==-1){
		content += '<option value="-1" selected></option>';
	} else {
		content += '<option value="-1"></option>';
	}
	$.each(values, function(key, val) {
		content += '<option value="' + val + '"';
		if (value==val) {
			content += ' selected';
		}
		content += '>' + val + '</option>';
	});
	content += '</select></div>';
	return content;
}