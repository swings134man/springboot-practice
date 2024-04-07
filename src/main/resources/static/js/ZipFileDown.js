// Common JS for ZipFileDown

function downloadZipFile(url, fileName, ids) {

    validZipFile(url, fileName, ids);

    // FIXME : need to axios Library import
    axios.get(url,
        {params: {targetList: ids, fileName: fileName}
            , responseType: 'blob'
        })
        .then(res => {
            const date = moment().format('YYYYMMDDHHmmss');

            const blob = new Blob([res.data], { type: 'application/octet-stream' });

            const link = document.createElement('a');
            const url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = `zipFile_${date}.zip`;
            link.click();

            window.URL.revokeObjectURL(url);
        }).catch((e) => {
        console.log(e);
        alert('오류가 발생했습니다.');
    });


}


function validZipFile(url, fileName, ids) {
    if(url === null || url === "") {
        alert("Please provide a valid URL to download the file.");
        return;
    }

    if(ids === null || ids.size === 0) {
        alert("Please select at least one file to download.");
        return;

    }

    if(fileName === null || fileName === "") {
        alert("Please provide a valid file name.");
        return;
    }
}