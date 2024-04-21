document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById('file').addEventListener('change', function(){
        document.getElementById('uploadForm').submit();
    });
});