function exportGridToCsv() {

    const csvContent =
        'data:text/csv;charset=utf-8,' +
        encodeURIComponent(exportArrayGrid);

    const downloadLink =
        document.createElement('a');

    downloadLink.setAttribute(
        'href',
        csvContent
    );

    downloadLink.setAttribute(
        'download',
        'DuctUtilizationGridReport'
    );

    document.body.appendChild(downloadLink);

    downloadLink.click();

    document.body.removeChild(downloadLink);
}