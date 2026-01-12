function createExecutionCounterUI(operationName, className) {
	console.log("at createExecutionCounterUI");

	// Remove existing container if any
	var existingContainer = document.getElementById("executionCounterContainer");
	if (existingContainer) {
	    existingContainer.remove();
	}
	
    var container = document.createElement("div");
    container.id = "executionCounterContainer";

    /* ================= ROW 1 ================= */
    var row1 = document.createElement("div");
    row1.className = "row mt-3";

    var col1 = document.createElement("div");
    col1.className = "col-md-12";

	var p = document.createElement("p");
	p.style.textAlign = "center";
	p.style.fontSize = "16px";
	p.style.color = "#333";

	p.innerHTML =
	    "The remaining time for running the nearest operation " +
	    "<span style='color:#28a745; font-weight:600;'>" + operationName + "</span>" +
	    " of class " +
	    "<span style='color:#17a2b8; font-weight:600;'>" + className + "</span>" +
	    " is:";

    col1.appendChild(p);
    row1.appendChild(col1);

    /* ================= ROW 2 ================= */
    var row2 = document.createElement("div");
    row2.className = "row text-center";

    row2.appendChild(createTimeColumn("Day", "daysValue"));
    row2.appendChild(createTimeColumn("Hour", "hoursValue"));
    row2.appendChild(createTimeColumn("Minute", "minutesValue"));
    row2.appendChild(createTimeColumn("Second", "secondsValue"));

    container.appendChild(row1);
    container.appendChild(row2);

    /* ======= INSERT IN THE RIGHT PLACE ======= */
    var headerRow = document.querySelector(
        "#OperationsScheduleReportDiv .row.second"
    );

    var loaderRow = headerRow.nextElementSibling;

    headerRow.parentNode.insertBefore(container, loaderRow);
}

function createTimeColumn(labelText, valueId) {
	
	console.log("createTimeColumn");

    var col = document.createElement("div");
    col.className = "col-md-3";

    var label = document.createElement("div");
    label.innerText = labelText;
    label.style.fontWeight = "bold";

    var value = document.createElement("div");
    value.id = valueId;
    value.innerText = "0";
    value.style.fontSize = "20px";
    value.style.color = "#007bff";

    col.appendChild(label);
    col.appendChild(value);

    return col;
}

function displayCounterValues(days, hours, minutes, seconds) {
    document.getElementById("daysValue").innerText = days;
    document.getElementById("hoursValue").innerText = hours;
    document.getElementById("minutesValue").innerText = minutes;
    document.getElementById("secondsValue").innerText = seconds;
}

function startExecutionCountdown(targetDateStr) {

    // Clear previous countdown if it exists
    if (executionCountdownInterval) {
        clearInterval(executionCountdownInterval);
        executionCountdownInterval = null;
    }

    var targetTime = new Date(targetDateStr.replace(" ", "T")).getTime();

    executionCountdownInterval = setInterval(function() {

        var now = new Date().getTime();
        var diff = targetTime - now;

        if (diff <= 0) {
            clearInterval(executionCountdownInterval);
            executionCountdownInterval = null;
            location.reload();
            return;
        }

        var days = Math.floor(diff / (1000 * 60 * 60 * 24));
        var hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((diff % (1000 * 60)) / 1000);

        displayCounterValues(days, hours, minutes, seconds);

    }, 1000);
}