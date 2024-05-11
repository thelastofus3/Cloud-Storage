document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById('file').addEventListener('change', function(){
        document.getElementById('uploadFile').submit();
    });
    document.getElementById('folder').addEventListener('change', function(){
        document.getElementById('uploadFolder').submit();
    });
});